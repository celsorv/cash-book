package com.softhouse.livrocaixa.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
public class CostCentreInputId {

    @NotNull
    private Long id;

}
