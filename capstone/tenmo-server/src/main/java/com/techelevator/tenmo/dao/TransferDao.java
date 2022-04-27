package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer initiateTransfer(Transfer transfer);

    List<Transfer> listAllTransfersByAccount(int accountId);

    Transfer getTransferById(int transferID);

    Transfer sendFunds(Transfer transfer);

}
