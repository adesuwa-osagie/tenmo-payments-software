package com.techelevator.tenmo.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tenmo.model.Accounts;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcAccountsDAOIntegrationTest {

    private static final Long TEST_USER_ID = 9999L;
    private static final BigDecimal TEST_STARTING_BALANCE = new BigDecimal("9999.99");


    private static SingleConnectionDataSource dataSource;
    private JdbcAccountsDAO accountsDAO;
    private JdbcUserDAO userDAO;


    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/tenmo");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //create User
        String sqlInsertUser = "INSERT INTO users (user_id, username, password_hash) " +
                "VALUES(?, 'Johnny Love', 'password9')";
        jdbcTemplate.update(sqlInsertUser, TEST_USER_ID);

        //create account
        sqlInsertUser = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_USER_ID, TEST_STARTING_BALANCE);

        accountsDAO = new JdbcAccountsDAO(dataSource);
        userDAO = new JdbcUserDAO(dataSource);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void get_balance_for_test_user() {
        //Arrange
        //ACTUAL: instantiate BigDecimal = AccountsDAO.getBalance - insert TEST_USER_ID
        //
        BigDecimal actual = accountsDAO.getBalance(TEST_USER_ID);
        //Act
        //EXPECTED: TEST_STARTING_BALANCE
        BigDecimal expected = TEST_STARTING_BALANCE;


        //Assert
        //AssertNotNull(BigDecimal ln.68)
        //AssertEquals(expected, actual)
        assertNotNull(actual);
        assertEquals(expected, actual);

    }

    @Test
    public void get_balance_test_user_2_return_1000() {
        //Arrange
        boolean testUser2 = userDAO.create("Yellow", "Banana");
        int testUser2Id = userDAO.findIdByUsername("Yellow");
        Long testUser2IdLong = Long.valueOf(testUser2Id);
        BigDecimal actual = accountsDAO.getBalance(testUser2IdLong);
        //Act
        BigDecimal startingBalance = new BigDecimal("1000.00");
        BigDecimal expected = startingBalance;
        //Assert
        assertNotNull(actual);
        assertEquals(expected, actual);

    }

    @Test
    public void find_all_accounts() {
        //Arrange
        List<Accounts> accounts = accountsDAO.findAllAccounts();
        //Act
        Accounts expected = accountsDAO.findCurrentUserAccount(TEST_USER_ID);
        Accounts actual = accounts.get(accounts.size() - 1);
        //Assert
        assertNotNull(accounts);
        assertAccountsAreEqual(expected, actual);

    }

    @Test
    public void find_all_accounts_after_adding_an_account() {

        //Arrange
        //findAllAccounts() before
        List<Accounts> beforeAdding = accountsDAO.findAllAccounts();

        //create another user using create method from userDAO
        boolean testUser2 = userDAO.create("Yellow", "Banana");
        int testUser2Id = userDAO.findIdByUsername("Yellow");
        Long testUser2IdLong = Long.valueOf(testUser2Id);

        //Act
        List<Accounts> actual = accountsDAO.findAllAccounts();

        int expectedNewListSize = beforeAdding.size() + 1;

        //Assert
        assertEquals(expectedNewListSize, actual.size());

    }

    @Test
    public void update_user_Balance_test() {
        //Arrange
        //make testUser2
        boolean testUser2 = userDAO.create("Yellow", "Banana");
        int testUser2Id = userDAO.findIdByUsername("Yellow");
        Long testUser2IdLong = Long.valueOf(testUser2Id);

        //use findUserAccount to get the test user Account
        Accounts theUpdatedAccount = accountsDAO.findCurrentUserAccount(TEST_USER_ID);
        //insert that into the updatedUserBalance along with testUser2IdLong
        accountsDAO.updateUserBalance(theUpdatedAccount, testUser2IdLong);

        //List<Accounts> accountsAgain = accountsDAO.findAllAccounts();
        //Accounts actual = accountsAgain.get(accounts.size() - 1);

        List<Accounts> accounts = accountsDAO.findAllAccounts();
        Accounts actual = accounts.get(accounts.size() - 1);

        //Assert
        assertAccountsAreEqual(theUpdatedAccount, actual);

    }


    @Test
    public void update_user_Balance_of_test_user_after_adding_two_new_users() {
        //Arrange
        //make testUser2
        boolean testUser2 = userDAO.create("Yellow", "Banana");
        int testUser2Id = userDAO.findIdByUsername("Yellow");
        Long testUser2IdLong = Long.valueOf(testUser2Id);

        //make testUser3
        boolean testUser3 = userDAO.create("Red", "Apple");
        int testUser3Id = userDAO.findIdByUsername("Red");
        Long testUser3IdLong = Long.valueOf(testUser3Id);

        //use findUserAccount to get the test user Account
        Accounts theUpdatedAccount = accountsDAO.findCurrentUserAccount(TEST_USER_ID);
        //insert that into the updatedUserBalance along with testUser2IdLong
        accountsDAO.updateUserBalance(theUpdatedAccount, testUser3IdLong);

        List<Accounts> accounts = accountsDAO.findAllAccounts();
        Accounts actual = accounts.get(accounts.size() - 1);

        //Assert
        assertAccountsAreEqual(theUpdatedAccount, actual);
    }


    private void assertAccountsAreEqual(Accounts expected, Accounts actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getBalance(), actual.getBalance());

    }

    private Accounts makeAccount(Long id, Long userId, BigDecimal balance) {
        Accounts theAccount = new Accounts();
        theAccount.setId(id);
        theAccount.setUserId(userId);
        theAccount.setBalance(balance);
        return theAccount;
    }


}