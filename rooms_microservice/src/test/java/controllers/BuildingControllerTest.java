package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.Application;
import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.services.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class BuildingControllerTest {

    @Mock
    private BuildingService buildingService;

    @InjectMocks
    private BuildingController buildingController;

    List<Building> buildings;
    Building b0;
    Building b1;

    @BeforeEach
    void setUp() {
        b0 = new Building(36, LocalTime.of(8, 0), LocalTime.of(20, 0), "EEMCS");
        b1 = new Building(25, LocalTime.of(8, 0), LocalTime.of(20, 0), "Library");
        buildings = new ArrayList<>();
        buildings.add(b0);
        buildings.add(b1);
    }

    @Test
    void sayHi_test() {
        assertEquals("Hello from the building!", buildingController.sayHi());
    }

    @Test
    void getAllBuildings_test() {
        when(buildingService.getAllBuildings()).thenReturn(buildings);
        assertEquals(buildings, buildingController.getAllBuildings());
    }

    @Test
    void getBuilding_test() {
        when(buildingService.getBuilding(36)).thenReturn(b0);
        assertEquals(b0, buildingController.getBuilding(36));
    }

    @Test
    void addBuilding_test() {
        Building b2 = new Building(23, LocalTime.of(8, 0), LocalTime.of(20, 0), "CEG");
        buildingController.addBuilding(b2);
        verify(buildingService, times(1)).addBuilding(b2);
    }

    @Test
    void updateBuilding_test() {
        Building b2 = new Building(36, LocalTime.of(8, 0), LocalTime.of(21, 0), "EWI");
        buildingController.updateBuilding(b2, 36);
        verify(buildingService, times(1)).updateBuilding(36, b2);
    }

    @Test
    void deleteBuilding_test() {
        buildingController.deleteBuilding(36);
        verify(buildingService, times(1)).deleteBuilding(36);
    }


}
