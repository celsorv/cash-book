package com.softhouse.livrocaixa.repository;

import com.softhouse.livrocaixa.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
