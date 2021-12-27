package com.softhouse.livrocaixa.mapper;

import com.softhouse.livrocaixa.dto.request.CostCentreInput;
import com.softhouse.livrocaixa.dto.response.CostCentreOutput;
import com.softhouse.livrocaixa.entity.CostCentre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CostCenterMapper {

    CostCenterMapper INSTANCE = Mappers.getMapper(CostCenterMapper.class);

    @Mapping(target = "subaccount", source = "subaccountId")
    CostCentre ToModel(CostCentreInput costCenterInput);

    CostCentreOutput toDTO(CostCentre costCenter);

    List<CostCentreOutput> toDTOList(List<CostCentre> costCenters);

}
