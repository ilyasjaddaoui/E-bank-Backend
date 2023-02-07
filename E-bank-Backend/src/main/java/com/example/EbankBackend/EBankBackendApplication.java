package com.example.EbankBackend;


import com.example.EbankBackend.dtos.AccountDTO;
import com.example.EbankBackend.dtos.CurrentDTO;
import com.example.EbankBackend.dtos.CustomerDTO;
import com.example.EbankBackend.dtos.SavingDTO;
import com.example.EbankBackend.exceptions.CustomerNotFoundException;
import com.example.EbankBackend.services.BankAccountService;
import com.example.EbankBackend.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.stream.Stream;


@SpringBootApplication
public class EBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBankBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerService customerService, BankAccountService bankAccountService){
		return args -> {
			Stream.of("Ilyas","Amine","Said").forEach(name->{
				CustomerDTO customerDTO=new CustomerDTO();
				customerDTO.setName(name);
				customerDTO.setEmail(name+"@Gmail.com");
				customerService.addCustomer(customerDTO);
			});
			customerService.getAll().forEach(customer->{
				try {
					bankAccountService.addCurrentAccount(Math.random()*90000,9000,customer.getId());
					bankAccountService.addSavingAccount(Math.random()*120000,5.5,customer.getId());
				}catch (CustomerNotFoundException e){
					e.printStackTrace();
				}
			});
			List<AccountDTO> accountDTOS=bankAccountService.AccountList();
			for (AccountDTO accountDTO:accountDTOS){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if (accountDTO instanceof SavingDTO){
						accountId=((SavingDTO) accountDTO).getId();
					}else {
						accountId=((CurrentDTO) accountDTO).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}
		};
	}

}
