package com.example.EbankBackend.web;

import com.example.EbankBackend.dtos.CustomerDTO;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;
import com.example.EbankBackend.services.BankAccountService;
import com.example.EbankBackend.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
public class CustomerRestController {
    private BankAccountService bankAccountService;
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<CustomerDTO> customerDTOS(){
        return customerService.getAll();
    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.addCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return customerService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.delete(id);
    }
}
