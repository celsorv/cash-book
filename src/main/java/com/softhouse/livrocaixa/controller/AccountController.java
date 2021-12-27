package com.softhouse.livrocaixa.controller;

import com.softhouse.livrocaixa.dto.request.AccountInput;
import com.softhouse.livrocaixa.dto.response.AccountOutput;
import com.softhouse.livrocaixa.dto.response.MessageResponseDto;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.mapper.AccountMapper;
import com.softhouse.livrocaixa.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    private final AccountService accountService;

    private final AccountMapper accountMapper = AccountMapper.INSTANCE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto create(@RequestBody @Valid AccountInput accountInput) {
        Account accountToSave = accountMapper.ToModel(accountInput);
        Account savedAccount = accountService.create(accountToSave);
        return createMessageResponse(savedAccount.getId(), "Created account with ID");
    }

    @PutMapping("/{id}")
    public MessageResponseDto update(@PathVariable Long id, @RequestBody @Valid AccountInput accountInput) {
        Account accountToUpdate = accountMapper.ToModel(accountInput);
        Account savedAccount = accountService.update(id, accountToUpdate);
        return createMessageResponse(savedAccount.getId(), "Updated account with ID");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("/{id}")
    public AccountOutput getById(@PathVariable Long id) {
        Account account = accountService.getById(id);
        return accountMapper.toDTO(account);
    }

    @GetMapping
    public List<AccountOutput> getAll() {
        return accountMapper.toDTOList(accountService.getAll());
    }

    private MessageResponseDto createMessageResponse(long id, String message) {
        return MessageResponseDto
                .builder()
                .message(message + " " + id)
                .build();
    }

}
