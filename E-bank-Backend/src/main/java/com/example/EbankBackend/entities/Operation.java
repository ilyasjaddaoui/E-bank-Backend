package com.example.EbankBackend.entities;

import com.example.EbankBackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private Account account;


}
