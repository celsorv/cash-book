package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.dto.request.AccountInput;
import com.softhouse.livrocaixa.dto.response.AccountOutput;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.exception.AccountNotFoundException;
import com.softhouse.livrocaixa.mapper.AccountMapper;
import com.softhouse.livrocaixa.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.softhouse.livrocaixa.utils.AccountUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testGivenAccountInputThenReturnSavedAccount() {

        AccountInput accountInput = createFakeInput();
        Account expectedSavedAccount = createFakeEntity();

        when(accountRepository.save(any(Account.class))).thenReturn(expectedSavedAccount);

        Account accountToSave = generateModel(accountInput);
        Account savedAccount = accountService.create(accountToSave);

        AccountOutput expectedSuccessOutput = generateOutputDto(expectedSavedAccount);
        AccountOutput successOutput = generateOutputDto(savedAccount);

        assertEquals(expectedSuccessOutput, successOutput);

    }

    @Test
    void testGivenValidAccountIdThenReturnThisAccount() {

        AccountInput expectedAccountInput = createFakeInput();
        Account expectedSavedAccount = createFakeEntity();
        expectedAccountInput.setId(expectedSavedAccount.getId());

        AccountOutput successAccountOutput = generateOutputDto(expectedSavedAccount);

        when(accountRepository.findById(expectedSavedAccount.getId())).thenReturn(Optional.of(expectedSavedAccount));
        AccountOutput successOutput = generateOutputDto(accountService.getById(expectedSavedAccount.getId()));

        assertEquals(successAccountOutput, successOutput);

    }

    @Test
    void testGivenInvalidAccountIdThenThrowException() {

        Long invalidAccountId = 1L;
        when(accountRepository.findById(invalidAccountId))
                .thenReturn(Optional.ofNullable(any(Account.class)));

        assertThrows(AccountNotFoundException.class, () -> accountService.getById(invalidAccountId));

    }

    @Test
    void testGivenNoDataThenReturnAllAccountRegistered() {

        List<Account> expectedRegisteredAccount = Collections.singletonList(createFakeEntity());
        AccountOutput accountOutput = createFakeOutput();

        when(accountRepository.findAll()).thenReturn(expectedRegisteredAccount);

        List<AccountOutput> expectedAccountOutputList = generateOutputDtoList(accountService.getAll());

        assertFalse(expectedAccountOutputList.isEmpty());
        assertEquals(expectedAccountOutputList.get(0).getId(), accountOutput.getId());

    }

    @Test
    void testGivenValidAccountIdAndUpdateInfoThenReturnUpdatedAccount() {

        Long updatedAccountId = 1L;

        AccountInput updateAccountInputRequest = createFakeInput();
        updateAccountInputRequest.setId(updatedAccountId);
        updateAccountInputRequest.setDescription("Nova descrição");

        Account expectedAccountToUpdate = createFakeEntity();
        expectedAccountToUpdate.setId(updatedAccountId);

        Account expectedAccountUpdated = createFakeEntity();
        expectedAccountUpdated.setId(updatedAccountId);
        expectedAccountToUpdate.setDescription(updateAccountInputRequest.getDescription());

        when(accountRepository.findById(updatedAccountId)).thenReturn(Optional.of(expectedAccountUpdated));
        when(accountRepository.save(any(Account.class))).thenReturn(expectedAccountUpdated);


        Account accountToSave = generateModel(updateAccountInputRequest);
        Account savedAccount = accountService.update(updatedAccountId, accountToSave);

        AccountOutput expectedSuccessOutput = generateOutputDto(expectedAccountUpdated);
        AccountOutput successOutput = generateOutputDto(savedAccount);

        assertEquals(expectedSuccessOutput, successOutput);

    }

    @Test
    void testGivenValidAccountIdeThenReturnSuccessOnDelete() {

        Long deletedAccountId = 1L;
        accountService.delete((deletedAccountId));

        verify(accountRepository, times(1)).deleteById(deletedAccountId);

    }

    @Test
    void testGivenInvalidAccountIdToDeleteThenThrowException() {

        Long invalidAccountId = 1L;

        // Fail
        // Mockito.doThrow(EntityInUseException.class).when(accountRepository).deleteById(invalidAccountId);

        // Success
        Mockito.doThrow(AccountNotFoundException.class).when(accountRepository).deleteById(invalidAccountId);

        assertThrows(AccountNotFoundException.class, () -> accountService.delete(invalidAccountId));

    }

    private Account generateModel(AccountInput accountInput) {
        return AccountMapper.INSTANCE.ToModel(accountInput);
    }

    private AccountOutput generateOutputDto(Account account) {
        return AccountMapper.INSTANCE.toDTO(account);
    }

    private List<AccountOutput> generateOutputDtoList(List<Account> accounts) {
        return AccountMapper.INSTANCE.toDTOList(accounts);
    }

}
