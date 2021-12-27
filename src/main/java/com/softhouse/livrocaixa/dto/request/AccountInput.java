package com.softhouse.livrocaixa.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountInput {

    private Long id;

    @NotEmpty
    private String description;

    @PastOrPresent
    private LocalDate openingBalanceDate;

    private BigDecimal openingBalanceAmount;

}
