package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.dto.request.TransactionInput;
import com.softhouse.livrocaixa.dto.response.TransactionOutput;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.entity.Transaction;
import com.softhouse.livrocaixa.mapper.TransactionMapper;
import com.softhouse.livrocaixa.repository.AccountRepository;
import com.softhouse.livrocaixa.repository.CostCentreRepository;
import com.softhouse.livrocaixa.repository.TransactionRepository;
import com.softhouse.livrocaixa.utils.AccountUtils;
import com.softhouse.livrocaixa.utils.CostCentreUtils;
import com.softhouse.livrocaixa.utils.TransactionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CostCentreRepository costCentreRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void testGivenCostCentreInputThenReturnSavedCostCentre() {

        TransactionInput transactionInput = TransactionUtils.createFakeInput();
        Transaction expectedSavedTransaction = TransactionUtils.createFakeEntity();
        Account expectedAccount = AccountUtils.createFakeEntity();
        CostCentre expectedCostCentre = CostCentreUtils.createFakeEntity();

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedSavedTransaction);

        Transaction transactionToSave = TransactionMapper.INSTANCE.ToModel(transactionInput);
        Transaction savedTransaction = transactionService.create(transactionToSave);

        TransactionOutput expectedSuccessOutput = TransactionMapper.INSTANCE.toDTO(expectedSavedTransaction);
        TransactionOutput successOutput = TransactionMapper.INSTANCE.toDTO(savedTransaction);

        assertEquals(expectedSuccessOutput, successOutput);

    }

}
