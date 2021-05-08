package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private RestTemplate restTemplate = new RestTemplate();// what communicates with the server
    private String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final ConsoleService console = new ConsoleService();

    public UserService(String url){
        this.BASE_URL = url;
    }

    public User[] getAllUsers(String AUTH_TOKEN) {
        User[] users = null;
        try {
            users = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET,
                    makeAuthEntity(AUTH_TOKEN), User[].class).getBody();
        } catch (RestClientResponseException ex) {
            console.printError("Could not retrieve the list of users. Is the server running?");
        } catch (ResourceAccessException ex) {
            console.printError("A network error occurred.");
        }
        return users;
    }

    private HttpEntity makeAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}