package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountHolderDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.Transfer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/transfer")
public class TransferController {

    private TransferDao transferDao;
    private AccountHolderDao accountHolderDao;
    private JdbcUserDao jdbcUserDao;
    public static final int TYPE_REQUEST = 1;
    public static final int TYPE_SEND = 2;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_APPROVED = 2;
    public static final int STATUS_REJECTED = 3;

    public TransferController(TransferDao transferDao, AccountHolderDao accountHolderDao, JdbcUserDao jdbcUserDao) {
        this.accountHolderDao = accountHolderDao;
        this.transferDao = transferDao;
        this.jdbcUserDao = jdbcUserDao;
    }
    @ApiOperation("Sends Funds From One User To Another")
    @ApiParam
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public boolean sendFunds(@Valid @RequestBody Transfer transfer, Principal principal) throws InvalidTransferException {
        boolean success = false;
        if (hasEnoughMoney(transfer, currentAccountHolder(principal))
                && notSameAccount(transfer, currentAccountHolder(principal))
                && isAccount(transfer)) {
            transfer.setAccountFromId(currentAccountHolder(principal).getAccountId());
            transfer.setTransferTypeId(TYPE_SEND);
            transfer.setTransferType(getTransferTypeAsString(TYPE_SEND));
            transfer.setTransferStatusId(STATUS_APPROVED);
            transfer.setTransferStatus(getTransferStatusAsString(STATUS_APPROVED));
            transferDao.sendFunds(transfer);
            success = true;
        }
        return success;
    }

    @ApiOperation("Sends A Request To The Server To Await Approval")
    @ApiParam
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/send/request", method = RequestMethod.POST)
    public boolean sendRequest(@Valid @RequestBody Transfer transfer, Principal principal) throws InvalidTransferException {
        boolean success = false;
        if (notSameAccount(transfer, currentAccountHolder(principal))
                && isAccount(transfer)) {
            transfer.setAccountToId(currentAccountHolder(principal).getAccountId());
            transfer.setTransferTypeId(TYPE_REQUEST);
            transfer.setTransferType(getTransferTypeAsString(TYPE_REQUEST));
            transfer.setTransferStatusId(STATUS_PENDING);
            transfer.setTransferStatus(getTransferStatusAsString(STATUS_PENDING));
            transferDao.initiateTransfer(transfer);
            success = true;
        }
        return success;
    }

    @ApiOperation("Rejects A Given Request")
    @ApiParam
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/reject", method = RequestMethod.PUT)
    public boolean rejectRequest(@Valid @RequestBody Transfer transfer){
        transfer.setTransferStatusId(STATUS_REJECTED);
        transfer.setTransferStatus(getTransferStatusAsString(STATUS_REJECTED));
        return transferDao.rejectTransfer(transfer);
    }

    @ApiOperation("Accepts A Given Request")
    @ApiParam
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/accept", method = RequestMethod.PUT)
    public boolean acceptRequest(@Valid @RequestBody Transfer transfer, Principal principal) throws InvalidTransferException{
        boolean success = false;
        if (hasEnoughMoney(transfer, currentAccountHolder(principal))
                && notSameAccount(transfer, currentAccountHolder(principal))
                && isAccount(transfer)) {
            transfer.setTransferStatusId(STATUS_APPROVED);
            transfer.setTransferStatus(getTransferStatusAsString(STATUS_APPROVED));
            transferDao.acceptTransfer(transfer);
            success = true;
        }
        return success;
    }

    @ApiOperation("Gets A List Of Transfers")
    @ApiParam
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(Principal principal) {
        int accountId = currentAccountHolder(principal).getAccountId();
        return transferDao.listAllTransfers(accountId);
    }

    public AccountHolder currentAccountHolder(Principal principal) {
        int currentUserId = jdbcUserDao.findIdByUsername(principal.getName());
        AccountHolder currentAccountHolder = accountHolderDao.getAccountHolderByUserId(currentUserId);
        return currentAccountHolder;
    }

    public boolean hasEnoughMoney(Transfer transfer, AccountHolder currentAccountHolder) {
        return (currentAccountHolder.getBalance().compareTo(transfer.getTransferAmount()) != -1);
    }

    public boolean notSameAccount(Transfer transfer, AccountHolder currentAccountHolder) {
        return currentAccountHolder.getAccountId() != transfer.getAccountToId();
    }

    public boolean isAccount(Transfer transfer) {
        return accountHolderDao.getAccountHolderByAccountId(transfer.getAccountToId()) != null;
    }

    public String getTransferStatusAsString(int statusId) {  //might need another model
        if (statusId == 1) {
            return "Pending";
        } else if (statusId == 2) {
            return "Approved";
        } else {
            return "Rejected";
        }
    }

    public String getTransferTypeAsString(int typeId) {
        if (typeId == 1) {
            return "Request";
        } else {
            return "Send";
        }
    }
}
