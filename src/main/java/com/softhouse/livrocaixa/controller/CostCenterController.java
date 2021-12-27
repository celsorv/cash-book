package com.softhouse.livrocaixa.controller;

import com.softhouse.livrocaixa.dto.request.CostCentreInput;
import com.softhouse.livrocaixa.dto.response.CostCentreOutput;
import com.softhouse.livrocaixa.dto.response.MessageResponseDto;
import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.mapper.CostCenterMapper;
import com.softhouse.livrocaixa.service.CostCentreService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cost-centres")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CostCenterController {

    private final CostCentreService costCenterService;

    private final CostCenterMapper costCenterMapper = CostCenterMapper.INSTANCE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto create(@RequestBody @Valid CostCentreInput costCenterInput) {
        CostCentre costCenterToSave = costCenterMapper.ToModel(costCenterInput);
        CostCentre savedCostCenter = costCenterService.create(costCenterToSave);
        return createMessageResponse(savedCostCenter.getId(), "Created cost center with ID");
    }

    @PutMapping("/{id}")
    public MessageResponseDto update(@PathVariable Long id, @RequestBody @Valid CostCentreInput costCenterInput) {
        CostCentre costCenterToUpdate = costCenterMapper.ToModel(costCenterInput);
        CostCentre savedCostCenter = costCenterService.update(id, costCenterToUpdate);
        return createMessageResponse(savedCostCenter.getId(), "Updated cost center with ID");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        costCenterService.delete(id);
    }

    @GetMapping("/{id}")
    public CostCentreOutput getById(@PathVariable Long id) {
        CostCentre costCenter = costCenterService.getById(id);
        return costCenterMapper.toDTO(costCenter);
    }

    @GetMapping
    public List<CostCentreOutput> getAll() {
        return costCenterMapper.toDTOList(costCenterService.getAll());
    }

    private MessageResponseDto createMessageResponse(long id, String message) {
        return MessageResponseDto
                .builder()
                .message(message + " " + id)
                .build();
    }

}
