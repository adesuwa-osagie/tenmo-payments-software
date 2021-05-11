package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.view.ConsoleService;
import io.cucumber.core.internal.gherkin.GenerateTokens;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountsService {

    private RestTemplate restTemplate = new RestTemplate();// what communicates with the server
    private String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final ConsoleService console = new ConsoleService();
    private final String INVALID_ACCOUNT_MSG = "This account is not valid. Please try again";

    public AccountsService(String url){
        this.BASE_URL = url;
    }

    public BigDecimal getBalance(Long userId, String AUTH_TOKEN) throws AccountsServiceException {
        BigDecimal theBalance = null;
        try {
          theBalance = restTemplate.exchange(BASE_URL + "accounts/" + userId, HttpMethod.GET,
                  makeAuthEntity(AUTH_TOKEN), BigDecimal.class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError("Could not retrieve the balance. Is the server running?");
        } catch (ResourceAccessException ex) {
            console.printError("A network error occurred.");
        }

        return theBalance;
    }

    //A PUT Request with a Request Body
    public Accounts updateUserBalance(BigDecimal updatedBalance, Long currentUserId, String AUTH_TOKEN) throws AccountsServiceException {
        Accounts accounts = new Accounts();
        accounts.setUserId(currentUserId);
        accounts.setBalance(updatedBalance);

        try {
            restTemplate.exchange(BASE_URL + "accounts/" + currentUserId, HttpMethod.PUT,
                    makeAccountsEntity(accounts, AUTH_TOKEN), Accounts.class);
        } catch (RestClientResponseException ex) {
            console.printError("Could not update the balance. Is the server running?");
            throw new AccountsServiceException(ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString()); // this is above although no other message is also above
        } catch (ResourceAccessException ex) {
            console.printError("A network error occurred.");
        }
        return accounts;
    }

    public Accounts findCurrentUserAccount(Long currentUserId, String AUTH_TOKEN){
        Accounts accounts = null;
        try{
            accounts = restTemplate.exchange(BASE_URL + "accounts/" + currentUserId + "/userAccount",
                    HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Accounts.class).getBody();
        }catch (RestClientResponseException ex){
            console.printError("Could not retrieve the account. Is the server running?");
        } catch (ResourceAccessException ex){
            console.printError("A network error has occurred.");
        }
        return accounts;
    }


    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity<Accounts> makeAccountsEntity(Accounts theAccountWithUpdatedBalance, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity<Accounts> entity = new HttpEntity<>(theAccountWithUpdatedBalance, headers);
        return entity;
    }

}
