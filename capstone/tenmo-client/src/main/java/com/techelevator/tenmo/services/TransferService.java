package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    AccountHolderService accountHolderService = new AccountHolderService();
    String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    public static final int TYPE_REQUEST = 1;
    public static final int TYPE_SEND = 2;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_APPROVED = 2;
    public static final int STATUS_REJECTED = 3;


    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public TransferService() {
        this.API_BASE_URL = AccountHolderService.API_BASE_URL;
    }

    public boolean sendingFunds(BigDecimal transferAmount, int userIdToSend) {
        accountHolderService.setAuthToken(authToken);
        int accountToId = getAccountIdFromContacts(accountHolderService.getContactList(), userIdToSend);
        // set transfer status' in the future
        // set transferId status to pending

        boolean success = false;
        Transfer transfer = new Transfer();
        transfer.setTransferTypeId(TYPE_SEND);
        transfer.setTransferType(getTransferTypeAsString(TYPE_SEND));
        transfer.setTransferStatusId(STATUS_PENDING);
        transfer.setTransferStatus(getTransferStatusAsString(STATUS_PENDING));
        transfer.setAccountToId(accountToId);
        transfer.setTransferAmount(transferAmount);
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        try {
            success = restTemplate.postForObject(API_BASE_URL + "transfer/send", entity, boolean.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public int getAccountIdFromContacts(AccountHolder[] contacts, int userToSend) {
        int accountToId = 0;
        for (AccountHolder accountHolder : contacts) {
            if (accountHolder.getUserId() == userToSend) {
                accountToId = accountHolder.getAccountId();
            }
        }
        return accountToId;
    }

    public Transfer[] getTransferList() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "transfer/list",
                            HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer getTransferById(int transferId) {
        Transfer transferToReturn = null;
        for (Transfer transfer : getTransferList()) {
            if (transfer.getTransferId() == transferId) {
                transferToReturn = transfer;
            }
        }
        return transferToReturn;
    }

    public String getTransferTypeAsString(int typeId) {
        if (typeId == 1) {
            return "Request";
        } else {
            return "Send";
        }
    }

    public String getTransferStatusAsString(int statusId) {  //might need another model
        if (statusId == 1) {
            return "Pending";
        } else if (statusId == 2) {
            return "Approved";
        } else {
            return "Rejected";
        }
    }

    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }
}
