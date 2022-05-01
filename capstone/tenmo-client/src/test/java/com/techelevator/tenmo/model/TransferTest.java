package com.techelevator.tenmo.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransferTest {

    AccountHolder[] testContacts;

    Transfer testTransfer;

    Transfer testTransfer2;



    @Before
    public void setUp() throws Exception {
        testContacts = new AccountHolder[3];
        AccountHolder accountHolder1 = new AccountHolder("matt",2001,new BigDecimal(1000),1001);
        AccountHolder accountHolder2 = new AccountHolder("justin",2002,new BigDecimal(730),1002);
        AccountHolder accountHolder3 = new AccountHolder("cymanthia",2003,new BigDecimal(15000),1003);
        testContacts[0] = accountHolder1;
        testContacts[1] = accountHolder2;
        testContacts[2] = accountHolder3;


        testTransfer = new Transfer(3001,2,"Send",2,"Approved",2001,2003,new BigDecimal(5.00));
        testTransfer2 = new Transfer(3002,2,"Send",2,"Approved",2002,2003,new BigDecimal(5.00));
    }

    @After
    public void tearDown() throws Exception {
    }



    @Test
    public void test_For_Account_From_Username(){
        //Arrange
        String expected = "cymanthia";
        String toOrFrom = "From";
        //Act
        String actual = testTransfer.goThroughUsernames(testContacts,toOrFrom);
        //Assert
        Assert.assertEquals("Incorrect Username!",expected, actual);
    }

    @Test
    public void test_For_Account_To_Username(){
        //Arrange
        String expected = "matt";
        String toOrFrom = "To";
        //Act
        String actual = testTransfer.goThroughUsernames(testContacts,toOrFrom);
        //Assert
        Assert.assertEquals("Incorrect Username!",expected, actual);
    }

    @Test
    public void test_For_Account_To_Username2(){
        //Arrange
        String expected = "justin";
        String toOrFrom = "To";
        //Act
        String actual = testTransfer2.goThroughUsernames(testContacts,toOrFrom);
        //Assert
        Assert.assertEquals("Incorrect Username!",expected, actual);
    }

}
