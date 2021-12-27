package com.softhouse.livrocaixa.dto.response;

import com.softhouse.livrocaixa.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CashBookDto {

    private LocalDate date;
    private String narration;
    private TransactionType type;
    private BigDecimal value;

}
