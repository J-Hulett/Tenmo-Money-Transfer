package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountHolderDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.exceptions.InvalidUserId;
import com.techelevator.tenmo.model.AccountHolder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("Get Current Account Holder")
    @ApiParam
    @RequestMapping(path = "/activeHolder", method = RequestMethod.GET)
    public AccountHolder getCurrentAccountHolder(Principal principal) throws InvalidUserId {
        int currentUserId = jdbcUserDao.findIdByUsername(principal.getName());
        return accountHolderDao.getAccountHolderByUserId(currentUserId);
    }

    @ApiOperation("Get List Of Other Account Holders - Contacts")
    @ApiParam
    @RequestMapping(path = "/contacts", method = RequestMethod.GET)
    public List<AccountHolder> getListOfOtherAccountHoldersNotAtUserId(Principal principal) throws InvalidUserId{
        int currentUserId = jdbcUserDao.findIdByUsername(principal.getName());
        return accountHolderDao.getListOfOtherAccountHoldersByUserId(currentUserId);
    }
}
