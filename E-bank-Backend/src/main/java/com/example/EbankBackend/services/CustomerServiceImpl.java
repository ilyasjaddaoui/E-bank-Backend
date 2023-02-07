package com.example.EbankBackend.services;

import com.example.EbankBackend.dtos.CustomerDTO;
import com.example.EbankBackend.entities.Customer;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;
import com.example.EbankBackend.mappers.BankAccountMapper;
import com.example.EbankBackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service @Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;
    private BankAccountMapper bankAccountMapper;

    @Override
    public List<CustomerDTO> getAll() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> customerDTOS=customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        return bankAccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(savedCustomer) ;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer=bankAccountMapper.fromCustomerDTO(customerDTO);
        Customer updatedCustomer=customerRepository.save(customer);
        return bankAccountMapper.fromCustomer(updatedCustomer) ;
    }

    @Override
    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
