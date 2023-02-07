package com.example.EbankBackend.dtos;

import com.example.EbankBackend.enums.OperationType;
import lombok.Data;

import java.util.Date;
@Data
public class OperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
