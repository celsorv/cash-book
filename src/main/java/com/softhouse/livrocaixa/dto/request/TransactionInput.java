package com.softhouse.livrocaixa.dto.request;

import com.softhouse.livrocaixa.enums.TransactionType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInput {

    private Long id;

    @PastOrPresent
    private LocalDate date;

    @NotEmpty
    private String narration;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull
    @Valid
    private AccountInputId account;

    private CostCentreInputId costCentre;

    @PositiveOrZero
    private BigDecimal value;

}
