package com.softhouse.livrocaixa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CostCentreMapDto {

    String classification;
    String description;
    BigDecimal total;

}
