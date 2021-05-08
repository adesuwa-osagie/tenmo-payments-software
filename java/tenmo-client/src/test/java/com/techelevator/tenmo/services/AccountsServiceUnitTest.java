package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.view.ConsoleService;
import io.cucumber.java.bs.A;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountsServiceUnitTest {

    /** We made a valiant attempt at this, but weren't sure how to do it since it takes AUTH_TOKEN/Token/Auth stuff
     * we want to learn how to do unit testing for the front end!
     * */




    /* private static final String API_BASE_URL = "http://localhost:8080/";

    private AccountsService accountsService;
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private UserService userService;
    private TransfersService transfersService;

    *//*public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL),
                new AccountsService(API_BASE_URL), new UserService(API_BASE_URL), new TransfersService(API_BASE_URL));
        app.run();
    }*//*

    public AccountsServiceUnitTest(ConsoleService console, AuthenticationService authenticationService,
               AccountsService accountsService, UserService userService, TransfersService transfersService) {
        this.console = console;
        this.authenticationService = authenticationService;
        this.accountsService = accountsService;
        this.userService = userService;
        this.transfersService = transfersService;
    }

    @Test
    public void getBalanceTest() {
        BigDecimal currentUserBalance = accountsService.getBalance(id, token);
    }

    @Test
    public void updateUserBalance() {
    }

    @Test
    public void findCurrentUserAccount() {
    }*/
}