package com.techelevator.util;

public class TransferNotAllowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public TransferNotAllowedException() {
        super("Invalid Transfer Request!");
    }

}
