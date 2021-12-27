package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.dto.request.CostCentreInput;
import com.softhouse.livrocaixa.dto.response.CostCentreOutput;
import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.mapper.CostCenterMapper;
import com.softhouse.livrocaixa.repository.CostCentreRepository;
import com.softhouse.livrocaixa.utils.CostCentreUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CostCentreServiceTest {

    @Mock
    private CostCentreRepository costCentreRepository;

    @InjectMocks
    private CostCentreService costCentreService;

    @Test
    void testGivenCostCentreInputThenReturnSavedCostCentre() {

        CostCentreInput costCentreInput = CostCentreUtils.createFakeInput();
        CostCentre expectedSavedCostCentre = CostCentreUtils.createFakeEntity();

        when(costCentreRepository.save(any(CostCentre.class))).thenReturn(expectedSavedCostCentre);

        CostCentre costCentreToSave = CostCenterMapper.INSTANCE.ToModel(costCentreInput);
        CostCentre savedCostCentre = costCentreService.create(costCentreToSave);

        CostCentreOutput expectedSuccessOutput = CostCenterMapper.INSTANCE.toDTO(expectedSavedCostCentre);
        CostCentreOutput successOutput = CostCenterMapper.INSTANCE.toDTO(savedCostCentre);

        assertEquals(expectedSuccessOutput, successOutput);

    }

}
