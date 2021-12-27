package com.softhouse.livrocaixa.mapper;

import com.softhouse.livrocaixa.dto.request.TransactionInput;
import com.softhouse.livrocaixa.dto.response.TransactionOutput;
import com.softhouse.livrocaixa.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    Transaction ToModel(TransactionInput transactionInput);

    TransactionOutput toDTO(Transaction transaction);

    List<TransactionOutput> toDTOList(List<Transaction> transactions);

}
