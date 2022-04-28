package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(value = "/transfer")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public boolean sendFunds(@Valid @RequestBody Transfer transfer) throws InvalidTransferException{
        return transferDao.sendFunds(transfer);
    }

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public List<Transfer> getListOfTransfers(){
        return transferDao.listAllTransfers();
    }


}
