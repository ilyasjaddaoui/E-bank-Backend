package com.example.EbankBackend.services;

import com.example.EbankBackend.dtos.*;
import com.example.EbankBackend.entities.*;
import com.example.EbankBackend.enums.OperationType;
import com.example.EbankBackend.exceptions.BalanceNotSufficientException;
import com.example.EbankBackend.exceptions.BankAccountNotFound;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;
import com.example.EbankBackend.mappers.BankAccountMapper;
import com.example.EbankBackend.repositories.AccountRepository;
import com.example.EbankBackend.repositories.CustomerRepository;
import com.example.EbankBackend.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private CustomerRepository customerRepository;
    private BankAccountMapper bankAccountMapper;

    @Override
    public AccountDTO getAccount(String id) throws CustomerNotFoundException {
        Account account=accountRepository.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        if (account instanceof Saving){
            Saving saving= (Saving) account;
            return bankAccountMapper.fromSaving(saving);
        }else {
            Current current= (Current) account;
            return bankAccountMapper.fromCurrent(current);
        }
    }

    @Override
    public List<AccountDTO> AccountList() {
        List<Account> accounts=accountRepository.findAll();
        List<AccountDTO> accountDTOS=accounts.stream().map(account -> {
            if (account instanceof Saving){
                Saving saving= (Saving) account;
                return bankAccountMapper.fromSaving(saving);
            }else {
                Current current= (Current) account;
                return bankAccountMapper.fromCurrent(current);
            }
        }).collect(Collectors.toList());
        return accountDTOS;
    }

    @Override
    public CurrentDTO addCurrentAccount(double balance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer Not found"));
        Current current=new Current();
        current.setId(UUID.randomUUID().toString());
        current.setCratedAt(new Date());
        current.setBalance(balance);
        current.setOverDraft(overDraft);
        current.setCustomer(customer);
        Current savedAccount=accountRepository.save(current);
        return bankAccountMapper.fromCurrent(savedAccount);
    }

    @Override
    public SavingDTO addSavingAccount(double balance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        Saving saving=new Saving();
        saving.setId(UUID.randomUUID().toString());
        saving.setCratedAt(new Date());
        saving.setBalance(balance);
        saving.setInterestRate(interestRate);
        saving.setCustomer(customer);
        Saving savedAccount=accountRepository.save(saving);
        return bankAccountMapper.fromSaving(savedAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFound, BalanceNotSufficientException {
        Account account=accountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFound("Account not found"));
        if (account.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        Operation operation=new Operation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setAccount(account);
        operationRepository.save(operation);
        // la mise a jour du compte
        account.setBalance(account.getBalance()-amount);
        accountRepository.save(account);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFound {

        Account account=accountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFound("Account not found"));
        Operation operation=new Operation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setAccount(account);
        operationRepository.save(operation);
        // la mise a jour du compte
        account.setBalance(account.getBalance()+amount);
        accountRepository.save(account);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFound, BalanceNotSufficientException {

        debit(accountIdSource, amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination, amount,"Transfer from "+accountIdSource);
    }

    @Override
    public List<OperationDTO> operations() {
        List<Operation> operations=operationRepository.findAll();
        List<OperationDTO> operationDTOS=operations.stream().map(operation -> bankAccountMapper.fromOperation(operation))
                .collect(Collectors.toList());
        return operationDTOS;
    }
}
