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
@RequestMapping("/account")
public class AccountHolderController {

    private AccountHolderDao accountHolderDao;

    public AccountHolderController(AccountHolderDao accountHolderDao) {
        this.accountHolderDao = accountHolderDao;
    }

    @RequestMapping(path = "/holder/{id}",method = RequestMethod.GET)
    public AccountHolder getAccountHolderById(@PathVariable int id) {
        return accountHolderDao.getAccountHolderById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<AccountHolder> getOtherAccountHoldersNotAtId(@PathVariable int id) {
        return accountHolderDao.getListOfOtherAccountHolders(id);
    }

}
