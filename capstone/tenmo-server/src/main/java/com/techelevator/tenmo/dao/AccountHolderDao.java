package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountHolder;

import java.util.List;

public interface AccountHolderDao {

    //all but the current users account for transfer menu
    List<AccountHolder> getListOfOtherAccountHolders(int accountId);

    AccountHolder getAccountHolderById(int accountId);



}
