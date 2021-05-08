package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfers {
    private Long transferId;
    private Long transferTypeId;
    private String transferType;
    private Long transferStatusId;
    private String transferStatus;
    private Long accountFrom;
    private String userFrom;
    private Long accountTo;
    private String userTo;
    private BigDecimal amount;

    public Transfers(){

    }

    public Transfers(Long transferId, Long transferTypeId, Long transferStatusId, Long accountFrom,
                     Long accountTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }

    public Transfers(Long transferId, Long transferTypeId, String transferType, Long transferStatusId, String transferStatus, Long accountFrom, String userFrom, Long accountTo, String userTo, BigDecimal amount) {
        this.transferId = transferId;
        this.transferTypeId = transferTypeId;
        this.transferType = transferType;
        this.transferStatusId = transferStatusId;
        this.transferStatus = transferStatus;
        this.accountFrom = accountFrom;
        this.userFrom = userFrom;
        this.accountTo = accountTo;
        this.userTo = userTo;
        this.amount = amount;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "\n--------------------------------------------" +
                "\n Transfer Details" +
                "\n--------------------------------------------" +
                "\n Id: " + transferId +
                "\n From: " + userFrom +
                "\n To: " + userTo +
                "\n Type: " + transferType +
                "\n Status: " + transferStatus +
                "\n Amount: $" + amount;
    }
}
