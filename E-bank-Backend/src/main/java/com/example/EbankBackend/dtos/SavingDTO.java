package com.example.EbankBackend.dtos;

import com.example.EbankBackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
@Data
public class SavingDTO extends AccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
