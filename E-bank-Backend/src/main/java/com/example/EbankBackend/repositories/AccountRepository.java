package com.example.EbankBackend.repositories;

import com.example.EbankBackend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
