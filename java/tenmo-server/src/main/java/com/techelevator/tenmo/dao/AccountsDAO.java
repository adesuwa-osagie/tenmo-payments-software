package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.AccountNotFoundException;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface AccountsDAO {

    BigDecimal getBalance(Long id);

    void updateUserBalance(Accounts accounts, Long id) throws AccountNotFoundException;

    List<Accounts> findAllAccounts();

    Accounts findCurrentUserAccount(Long userId);

}
