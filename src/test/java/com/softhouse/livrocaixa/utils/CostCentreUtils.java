package com.softhouse.livrocaixa.utils;

import com.softhouse.livrocaixa.dto.request.CostCentreInput;
import com.softhouse.livrocaixa.dto.request.CostCentreInputId;
import com.softhouse.livrocaixa.dto.response.CostCentreOutput;
import com.softhouse.livrocaixa.entity.CostCentre;

public class CostCentreUtils {

    private static final Long COST_CENTRE_ID = 20L;
    private static final String DESCRIPTION = "Telefone";
    private static final String CLASSIFICATION = "3.2.2";
    private static final Boolean ANALYTICAL_ACCOUNT = true;
    private static final CostCentreInputId SUBACCOUNT_ID = null;
    private static final CostCentreOutput SUBACCOUNT_OUTPUT = null;
    private static final CostCentre SUBACCOUNT = null;

    public static CostCentreInput createFakeInput() {
        return CostCentreInput.builder()
                .id(COST_CENTRE_ID)
                .description(DESCRIPTION)
                .classification(CLASSIFICATION)
                .analyticalAccount(ANALYTICAL_ACCOUNT)
                .subaccountId(SUBACCOUNT_ID)
                .build();
    }

    public static CostCentreInputId createFakeInputId() {
        return CostCentreInputId.builder().id(COST_CENTRE_ID).build();
    }

    public static CostCentreOutput createFakeOutput() {
        return CostCentreOutput.builder()
                .id(COST_CENTRE_ID)
                .description(DESCRIPTION)
                .subaccount(SUBACCOUNT_OUTPUT)
                .build();
    }

    public static CostCentre createFakeEntity() {
        return CostCentre.builder()
                .id(COST_CENTRE_ID)
                .description(DESCRIPTION)
                .classification(CLASSIFICATION)
                .analyticalAccount(ANALYTICAL_ACCOUNT)
                .subaccount(SUBACCOUNT)
                .build();
    }

}
