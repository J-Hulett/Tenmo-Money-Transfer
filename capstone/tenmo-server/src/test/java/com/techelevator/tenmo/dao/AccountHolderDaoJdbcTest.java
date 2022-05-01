package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidAccountNumber;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.exceptions.InvalidUserId;
import com.techelevator.tenmo.model.AccountHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountHolderDaoJdbcTest extends BaseDaoTests{

    AccountHolder accountHolderA;
    AccountHolder accountHolderB;
    AccountHolder accountHolderC;
    List<AccountHolder> testList;
    private AccountHolderDaoJdbc sut;

    @Before
    public void setup() throws Exception {
      //  sut = new AccountHolderDaoJdbc(dataSource);
        accountHolderA = new AccountHolder("UserA", 2001, new BigDecimal("1000.00"), 1001);
        accountHolderB = new AccountHolder("UserB", 2002, new BigDecimal("1000.00"), 1002);
        accountHolderC = new AccountHolder("UserC", 2003, new BigDecimal("1000.00"), 1003);
        testList = new ArrayList<>();
        testList.add(accountHolderA);
        testList.add(accountHolderB);
        testList.add(accountHolderC);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returns_userA_As_Account_Holder_By_Account_ID() throws InvalidAccountNumber {

        int accountId = 2001;
        AccountHolder testPerson = accountHolderA;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByAccountId(accountId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_userB_As_Account_Holder_By_Account_ID() throws InvalidAccountNumber{
        int accountId = 2002;
        AccountHolder testPerson = accountHolderB;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByAccountId(accountId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_userC_As_Account_Holder_By_Account_ID() throws InvalidAccountNumber {
        int accountId = 2003;
        AccountHolder testPerson = accountHolderC;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByAccountId(accountId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_null_when_Account_Id_Doesnt_Exist() throws InvalidAccountNumber{
        int accountId = 2010;

        AccountHolder actualResult = sut.getAccountHolderByAccountId(accountId);

        Assert.assertNull(actualResult.getUsername());
        Assert.assertEquals(0, actualResult.getAccountId());
        Assert.assertNull(actualResult.getBalance());
        Assert.assertEquals(0, actualResult.getUserId());
    }

    @Test
    public void returns_userA_As_Account_Holder_By_User_ID() throws InvalidUserId {
        int userId = 1001;
        AccountHolder testPerson = accountHolderA;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByUserId(userId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_userB_As_Account_Holder_By_User_ID() throws InvalidUserId{
        int userId = 1002;
        AccountHolder testPerson = accountHolderB;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByUserId(userId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_userC_As_Account_Holder_By_User_ID() throws InvalidUserId{
        int userId = 1003;
        AccountHolder testPerson = accountHolderC;
        String expectedName = testPerson.getUsername();
        int expectedAccount = testPerson.getAccountId();
        BigDecimal expectedBalance = testPerson.getBalance();
        int expectedId = testPerson.getUserId();

        AccountHolder actualPerson = sut.getAccountHolderByUserId(userId);
        String  actualName = actualPerson.getUsername();
        int  actualAccount = actualPerson.getAccountId();
        BigDecimal  actualBalance = actualPerson.getBalance();
        int  actualId = actualPerson.getUserId();

        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedAccount, actualAccount);
        Assert.assertEquals(expectedBalance, actualBalance);
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void returns_null_when_User_Id_Doesnt_Exist() throws InvalidUserId {
        int userId = 1010;

        AccountHolder actualResult = sut.getAccountHolderByUserId(userId);

        Assert.assertNull(actualResult.getUsername());
        Assert.assertEquals(0, actualResult.getAccountId());
        Assert.assertNull(actualResult.getBalance());
        Assert.assertEquals(0, actualResult.getUserId());
    }

    @Test
    public void returns_a_list_of_account_holders_that_doesnt_include_current_user() throws InvalidUserId{
        int userId = 1001;

        int expectedSize = 2;
        List<AccountHolder> actualList = sut.getListOfOtherAccountHoldersByUserId(userId);

        Assert.assertEquals(expectedSize, actualList.size());
    }
}
