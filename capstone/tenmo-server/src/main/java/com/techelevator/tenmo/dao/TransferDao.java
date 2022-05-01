package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.Transfer;

import java.security.Principal;
import java.util.List;

public interface TransferDao {

    boolean initiateTransfer(Transfer transfer) throws InvalidTransferException;

    List<Transfer> listAllTransfers(int accountId);

    boolean rejectTransfer(Transfer transfer);

    boolean acceptTransfer(Transfer transfer) throws InvalidTransferException;

    boolean sendFunds(Transfer transfer) throws InvalidTransferException;

    Transfer getTransferById(int transferId);
}
