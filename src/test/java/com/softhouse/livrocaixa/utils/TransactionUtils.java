package com.softhouse.livrocaixa.utils;

import com.softhouse.livrocaixa.dto.request.AccountInputId;
import com.softhouse.livrocaixa.dto.request.CostCentreInputId;
import com.softhouse.livrocaixa.dto.request.TransactionInput;
import com.softhouse.livrocaixa.dto.response.AccountOutput;
import com.softhouse.livrocaixa.dto.response.CostCentreOutput;
import com.softhouse.livrocaixa.dto.response.TransactionOutput;
import com.softhouse.livrocaixa.entity.Account;
import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.entity.Transaction;
import com.softhouse.livrocaixa.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionUtils {

    private static final Long TRANSACTION_ID = 1L;
    private static final String NARRATION = "Pago telefone";
    private static final LocalDate TRANSACTION_DATE = LocalDate.now();
    private static final TransactionType TRANSACTION_TYPE = TransactionType.DEBIT;
    private static final BigDecimal TRANSACTION_VALUE = BigDecimal.ONE;

    private static final Account ACCOUNT = AccountUtils.createFakeEntity();
    private static final AccountInputId ACCOUNT_INPUT_ID = AccountUtils.createFakeInputId();
    private static final AccountOutput ACCOUNT_OUTPUT = AccountUtils.createFakeOutput();

    private static final CostCentre COST_CENTRE = CostCentreUtils.createFakeEntity();
    private static final CostCentreInputId COST_CENTRE_INPUT_ID = CostCentreUtils.createFakeInputId();
    private static final CostCentreOutput COST_CENTRE_OUTPUT = CostCentreUtils.createFakeOutput();

    public static TransactionInput createFakeInput() {
        return TransactionInput.builder()
                .id(TRANSACTION_ID)
                .date(TRANSACTION_DATE)
                .narration(NARRATION)
                .type(TRANSACTION_TYPE)
                .value(TRANSACTION_VALUE)
                .account(ACCOUNT_INPUT_ID)
                .costCentre(COST_CENTRE_INPUT_ID)
                .build();
    }

    public static TransactionOutput createFakeOutput() {
        return TransactionOutput.builder()
                .id(TRANSACTION_ID)
                .date(TRANSACTION_DATE)
                .narration(NARRATION)
                .type(TRANSACTION_TYPE)
                .value(TRANSACTION_VALUE)
                .account(ACCOUNT_OUTPUT)
                .costCentre(COST_CENTRE_OUTPUT)
                .build();
    }

    public static Transaction createFakeEntity() {
        return Transaction.builder()
                .id(TRANSACTION_ID)
                .date(TRANSACTION_DATE)
                .narration(NARRATION)
                .type(TRANSACTION_TYPE)
                .account(ACCOUNT)
                .costCentre(COST_CENTRE)
                .build();
    }

}
