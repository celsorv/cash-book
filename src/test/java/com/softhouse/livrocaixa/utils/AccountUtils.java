package com.softhouse.livrocaixa.utils;

import com.softhouse.livrocaixa.dto.request.AccountInput;
import com.softhouse.livrocaixa.dto.request.AccountInputId;
import com.softhouse.livrocaixa.dto.response.AccountOutput;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.mapper.AccountMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountUtils {

    private static final Long ACCOUNT_ID = 1L;
    private static final String DESCRIPTION = "Caixa";
    private static final LocalDate OPENING_BALANCE_DATE = LocalDate.of(2020, 12, 31);
    private static final BigDecimal OPENING_BALANCE_AMOUNT = BigDecimal.ONE;

    public static AccountInput createFakeInput() {
        return AccountInput.builder()
                .id(ACCOUNT_ID)
                .description(DESCRIPTION)
                .openingBalanceDate(OPENING_BALANCE_DATE)
                .openingBalanceAmount(OPENING_BALANCE_AMOUNT)
                .build();
    }

    public static AccountInputId createFakeInputId() {
        return AccountInputId.builder().id(ACCOUNT_ID).build();
    }

    public static AccountOutput createFakeOutput() {
        return AccountMapper.INSTANCE.toDTO(createFakeEntity());
    }

    public static Account createFakeEntity() {
        return Account.builder()
                .id(ACCOUNT_ID)
                .description(DESCRIPTION)
                .openingBalanceDate(OPENING_BALANCE_DATE)
                .openingBalanceAmount(OPENING_BALANCE_AMOUNT)
                .build();
    }

}
