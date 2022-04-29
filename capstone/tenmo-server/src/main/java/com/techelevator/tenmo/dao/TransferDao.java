package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.Transfer;

import java.security.Principal;
import java.util.List;

public interface TransferDao {
    Transfer initiateTransfer(Transfer transfer);

    List<Transfer> listAllTransfers(int accountId);

    Transfer getTransferById(int transferID);

    boolean sendFunds(Transfer transfer) throws InvalidTransferException;



}
