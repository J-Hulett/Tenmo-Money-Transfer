package com.techelevator.tenmo.services;


public class TransferService {

    AccountHolderService accountHolderService = new AccountHolderService();
    String API_BASE_URL;

    public TransferService() {
        this.API_BASE_URL = AccountHolderService.API_BASE_URL;
    }



}
