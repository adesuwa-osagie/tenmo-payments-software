package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.model.Accounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
public class AccountsController {

    @Autowired
    private AccountsDAO accountsDAO;

    @RequestMapping(path = "/accounts/{id}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long id){
        return accountsDAO.getBalance(id);
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Accounts> listAllAccounts(){
        return accountsDAO.findAllAccounts();
    }

    @RequestMapping(path = "/accounts/{id}", method = RequestMethod.PUT)
    public void updateAccountBalance(@RequestBody Accounts accounts,
                                         @PathVariable Long id) throws AccountNotFoundException {
        accountsDAO.updateUserBalance(accounts, id);
    }

    @RequestMapping(path = "/accounts/{id}/userAccount", method = RequestMethod.GET)
    public Accounts findCurrentUserAccount(@PathVariable Long id){
        return accountsDAO.findCurrentUserAccount(id);
    }



}

