package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcTransfersDAOIntegrationTest {

    private static final Long TEST_TRANSFER_ID = 9999L;
    private static final Long TEST_TRANSFER_STATUS_ID = 8888L;
    private static final Long TEST_ACCOUNT_FROM = 9000L;
    private static final Long TEST_ACCOUNT_TO = 8000L;
    private static final Long TEST_USER_ID = 9999L;
    private static final BigDecimal TEST_STARTING_BALANCE = new BigDecimal("9999.99");
    private static final String TEST_TRANSFER_TYPE_DESC = "unknown";
    private static final Long TEST_TRANSFER_TYPE_ID = 900L;
    private static final String TEST_TRANSFER_STATUS_DESC = "Turkey";
    private static final BigDecimal TEST_AMOUNT = new BigDecimal("5999.99");



    private static SingleConnectionDataSource dataSource;
    private JdbcTransfersDAO transfersDAO;
    private JdbcUserDAO userDAO;
    private JdbcAccountsDAO accountsDAO;

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
    public void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //create User
        String sqlInsertUser = "INSERT INTO users (user_id, username, password_hash) " +
                "VALUES(?, 'Johnny Love', 'password9')";
        jdbcTemplate.update(sqlInsertUser, TEST_USER_ID);

        //create account
        sqlInsertUser = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_ACCOUNT_FROM, TEST_USER_ID, TEST_STARTING_BALANCE);

        sqlInsertUser = "INSERT INTO accounts (account_id, user_id, balance) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_ACCOUNT_TO, TEST_USER_ID, TEST_STARTING_BALANCE);

        sqlInsertUser = "INSERT INTO transfer_types (transfer_type_id, transfer_type_desc) " +
                "VALUES(?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_TRANSFER_TYPE_ID, TEST_TRANSFER_TYPE_DESC);

        sqlInsertUser = "INSERT INTO transfer_statuses (transfer_status_id, transfer_status_desc) " +
                "VALUES(?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_TRANSFER_STATUS_ID, TEST_TRANSFER_STATUS_DESC);

        sqlInsertUser = "INSERT INTO transfers (transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlInsertUser, TEST_TRANSFER_ID, TEST_TRANSFER_TYPE_ID, TEST_TRANSFER_STATUS_ID, TEST_ACCOUNT_FROM,
                TEST_ACCOUNT_TO, TEST_AMOUNT);

        accountsDAO = new JdbcAccountsDAO(dataSource);
        userDAO = new JdbcUserDAO(dataSource);
        transfersDAO = new JdbcTransfersDAO(dataSource);


    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
//                                  Long transferId, Long transferTypeId,
//                                   Long transferStatusId, Long account_from,
//                                   Long account_to, BigDecimal amount
    @Test
    public void list_All_Transfers() {
        //Arrange
        List<Transfers> transfers = transfersDAO.listAllTransfers();
        //Act
        Transfers expected = makeTransfer(TEST_TRANSFER_ID, TEST_TRANSFER_TYPE_ID, TEST_TRANSFER_STATUS_ID,
                TEST_ACCOUNT_FROM, TEST_ACCOUNT_TO, TEST_AMOUNT);
        Transfers actual = transfers.get(transfers.size() - 1);
        //Assert
        assertNotNull(transfers);
        assertTransfersAreEqual(expected, actual);
    }

    @Test
    public void listAllTransfersForUser() {
        //Act
        //Arrange
        //Assert
    }

    @Test
    public void addTransfer() {
        //Act
        //Arrange
        //Assert
    }

    private Transfers makeTransfer(Long transferId, Long transferTypeId,
                                   Long transferStatusId, Long account_from,
                                   Long account_to, BigDecimal amount){

        Transfers theTransfer = new Transfers();
        theTransfer.setTransferId(transferId);
        theTransfer.setTransferTypeId(transferTypeId);
        theTransfer.setTransferStatusId(transferStatusId);
        theTransfer.setAccountFrom(account_from);
        theTransfer.setAccountTo(account_to);
        theTransfer.setAmount(amount);
        return theTransfer;

    }

    private void assertTransfersAreEqual(Transfers expected, Transfers actual){
        assertEquals(expected.getTransferId(), actual.getTransferId());
        assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
        assertEquals(expected.getAccountTo(), actual.getAccountTo());
        assertEquals(expected.getAmount(), actual.getAmount());
    }
}