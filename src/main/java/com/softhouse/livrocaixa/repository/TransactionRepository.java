package com.softhouse.livrocaixa.repository;

import com.softhouse.livrocaixa.dto.response.CashBookDto;
import com.softhouse.livrocaixa.dto.response.CostCentreMapDto;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("""
        SELECT SUM(CASE WHEN type = 'CREDIT' THEN value ELSE -value END) FROM Transaction
                    WHERE account = ?1 AND date BETWEEN ?2 AND ?3
    """)
    BigDecimal getAccountBalance(Account account, LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT NEW com.softhouse.livrocaixa.dto.response.CashBookDto(t.date, t.narration, t.type, t.value)
                    FROM Transaction t WHERE account = ?1 AND date BETWEEN ?2 AND ?3 ORDER BY account, date
    """)
    List<CashBookDto> getAccountTransactions(Account account, LocalDate startDate, LocalDate endDate);

    @Query(nativeQuery = true)
    List<CostCentreMapDto> getCostCentreMap(Long id, LocalDate startDate, LocalDate endDate);

}
