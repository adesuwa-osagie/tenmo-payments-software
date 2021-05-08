package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Transfers;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TransfersService {

    private RestTemplate restTemplate = new RestTemplate();// what communicates with the server
    private String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final ConsoleService console = new ConsoleService();

    public TransfersService(String url){
        this.BASE_URL = url;
    }

/**
 *HttpHeaders headers = new HttpHeaders();
 * headers.set("Accept", "application/json");
 *
 * Map<String, String> params = new HashMap<String, String>();
 * params.put("msisdn", msisdn);
 * params.put("email", email);
 * params.put("clientVersion", clientVersion);
 * params.put("clientType", clientType);
 * params.put("issuerName", issuerName);
 * params.put("applicationName", applicationName);
 *
 * HttpEntity entity = new HttpEntity(headers);
 *
 * HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class, params);
 * */

    //A POST Request with query parameters
    public String addTransfer(String transferType, String transferStatus, Long currentUserId,
                            Long receiverUserId, BigDecimal amountToTransfer, String AUTH_TOKEN){
        //Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(AUTH_TOKEN);

        //Instantiate a map
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        //put (ie. add) key-value pairs to the map
        params.add("transferType", transferType);
        params.add("transferStatus", transferStatus);
        params.add("amountToTransfer", amountToTransfer.toString());

        //entity
        HttpEntity<MultiValueMap<String, String>> request
                = new HttpEntity<>(params,headers);

        //restTemplate
        ResponseEntity<String> response
                = restTemplate.exchange(BASE_URL + "accounts/" + currentUserId +
                "/transfer/" + receiverUserId, HttpMethod.POST, request, String.class);

        return response.getBody();

    }

    public Transfers[] listAllTransfersForUser(Long userAccountId, String AUTH_TOKEN){
        Transfers[] transfers = null;
        try{
            transfers = restTemplate.exchange(BASE_URL + "transfers/" + userAccountId,
                    HttpMethod.GET, makeAuthEntity(AUTH_TOKEN), Transfers[].class).getBody();
        }catch (RestClientResponseException ex) {
            console.printError("Could not retrieve the list of transfers for you. Is the server running?");
        } catch (ResourceAccessException ex) {
            console.printError("A network error occurred.");
        }
        return transfers;
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
