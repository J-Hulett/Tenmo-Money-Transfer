package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AccountHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class AccountHolderServiceTest {

    AccountHolder[] testContacts;

    AccountHolderService accountHolderService;

    @Before
    public void setUp() throws Exception {
        testContacts = new AccountHolder[3];
        AccountHolder accountHolder1 = new AccountHolder("matt",2001,new BigDecimal(1000),1001);
        AccountHolder accountHolder2 = new AccountHolder("justin",2002,new BigDecimal(730),1002);
        AccountHolder accountHolder3 = new AccountHolder("cymanthia",2003,new BigDecimal(15000),1003);
        testContacts[0] = accountHolder1;
        testContacts[1] = accountHolder2;
        testContacts[2] = accountHolder3;

        accountHolderService = new AccountHolderService();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_Returns_Proper_Username_For_Account_Number_1(){
        //Arrange
        String expected = "matt";
        //Act
        String actual = accountHolderService.getUsernameByAccountId(2001);
        //Assert

    }
}
