package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    private transient BuildingRepository buildingRepository;


    /**
     * Method that requests all buildings from the database and returns them as a List of Building.
     *
     * @return List of Building Objects
     */
    public List<Building> getAllBuildings() {
        return new ArrayList<>(buildingRepository.findAll());
    }

    public Building getBuilding(int id) {
        return buildingRepository.findById(id).get();
    }

    public void addBuilding(Building building) {
        buildingRepository.save(building);
    }

    /**
     * Update a building by checking if IDs match and then requesting a save from database.
     *
     * @param id       ID of the building
     * @param building New updated building entity
     */
    public void updateBuilding(int id, Building building) {
        if (building.getId() == id) {
            buildingRepository.save(building);
        }
    }

    public void deleteBuilding(int id) {
        buildingRepository.deleteById(id);
    }
}

