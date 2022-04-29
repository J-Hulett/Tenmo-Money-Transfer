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

    private String authToken = null;
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }


    public TransferService() {
        this.API_BASE_URL = AccountHolderService.API_BASE_URL;
    }

    public boolean sendingFunds(BigDecimal transferAmount, int userIdToSend){
         accountHolderService.setAuthToken(authToken);
         int accountToId = accountHolderService.getAccountHolderByUserId(userIdToSend).getAccountId();


         boolean success = false;
         Transfer transfer = new Transfer();
             transfer.setTransferTypeId(2);
             transfer.setTransferType(getTransferTypeAsString(2));
             transfer.setTransferStatusId(2);
             transfer.setTransferStatus(getTransferStatusAsString(2));
             transfer.setAccountToId(accountToId);
             transfer.setTransferAmount(transferAmount);
             HttpEntity<Transfer> entity = makeTransferEntity(transfer);
             try {
                 success = restTemplate.postForObject(API_BASE_URL + "transfer/send",entity, boolean.class);
             } catch (RestClientResponseException | ResourceAccessException e) {
                 BasicLogger.log(e.getMessage());
             }
        return success;
    }

    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer,headers);
    }

    public String getTransferTypeAsString(int typeId) {
        if (typeId == 1) {
            return "Request";
        } else {
            return "Send";
        }
    }

    public String getTransferStatusAsString(int statusId){  //might need another model
        if (statusId == 1) {
            return "Pending";
        } else if (statusId == 2) {
            return "Approved";
        } else {
            return "Rejected";
        }
    }

    public Transfer[] getTransferList(){
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

    public HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
