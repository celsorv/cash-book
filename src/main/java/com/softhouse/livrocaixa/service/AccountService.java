package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.exception.AccountNotFoundException;
import com.softhouse.livrocaixa.exception.EntityInUseException;
import com.softhouse.livrocaixa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private static final String MSG_UNABLE_TO_DELETE
            = "Unable to delete. Account id %d is referenced by other records.";

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        //verifyIfExistsSubaccount(account);
        account.setId(null);
        return accountRepository.save(account);
    }

    public Account update(Long id, Account account) {
        getById(id);
        //verifyIfExistsSubaccount(account);
        account.setId(id);
        return accountRepository.save(account);
    }

    public void delete(Long id) {
        try {
            accountRepository.deleteById(id);
            accountRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new AccountNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_UNABLE_TO_DELETE, id));
        }
    }

    public Account getById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

}
