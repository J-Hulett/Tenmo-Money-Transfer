package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidAccountNumber;
import com.techelevator.tenmo.exceptions.InvalidUserId;
import com.techelevator.tenmo.model.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderDao {

    //all but the current users account for transfer menu
    List<AccountHolder> getListOfOtherAccountHoldersByUserId(int userId) throws InvalidUserId;

    AccountHolder getAccountHolderByAccountId(int accountId) throws InvalidAccountNumber;

    AccountHolder getAccountHolderByUserId(int userId) throws InvalidUserId;
}
