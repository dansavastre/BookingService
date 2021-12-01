package services_test;

import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.repositories.BuildingRepository;
import nl.tudelft.sem.template.services.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BuildingService_test {

    @Mock
    private BuildingRepository buildingRepository;

    private BuildingService buildingService;


    Building b0;
    Building b1;
    Building b2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        b0 = new Building(36, LocalTime.of(8,0), LocalTime.of(20,0), "EEMCS");
        b1 = new Building(25, LocalTime.of(8,0), LocalTime.of(20,0), "Library");
        b2 = new Building(36, LocalTime.of(8,0), LocalTime.of(20,0), "EEMCS");

        buildingService = new BuildingService(buildingRepository);
    }


    @Test
    void getAllBuildings_test() {
        List<Building> buildings = new ArrayList<>();
        buildings.add(b0);
        buildings.add(b1);
        when(buildingRepository.findAll()).thenReturn(buildings);
        assertEquals(buildings, buildingService.getAllBuildings());
    }

    @Test
    void getBuilding_test() {
        when(buildingRepository.findById(36)).thenReturn(java.util.Optional.ofNullable(b0));
        assertEquals(b0, buildingService.getBuilding(36));
    }

    @Test
    void addBuilding_test() {
        buildingService.addBuilding(b1);
        verify(buildingRepository, times(1)).save(b1);
    }

    @Test
    void updateBuilding_test() {
        buildingService.updateBuilding(36, b2);
        verify(buildingRepository, times(1)).deleteById(36);
        verify(buildingRepository, times(1)).save(b2);
    }

    @Test
    void deleteBuilding_test() {
        buildingService.deleteBuilding(36);
        verify(buildingRepository, times(1)).deleteById(36);
    }
}
