package nl.tudelft.sem.template.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.RoomApplication;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.repositories.BuildingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@AutoConfigureMockMvc
@SpringBootTest(classes = RoomApplication.class)
public class BuildingServiceTest {

    @Mock
    private transient BuildingRepository buildingRepository;

    @InjectMocks
    private transient BuildingService buildingService;


    transient Building b0;
    transient Building b1;
    transient Building b2;

    @BeforeEach
    void setUp() {
        b0 = new Building(36, LocalTime.of(8, 0), LocalTime.of(20, 0), "EEMCS");
        b1 = new Building(25, LocalTime.of(8, 0), LocalTime.of(20, 0), "Library");
        b2 = new Building(36, LocalTime.of(8, 0), LocalTime.of(20, 0), "EEMCS");
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
