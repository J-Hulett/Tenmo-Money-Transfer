package com.techelevator.tenmo.model;

import com.techelevator.tenmo.services.AccountHolderService;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int transferTypeId;
    private String transferType;
    private int transferStatusId;
    private String transferStatus;
    private int accountToId;
    private int accountFromId;
    private BigDecimal transferAmount;

    public Transfer() {
    }

    public String viewTransferToString(int userId, AccountHolderService accountHolderService) {
        int currentUser = accountHolderService.getAccountHolderByUserId(userId).getAccountId();
        String toOrFrom = (accountFromId == currentUser) ? "To: " : "From: ";

        String nameOnAccount = accountHolderService.getAccountHolderByUserId(userId).getUsername();
        String thinkofname = (nameOnAccount == )

        return transferId + "          " + toOrFrom + " " + + "          $ " + transferAmount;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
