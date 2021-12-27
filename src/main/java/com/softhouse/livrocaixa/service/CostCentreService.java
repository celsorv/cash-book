package com.softhouse.livrocaixa.service;

import com.softhouse.livrocaixa.entity.CostCentre;
import com.softhouse.livrocaixa.exception.CostCentreNotFoundException;
import com.softhouse.livrocaixa.exception.EntityInUseException;
import com.softhouse.livrocaixa.exception.SubaccountNotFoundException;
import com.softhouse.livrocaixa.repository.CostCentreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostCentreService {

    private final CostCentreRepository costCentreRepository;

    private static final String MSG_UNABLE_TO_DELETE
            = "Unable to delete. Cost centre id %d is referenced by other records.";

    @Autowired
    public CostCentreService(CostCentreRepository costCentreRepository) {
        this.costCentreRepository = costCentreRepository;
    }

    public CostCentre create(CostCentre costCentre) {
        verifyIfExistsSubaccount(costCentre);
        costCentre.setId(null);
        return costCentreRepository.save(costCentre);
    }

    public CostCentre update(Long id, CostCentre costCentre) {
        getById(id);
        verifyIfExistsSubaccount(costCentre);
        costCentre.setId(id);
        return costCentreRepository.save(costCentre);
    }

    public void delete(Long id) {
        try {
            costCentreRepository.deleteById(id);
            costCentreRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CostCentreNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(String.format(MSG_UNABLE_TO_DELETE, id));
        }
    }

    public CostCentre getById(Long id) {
        return costCentreRepository.findById(id)
                .orElseThrow(() -> new CostCentreNotFoundException(id));
    }

    public List<CostCentre> getAll() {
        return costCentreRepository.findAll();
    }

    private void verifyIfExistsSubaccount(CostCentre costCentre) {
        if (costCentre.getSubaccount() != null) {
            final Long id = costCentre.getSubaccount().getId();
            costCentreRepository.findById(id)
                    .orElseThrow(() -> new SubaccountNotFoundException(id));
        }
    }

}
