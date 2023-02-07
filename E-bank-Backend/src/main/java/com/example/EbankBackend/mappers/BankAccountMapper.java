package com.example.EbankBackend.mappers;

import com.example.EbankBackend.dtos.CurrentDTO;
import com.example.EbankBackend.dtos.CustomerDTO;
import com.example.EbankBackend.dtos.OperationDTO;
import com.example.EbankBackend.dtos.SavingDTO;
import com.example.EbankBackend.entities.Current;
import com.example.EbankBackend.entities.Customer;
import com.example.EbankBackend.entities.Operation;
import com.example.EbankBackend.entities.Saving;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
    public SavingDTO fromSaving(Saving saving){
        SavingDTO savingDTO=new SavingDTO();
        BeanUtils.copyProperties(saving,savingDTO);
        savingDTO.setCustomerDTO(fromCustomer(saving.getCustomer()));
        savingDTO.setType(saving.getClass().getSimpleName());
        return savingDTO;
    }
    public Saving fromSavingDTO(SavingDTO savingDTO){
        Saving saving=new Saving();
        BeanUtils.copyProperties(savingDTO,saving);
        saving.setCustomer(fromCustomerDTO(savingDTO.getCustomerDTO()));
        return saving;
    }
    public CurrentDTO fromCurrent(Current current){
        CurrentDTO currentDTO=new CurrentDTO();
        BeanUtils.copyProperties(current,currentDTO);
        currentDTO.setCustomerDTO(fromCustomer(current.getCustomer()));
        currentDTO.setType(current.getClass().getSimpleName());
        return currentDTO;
    }
    public Current fromCurrentDTO(CurrentDTO currentDTO){
        Current current=new Current();
        BeanUtils.copyProperties(currentDTO,current);
        current.setCustomer(fromCustomerDTO(currentDTO.getCustomerDTO()));
        return current;
    }
    public OperationDTO fromOperation(Operation operation){
        OperationDTO operationDTO=new OperationDTO();
        BeanUtils.copyProperties(operation,operationDTO);
        return operationDTO;
    }

    public Operation fromOperationDTO(OperationDTO operationDTO){
        Operation operation=new Operation();
        BeanUtils.copyProperties(operationDTO,operation);
        return operation;
    }
}
