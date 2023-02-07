package com.example.EbankBackend.exceptions;

public class BankAccountNotFound extends Exception {
    public BankAccountNotFound(String message) {
        super(message);
    }
}
