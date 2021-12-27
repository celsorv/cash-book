package com.softhouse.livrocaixa.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.softhouse.livrocaixa.dto.request.TransactionInput;
import com.softhouse.livrocaixa.dto.response.CashBookDto;
import com.softhouse.livrocaixa.dto.response.CostCentreMapDto;
import com.softhouse.livrocaixa.dto.response.MessageResponseDto;
import com.softhouse.livrocaixa.dto.response.TransactionOutput;
import com.softhouse.livrocaixa.dto.response.view.TransactionView;
import com.softhouse.livrocaixa.entity.Transaction;
import com.softhouse.livrocaixa.mapper.TransactionMapper;
import com.softhouse.livrocaixa.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

    private final TransactionService transactionService;

    private final TransactionMapper transactionMapper = TransactionMapper.INSTANCE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto create(@RequestBody @Valid TransactionInput transactionInput) {
        Transaction transactionToSave = transactionMapper.ToModel(transactionInput);
        Transaction savedTransaction = transactionService.create(transactionToSave);
        return createMessageResponse(savedTransaction.getId(), "Created transaction with ID");
    }

    @PutMapping("/{id}")
    public MessageResponseDto update(@PathVariable Long id, @RequestBody @Valid TransactionInput transactionInput) {
        Transaction transactionToUpdate = transactionMapper.ToModel(transactionInput);
        Transaction savedTransaction = transactionService.update(id, transactionToUpdate);
        return createMessageResponse(savedTransaction.getId(), "Updated transaction with ID");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        transactionService.delete(id);
    }

    @JsonView(TransactionView.DescriptionOnly.class)
    @GetMapping("/{id}")
    public TransactionOutput getById(@PathVariable Long id) {
        Transaction transaction = transactionService.getById(id);
        return transactionMapper.toDTO(transaction);
    }

    @JsonView(TransactionView.DescriptionOnly.class)
    @GetMapping
    public List<TransactionOutput> getAll() {
        return transactionMapper.toDTOList(transactionService.getAll());
    }

    @GetMapping("/{id}/cashbook")
    public List<CashBookDto> getCashBook(@PathVariable Long id,
                     @RequestParam(required = false) Integer month,
                                         @RequestParam(required = false) Integer year) {

        return transactionService.getCashBook(id, month, year);
    }

    @GetMapping("/{id}/cost-centre-map")
    public List<CostCentreMapDto> getCostCentreMap(@PathVariable Long id,
                                                   @RequestParam(required = false) Integer month,
                                                   @RequestParam(required = false) Integer year) {

        return transactionService.getCostCentreMap(id, month, year);
    }

    private MessageResponseDto createMessageResponse(long id, String message) {
        return MessageResponseDto
                .builder()
                .message(message + " " + id)
                .build();
    }

}
