package com.softhouse.livrocaixa.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CostCentreInput {

    private Long id;

    @NotEmpty
    private String description;

    @NotEmpty
    private String classification;

    private Boolean analyticalAccount;

    @Valid
    private CostCentreInputId subaccountId;

}
