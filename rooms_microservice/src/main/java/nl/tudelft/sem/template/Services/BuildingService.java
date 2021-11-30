package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.Building;
import nl.tudelft.sem.template.Repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public List<Building> getAllBuildings(){
        List<Building> b = new ArrayList<>();
        buildingRepository.findAll().forEach(b::add);
        return b;
    }

    public Building getBuilding(int id){
        return buildingRepository.findById(id).get();
    }

    public void addBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void updateBuilding(int id, Building building) {
        buildingRepository.deleteById(id);
        buildingRepository.save(building);
    }

    public void deleteBuilding(int id) {
        buildingRepository.deleteById(id);
    }
}

