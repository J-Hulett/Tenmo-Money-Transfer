package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exceptions.InvalidAccountNumber;
import com.techelevator.tenmo.exceptions.InvalidTransferException;
import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.Transfer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TransferDaoJdbcTest extends BaseDaoTests {

    private TransferDaoJdbc sut;
    private AccountHolderDaoJdbc accountHolderSut;
    Transfer testTransfer1;
    Transfer testTransfer2;
    Transfer testTransfer3;
    List<Transfer> testList;

    @Before
    public void setUp() throws Exception {
        sut = new TransferDaoJdbc(dataSource);
        accountHolderSut = new AccountHolderDaoJdbc(dataSource);
        testTransfer1 = new Transfer(3009, 2, "Send", 2,
                "Approved", 2002, 2001, new BigDecimal("25.00"));
        testTransfer2 = new Transfer(3004, 1, "Request", 2,
                "Approved", 2001, 2003, new BigDecimal("100.25"));
        testTransfer3 = new Transfer(3003, 1, "Request", 3,
                "Rejected", 2003, 2002, new BigDecimal("100.00"));
        testList = new ArrayList<>();
        testList.add(testTransfer1);
        testList.add(testTransfer2);
        testList.add(testTransfer3);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void initiate_Transfer_Returns_New_Transfer() throws InvalidTransferException {
        int expectedTransferId = testTransfer1.getTransferId();
        int expectedTransferTypeId = testTransfer1.getTransferTypeId();
        String expectedTransferType = testTransfer1.getTransferType();
        int expectedTransferStatusId = testTransfer1.getTransferStatusId();
        String expectedTransferStatus = testTransfer1.getTransferStatus();
        int expectedAccountToId = testTransfer1.getAccountToId();
        int expectedAccountFromId = testTransfer1.getAccountFromId();
        BigDecimal expectedAmount = testTransfer1.getTransferAmount();

        sut.initiateTransfer(testTransfer1);
        Transfer actual = sut.getTransferById(testTransfer1.getTransferId());

        Assert.assertEquals(expectedTransferId, actual.getTransferId());
        Assert.assertEquals(expectedTransferTypeId, actual.getTransferTypeId());
        Assert.assertEquals(expectedTransferType, actual.getTransferType());
        Assert.assertEquals(expectedTransferStatusId, actual.getTransferStatusId());
        Assert.assertEquals(expectedTransferStatus, actual.getTransferStatus());
        Assert.assertEquals(expectedAccountToId, actual.getAccountToId());
        Assert.assertEquals(expectedAccountFromId, actual.getAccountFromId());
        Assert.assertEquals(expectedAmount, actual.getTransferAmount());
    }

    @Test
    public void reject_Transfer_Updates_Existing_Transfer_To_Rejected_Status() {
        int expectedTransferId = testTransfer3.getTransferId();
        int expectedTransferTypeId = testTransfer3.getTransferTypeId();
        String expectedTransferType = testTransfer3.getTransferType();
        int expectedTransferStatusId = testTransfer3.getTransferStatusId();
        String expectedTransferStatus = testTransfer3.getTransferStatus();
        int expectedAccountToId = testTransfer3.getAccountToId();
        int expectedAccountFromId = testTransfer3.getAccountFromId();
        BigDecimal expectedAmount = testTransfer3.getTransferAmount();

        sut.rejectTransfer(testTransfer3);
        Transfer actual = sut.getTransferById(expectedTransferId);

        Assert.assertEquals(expectedTransferId, actual.getTransferId());
        Assert.assertEquals(expectedTransferTypeId, actual.getTransferTypeId());
        Assert.assertEquals(expectedTransferType, actual.getTransferType());
        Assert.assertEquals(expectedTransferStatusId, actual.getTransferStatusId());
        Assert.assertEquals(expectedTransferStatus, actual.getTransferStatus());
        Assert.assertEquals(expectedAccountToId, actual.getAccountToId());
        Assert.assertEquals(expectedAccountFromId, actual.getAccountFromId());
        Assert.assertEquals(expectedAmount, actual.getTransferAmount());
    }

    @Test
    public void accept_Transfer_Updates_Existing_Transfer_To_Accepted_Status() throws InvalidTransferException {
        int expectedTransferId = testTransfer2.getTransferId();
        int expectedTransferTypeId = testTransfer2.getTransferTypeId();
        String expectedTransferType = testTransfer2.getTransferType();
        int expectedTransferStatusId = testTransfer2.getTransferStatusId();
        String expectedTransferStatus = testTransfer2.getTransferStatus();
        int expectedAccountToId = testTransfer2.getAccountToId();
        int expectedAccountFromId = testTransfer2.getAccountFromId();
        BigDecimal expectedAmount = testTransfer2.getTransferAmount();

        sut.acceptTransfer(testTransfer2);
        Transfer actual = sut.getTransferById(expectedTransferId);

        Assert.assertEquals(expectedTransferId, actual.getTransferId());
        Assert.assertEquals(expectedTransferTypeId, actual.getTransferTypeId());
        Assert.assertEquals(expectedTransferType, actual.getTransferType());
        Assert.assertEquals(expectedTransferStatusId, actual.getTransferStatusId());
        Assert.assertEquals(expectedTransferStatus, actual.getTransferStatus());
        Assert.assertEquals(expectedAccountToId, actual.getAccountToId());
        Assert.assertEquals(expectedAccountFromId, actual.getAccountFromId());
        Assert.assertEquals(expectedAmount, actual.getTransferAmount());
    }

    @Test
    public void accept_Transfer_Updates_Balance_Of_Both_Accounts() throws InvalidTransferException, InvalidAccountNumber {
        AccountHolder accountTo = accountHolderSut.getAccountHolderByAccountId(testTransfer2.getAccountToId());
        AccountHolder accountFrom = accountHolderSut.getAccountHolderByAccountId(testTransfer2.getAccountFromId());

        BigDecimal expectedToBalance = accountTo.getBalance().add(testTransfer2.getTransferAmount());
        BigDecimal expectedFromBalance = accountFrom.getBalance().subtract(testTransfer2.getTransferAmount());

        sut.acceptTransfer(testTransfer2);

        accountTo = accountHolderSut.getAccountHolderByAccountId(testTransfer2.getAccountToId());
        accountFrom = accountHolderSut.getAccountHolderByAccountId(testTransfer2.getAccountFromId());
        
        BigDecimal accountToBalance = accountTo.getBalance();
        BigDecimal accountFromBalance = accountFrom.getBalance();

        Assert.assertEquals(expectedToBalance, accountToBalance);
        Assert.assertEquals(expectedFromBalance, accountFromBalance);
    }

    @Test
    public void list_All_Transfers_For_Current_User_2003_Has_Size_5() {
        int expectedSize = 5;
        int currentUserAccount = 2003;

        List<Transfer> actual = sut.listAllTransfers(currentUserAccount);

        Assert.assertEquals(expectedSize, actual.size());
    }

    @Test
    public void list_All_Transfers_For_Current_User_2001_Has_Size_4() {
        int expectedSize = 4;
        int currentUserAccount = 2001;

        List<Transfer> actual = sut.listAllTransfers(currentUserAccount);

        Assert.assertEquals(expectedSize, actual.size());
    }

    @Test
    public void list_All_Transfers_For_Current_User_2002_Has_Size_7() {
        int expectedSize = 7;
        int currentUserAccount = 2002;

        List<Transfer> actual = sut.listAllTransfers(currentUserAccount);

        Assert.assertEquals(expectedSize, actual.size());
    }

}
