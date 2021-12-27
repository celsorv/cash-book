package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.dto.response.CashBookDto;
import com.softhouse.livrocaixa.dto.response.CostCentreMapDto;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.entity.Transaction;
import com.softhouse.livrocaixa.exception.AccountNotFoundException;
import com.softhouse.livrocaixa.exception.BusinessException;
import com.softhouse.livrocaixa.exception.EntityInUseException;
import com.softhouse.livrocaixa.exception.TransactionNotFoundException;
import com.softhouse.livrocaixa.repository.AccountRepository;
import com.softhouse.livrocaixa.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static com.softhouse.livrocaixa.enums.TransactionType.CREDIT;
import static com.softhouse.livrocaixa.enums.TransactionType.DEBIT;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private static final String MSG_UNABLE_TO_DELETE
            = "Unable to delete. Transaction id %d is referenced by other records.";

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Transaction create(Transaction transaction) {
        transaction.setId(null);
        validationBeforePersists(transaction);
        return transactionRepository.save(transaction);
    }

    public Transaction update(Long id, Transaction transaction) {
        transaction.setId(id);
        validationBeforePersists(transaction);
        getById(id);
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        try {
            transactionRepository.deleteById(id);
            transactionRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new TransactionNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_UNABLE_TO_DELETE, id));
        }
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<CashBookDto> getCashBook(Long id, Integer month, Integer year) {

        if (month == null) month = LocalDate.now().getMonthValue();
        if (year == null) year = LocalDate.now().getYear();

        if (month < 1 || month > 12 || year < 2000)
            throw new BusinessException("The month and/or year entered as parameters are invalid values.");

        Account account = getAccount(id);

        LocalDate firstDayDate = LocalDate.of(year, month, 1);
        LocalDate lastDayDate = firstDayDate.with(TemporalAdjusters.lastDayOfMonth());

        LocalDate firstDateBalanceBF = account.getOpeningBalanceDate();
        LocalDate lastDateBalanceBF = firstDayDate.minusDays(1);

        BigDecimal balanceBFAmount = transactionRepository.getAccountBalance(account,
                                            firstDateBalanceBF, lastDateBalanceBF);

        if (balanceBFAmount == null) balanceBFAmount = BigDecimal.ZERO;
        balanceBFAmount = balanceBFAmount.add(account.getOpeningBalanceAmount());

        List<CashBookDto> list = transactionRepository.getAccountTransactions(account,
                firstDayDate, lastDayDate);

        BigDecimal balanceCFAmount = list.stream()
                                         .map(e -> CREDIT.equals(e.getType()) ? e.getValue() : e.getValue().negate())
                                         .reduce(BigDecimal.ZERO, BigDecimal::add);

        balanceCFAmount = balanceCFAmount.add(balanceBFAmount);

        CashBookDto balanceBFTransaction = CashBookDto.builder()
                .date(lastDateBalanceBF)
                .value(balanceBFAmount)
                .narration("(Brought Forward) Balance B/F")
                .type(balanceBFAmount.compareTo(BigDecimal.ZERO) > 0 ? CREDIT : DEBIT)
                .build();

        CashBookDto balanceCFTransaction = CashBookDto.builder()
                .date(lastDayDate)
                .value(balanceCFAmount)
                .narration("(Carried Forward) Balance C/F")
                .type(balanceCFAmount.compareTo(BigDecimal.ZERO) > 0 ? CREDIT : DEBIT)
                .build();

        list.add(0, balanceBFTransaction);
        list.add(balanceCFTransaction);

        return list;

    }

    public List<CostCentreMapDto> getCostCentreMap(Long id, Integer month, Integer year) {

        if (month == null) month = LocalDate.now().getMonthValue();
        if (year == null) year = LocalDate.now().getYear();

        if (month < 1 || month > 12 || year < 2000)
            throw new BusinessException("The month and/or year entered as parameters are invalid values.");

        LocalDate firstDayDate = LocalDate.of(year, month, 1);
        LocalDate lastDayDate = firstDayDate.with(TemporalAdjusters.lastDayOfMonth());

        return transactionRepository.getCostCentreMap(id, firstDayDate, lastDayDate);

    }

    private void validationBeforePersists(Transaction transaction) {

        final CostCentre costCentre = transaction.getCostCentre();

        if (costCentre != null) {
            final Boolean analyticalAccount = costCentre.getAnalyticalAccount();
            if (analyticalAccount != null && !analyticalAccount)
                throw new BusinessException("This cost center does not accept transactions.");
        }

        final Account account = getAccount(transaction.getAccount().getId());
        final LocalDate openingBalanceDate = account.getOpeningBalanceDate();

        if (transaction.getDate().isBefore(openingBalanceDate))
            throw new BusinessException(
                    String.format("Transaction must have a date later than the opening balance date (%s).",
                            openingBalanceDate));

    }

    private Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

}
