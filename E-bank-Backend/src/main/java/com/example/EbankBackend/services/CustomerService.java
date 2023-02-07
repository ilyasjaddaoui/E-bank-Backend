package com.example.EbankBackend.services;

import com.example.EbankBackend.dtos.CustomerDTO;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAll();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO addCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void delete(Long customerId);
}
