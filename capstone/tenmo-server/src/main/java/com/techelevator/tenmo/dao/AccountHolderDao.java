package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountHolder;

import java.math.BigDecimal;
import java.util.List;

public interface AccountHolderDao {

    //all but the current users account for transfer menu
    List<AccountHolder> getListOfOtherAccountHoldersByUserId(int userId);

    AccountHolder getAccountHolderByAccountId(int accountId);

    AccountHolder getAccountHolderByUserId(int userId);
}
