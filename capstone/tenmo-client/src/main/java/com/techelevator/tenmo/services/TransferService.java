package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AccountHolder;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import com.techelevator.util.InvalidTransferException;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {

    AccountHolderService accountHolderService = new AccountHolderService();
    String API_BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();

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

        boolean success = false;
        Transfer transfer = new Transfer();
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

    public boolean sendingRequest(BigDecimal requestAmount, int userIdToRequest) {
        accountHolderService.setAuthToken(authToken);
        int accountToRequestId = getAccountIdFromContacts(accountHolderService.getContactList(), userIdToRequest);

        boolean success = false;
        Transfer transfer = new Transfer();
        transfer.setAccountFromId(accountToRequestId);
        transfer.setTransferAmount(requestAmount);
        HttpEntity<Transfer> entity = makeTransferEntity(transfer);
        try {
            success = restTemplate.postForObject(API_BASE_URL + "transfer/send/request", entity, boolean.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

     public boolean acceptOrRejectRequest(int selectedOption, int transferId){
        HttpEntity<Transfer> entity = makeTransferEntity(getTransferById(transferId));
        boolean successful = false;
        switch(selectedOption){
            case 1 :
                try{
                    ResponseEntity<Boolean> success =
                            restTemplate.exchange(API_BASE_URL + "transfer/accept", HttpMethod.PUT , entity, boolean.class);
                    successful = success.getBody();
                } catch (RestClientResponseException | ResourceAccessException e){
                    BasicLogger.log(e.getMessage());
                }
                break;
            case 2 :
                try{
                    ResponseEntity<Boolean> success =
                            restTemplate.exchange(API_BASE_URL + "transfer/reject", HttpMethod.PUT , entity, boolean.class);
                    successful = success.getBody();
                } catch (RestClientResponseException | ResourceAccessException e){
                    BasicLogger.log(e.getMessage());
                }
                break;
            case 0 :
                successful = true;
                break;
            default :
                System.out.println("Invalid Selection - Please Enter a Valid Option");
        }
        return successful;
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
