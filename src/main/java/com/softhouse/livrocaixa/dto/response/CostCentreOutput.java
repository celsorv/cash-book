package com.softhouse.livrocaixa.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.softhouse.livrocaixa.dto.response.view.TransactionView;
import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CostCentreOutput {

    @JsonView(TransactionView.DescriptionOnly.class)
    private Long id;

    @JsonView(TransactionView.DescriptionOnly.class)
    private String description;

    private CostCentreOutput subaccount;

}
