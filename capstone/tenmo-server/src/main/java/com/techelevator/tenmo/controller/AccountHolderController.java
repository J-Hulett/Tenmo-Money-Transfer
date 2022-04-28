package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountHolderDao;
import com.techelevator.tenmo.model.AccountHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.AccessController;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/holder")
public class AccountHolderController {

    private AccountHolderDao accountHolderDao;

    public AccountHolderController(AccountHolderDao accountHolderDao) {
        this.accountHolderDao = accountHolderDao;
    }

//
//    //  "/holder/account/{accountId}"
//    @RequestMapping(path = "/account/{accountId}",method = RequestMethod.GET)
//    public AccountHolder getAccountHolderByAccountId(@PathVariable int accountId) {
//        return accountHolderDao.getAccountHolderByAccountId(accountId);
//    }

    // "/holder/{userId}"
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public AccountHolder getAccountHolderByUserId(@PathVariable int userId){
        return accountHolderDao.getAccountHolderByUserId(userId);
    }


    // "/holder/contacts/{userId}
    @RequestMapping(path = "/contacts/{userId}", method = RequestMethod.GET)
    public List<AccountHolder> getListOfOtherAccountHoldersNotAtUserId(@PathVariable int userId) {
        return accountHolderDao.getListOfOtherAccountHoldersByUserId(userId);
    }

}
