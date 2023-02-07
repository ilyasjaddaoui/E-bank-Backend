package com.example.EbankBackend.web;

import com.example.EbankBackend.dtos.*;
import com.example.EbankBackend.exceptions.BalanceNotSufficientException;
import com.example.EbankBackend.exceptions.BankAccountNotFound;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;
import com.example.EbankBackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts")
    public List<AccountDTO> accountDTOList(){
        return bankAccountService.AccountList();
    }
    @GetMapping("/accounts/{accountId}")
    public AccountDTO getAccount(@PathVariable String accountId) throws CustomerNotFoundException {
        return bankAccountService.getAccount(accountId);
    }
    @GetMapping("/accounts/operations")
    public List<OperationDTO> getOperation(){
        return bankAccountService.operations();
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debitDTO(@RequestBody DebitDTO debitDTO) throws BankAccountNotFound, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO creditDTO(@RequestBody CreditDTO creditDTO) throws BankAccountNotFound {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void TransferDTO (@RequestBody TransferDTO transferDTO) throws BankAccountNotFound, BalanceNotSufficientException {
        this.bankAccountService.transfer(
                transferDTO.getAccountSource(),
                transferDTO.getAccountDestination(),
                transferDTO.getAmount()
        );

    }
}
