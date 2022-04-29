package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountHolderDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.AccountHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.AccessController;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/holder")
public class AccountHolderController {

    private AccountHolderDao accountHolderDao;
    private JdbcUserDao jdbcUserDao;

    public AccountHolderController(AccountHolderDao accountHolderDao, JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
        this.accountHolderDao = accountHolderDao;
    }

    // "/holder/{userId}"
//    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
//    public AccountHolder getAccountHolderByUserId(@PathVariable int userId) {
//        return accountHolderDao.getAccountHolderByUserId(userId);
//    }

    @RequestMapping(path = "/activeHolder", method = RequestMethod.GET)
    public AccountHolder getCurrentAccountHolder(Principal principal) {
        int currentUserId = jdbcUserDao.findIdByUsername(principal.getName());
        return accountHolderDao.getAccountHolderByUserId(currentUserId);
    }

    @RequestMapping(path = "/contacts", method = RequestMethod.GET)
    public List<AccountHolder> getListOfOtherAccountHoldersNotAtUserId(Principal principal) {
        int currentUserId = jdbcUserDao.findIdByUsername(principal.getName());
        return accountHolderDao.getListOfOtherAccountHoldersByUserId(currentUserId);
    }


}
