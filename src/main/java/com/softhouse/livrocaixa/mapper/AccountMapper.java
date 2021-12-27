package com.softhouse.livrocaixa.mapper;

import com.softhouse.livrocaixa.dto.request.AccountInput;
import com.softhouse.livrocaixa.dto.response.AccountOutput;
import com.softhouse.livrocaixa.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account ToModel(AccountInput accountInput);

    AccountOutput toDTO(Account account);

    List<AccountOutput> toDTOList(List<Account> accounts);

}
