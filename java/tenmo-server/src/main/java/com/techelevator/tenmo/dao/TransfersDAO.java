package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface TransfersDAO {

    List<Transfers> listAllTransfers();

    void addTransfer(String transferTypeId, String transferStatusId, Long currentUserId, Long receiverUserId, BigDecimal amountToTransfer);

    public List<Transfers> listAllTransfersForUser(Long currentUserAccountId);
}
