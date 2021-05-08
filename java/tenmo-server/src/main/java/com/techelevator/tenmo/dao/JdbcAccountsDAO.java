package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountsDAO implements AccountsDAO{
    private JdbcTemplate jdbcTemplate;

    /*public JdbcAccountsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/

    public JdbcAccountsDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BigDecimal getBalance(Long userId){
        Accounts accounts = new Accounts();
        BigDecimal theUserBalance;

        String sqlCurrentBalance = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCurrentBalance,userId);

        while(results.next()){
            accounts = mapToRowAccounts(results);
        }
        theUserBalance = accounts.getBalance();

        return theUserBalance;
    }

    @Override
    public List<Accounts> findAllAccounts(){
        List<Accounts> accounts = new ArrayList<>();
        String sqlFindAllAccounts = "SELECT account_id, user_id, balance FROM accounts ORDER BY user_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindAllAccounts);
        while (results.next()){
            Accounts account = mapToRowAccounts(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Accounts findCurrentUserAccount(Long userId){
        Accounts accounts = new Accounts();
        String sqlFindCurrentUserAccount = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFindCurrentUserAccount, userId);
        while (results.next()){
            accounts = mapToRowAccounts(results);
        }
        return accounts;
    }

    @Override
    public void updateUserBalance(Accounts updatedUserBalance, Long userId){
        String sqlUpdateCurrentUserBalance = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sqlUpdateCurrentUserBalance, updatedUserBalance.getBalance(),
                updatedUserBalance.getUserId());
    }

    private Accounts mapToRowAccounts(SqlRowSet results) {
        Accounts theAccount = new Accounts();

        theAccount.setId(results.getLong("account_id"));
        theAccount.setUserId(results.getLong("user_id"));
        theAccount.setBalance(results.getBigDecimal("balance"));
        return theAccount;
    }


}
