package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer initiateTransfer(Transfer transfer);

    List<Transfer> listAllTransfers();

    Transfer getTransferById(int transferID);

    boolean sendFunds(Transfer transfer) throws InvalidTransferException;

}
