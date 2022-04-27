package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class AccountHolder {
    private String username;
    private int accountId;
    private BigDecimal balance;

    public AccountHolder() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
