package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer initiateTransfer(Transfer transfer);

    List<Transfer> listAllTransfersByAccount(int accountId, AccountHolderDao accountHolderDao);

    Transfer getTransferById(int transferID);

    boolean sendFunds(Transfer transfer);

}
