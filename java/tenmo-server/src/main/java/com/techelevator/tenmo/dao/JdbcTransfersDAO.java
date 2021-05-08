package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransfersDAO implements TransfersDAO {

    private JdbcTemplate jdbcTemplate;

    /*public JdbcTransfersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }*/

    public JdbcTransfersDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Transfers> listAllTransfers() {
        List<Transfers> transfersList = new ArrayList<>();
        String sqlListAllTransfers = "SELECT transfer_id, transfer_type_id, transfer_status_id, " +
                "account_from, account_to, amount FROM transfers";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListAllTransfers);
        while (results.next()) {
            Transfers transfer = mapToRowTransfers(results);
            transfersList.add(transfer);
        }
        return transfersList;
    }

    @Override
    public List<Transfers> listAllTransfersForUser(Long currentUserAccountId) {
        List<Transfers> transfersList = new ArrayList<>();
        String sqlListAllTransfersForUser = "SELECT t.transfer_id, type.transfer_type_desc, " +
                "status.transfer_status_desc, " +
                "(SELECT u.username WHERE t.account_from = a.account_id), " +
                "(SELECT uc.username WHERE t.account_to = ac.account_id) AS userTo, " +
                "t.amount " +
                "FROM transfers t " +
                "JOIN transfer_types type ON t.transfer_type_id = type.transfer_type_id " +
                "JOIN transfer_statuses status ON t.transfer_status_id = status.transfer_status_id " +
                "JOIN accounts a ON t.account_from = a.account_id " +
                "JOIN users u ON u.user_id = a.user_id " +
                "JOIN accounts ac ON t.account_to = ac.account_id " +
                "JOIN users uc ON uc.user_id = ac.user_Id " +
                "WHERE t.account_to = ? OR t.account_from = ? " +
                "ORDER BY t.transfer_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlListAllTransfersForUser, currentUserAccountId, currentUserAccountId);
        while (results.next()) {
            Transfers transfers = mapToRowTransfersDisplay(results);
            transfersList.add(transfers);
        }
        return transfersList;
    }

    @Override
    public void addTransfer(String transferType, String transferStatus, Long currentUserId, Long receiverUserId, BigDecimal amountToTransfer) {
        String sqlAddTransfer = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES(" +
                "(SELECT transfer_type_id FROM transfer_types WHERE transfer_type_desc = ?), " +
                "(SELECT transfer_status_id FROM transfer_statuses WHERE transfer_status_desc = ?), " +
                "(SELECT account_id FROM accounts WHERE user_id = ?), " +
                "(SELECT account_id FROM accounts WHERE user_id = ?), " +
                "?)";
        jdbcTemplate.update(sqlAddTransfer, transferType, transferStatus, currentUserId, receiverUserId, amountToTransfer);

    }

    private Transfers mapToRowTransfers(SqlRowSet results) {
        Transfers theTransfer = new Transfers();

        theTransfer.setTransferId(results.getLong("transfer_id"));
        theTransfer.setTransferTypeId(results.getLong("transfer_type_id"));
        theTransfer.setTransferStatusId(results.getLong("transfer_status_id"));
        theTransfer.setAccountFrom(results.getLong("account_from"));
        theTransfer.setAccountTo(results.getLong("account_to"));
        theTransfer.setAmount(results.getBigDecimal("amount"));

        return theTransfer;
    }

    private Transfers mapToRowTransfersDisplay(SqlRowSet results) {
        Transfers theTransfer = new Transfers();

        theTransfer.setTransferId(results.getLong("transfer_id"));
        theTransfer.setTransferType(results.getString("transfer_type_desc"));
        theTransfer.setTransferStatus(results.getString("transfer_status_desc"));
        theTransfer.setUserFrom(results.getString("username"));
        theTransfer.setUserTo(results.getString("userTo"));
        theTransfer.setAmount(results.getBigDecimal("amount"));

        return theTransfer;
    }
}
