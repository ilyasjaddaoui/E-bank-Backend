package com.example.EbankBackend.services;

import com.example.EbankBackend.dtos.AccountDTO;
import com.example.EbankBackend.dtos.CurrentDTO;
import com.example.EbankBackend.dtos.OperationDTO;
import com.example.EbankBackend.dtos.SavingDTO;
import com.example.EbankBackend.exceptions.BalanceNotSufficientException;
import com.example.EbankBackend.exceptions.BankAccountNotFound;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    AccountDTO getAccount(String id) throws CustomerNotFoundException;
    List<AccountDTO> AccountList();
    CurrentDTO addCurrentAccount(double balance,double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingDTO addSavingAccount(double balance, double interestRate, Long customerId) throws CustomerNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFound, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFound;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFound, BalanceNotSufficientException;
    List<OperationDTO> operations();



}
