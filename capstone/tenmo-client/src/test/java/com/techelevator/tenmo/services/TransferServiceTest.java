package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    private static final String EXPECTED_API_URL = "http://localhost:8080/";

    private Transfer[] expectedTransfers = new Transfer[3];

    private Transfer testTransfer1;
    private Transfer testTransfer2;
    private Transfer testTransfer3;

    @Mock
    private RestTemplate mockRestTemplate;

    @InjectMocks
    private TransferService sut;


    @Before
    public void setUp() throws Exception {
        testTransfer1 = new Transfer(3009, 2, "Send", 2,
                "Approved", 2002, 2001, new BigDecimal("25.00"));
        testTransfer2 = new Transfer(3004, 1, "Request", 2,
                "Approved", 2001, 2003, new BigDecimal("100.25"));
        testTransfer3 = new Transfer(3003, 1, "Request", 3,
                "Rejected", 2003, 2002, new BigDecimal("100.00"));

        expectedTransfers[0] = testTransfer1;
        expectedTransfers[1] = testTransfer2;
        expectedTransfers[2] = testTransfer3;


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get_Transfer_List_Returns_All_Transfers() {
        //Arrange
        when(mockRestTemplate.getForObject(eq(EXPECTED_API_URL + "transfer/list"),
                eq(Transfer[].class))).thenReturn(expectedTransfers);

        //Act
        Transfer[] actualTransfers = sut.getTransferList();

        //Assert
        Assert.assertArrayEquals(expectedTransfers,actualTransfers);
    }


}
