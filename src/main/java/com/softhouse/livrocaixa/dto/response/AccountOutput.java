package com.softhouse.livrocaixa.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.softhouse.livrocaixa.dto.response.view.TransactionView;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AccountOutput {

    @JsonView(TransactionView.DescriptionOnly.class)
    private Long id;

    @JsonView(TransactionView.DescriptionOnly.class)
    private String description;

    private LocalDate openingBalanceDate;
    private BigDecimal openingBalanceAmount;

}
