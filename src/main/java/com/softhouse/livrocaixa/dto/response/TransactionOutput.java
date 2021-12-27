package com.softhouse.livrocaixa.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.softhouse.livrocaixa.dto.response.view.TransactionView;
import com.softhouse.livrocaixa.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonView(TransactionView.DescriptionOnly.class)
public class TransactionOutput {

    private Long id;
    private LocalDate date;
    private String narration;
    private TransactionType type;
    private BigDecimal value;
    private AccountOutput account;
    private CostCentreOutput costCentre;

}
