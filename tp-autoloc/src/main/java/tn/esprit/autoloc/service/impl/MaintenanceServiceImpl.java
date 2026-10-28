package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        if (maintenance.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel objet Maintenance ne doit pas avoir d'identifiant");
        }
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override


    public Maintenance update(Long id, Maintenance maintenance) {
        Maintenance existant = findById(id);

        existant.setDateDebut(maintenance.getDateDebut());
        existant.setDateFin(maintenance.getDateFin());
        existant.setDescription(maintenance.getDescription());

        return maintenanceRepository.save(existant);
    }


    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }
}