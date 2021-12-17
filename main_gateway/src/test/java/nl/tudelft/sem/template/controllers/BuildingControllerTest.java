package controllers.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.objects.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;



public class BuildingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient BuildingController buildingController;

    private transient Building b1;
    private transient Building b2;
    private transient List<Building> buildings = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        b1 = new Building(1, LocalTime.of(8, 0), LocalTime.of(22, 0), "Ewi");
        b2 = new Building(2, LocalTime.of(9, 30), LocalTime.of(17, 45), "Drebbelweg");
        buildings.add(b1);
        buildings.add(b2);
    }

    @Test
    void getBuildings_test() {
        String uri = "http://localhost:8082/buildings";
        when(restTemplate.getForObject(uri, List.class))
                .thenReturn(buildings);

        Assertions.assertThat(buildingController.getBuildings()).isEqualTo(buildings);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
    }

    @Test
    void getBuilding_test() {
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(1));
        when(restTemplate.getForObject(uri, Building.class))
                .thenReturn(b1);
        Assertions.assertThat(buildingController.getBuilding(1)).isEqualTo(b1);
        verify(restTemplate, times(1)).getForObject(uri, Building.class);
    }

    @Test
    void postBuilding_test() {
        String uri = "http://localhost:8082/buildings";
        Assertions.assertThat(buildingController.postBuilding(b1)).isTrue();
        verify(restTemplate, times(1)).postForObject(uri, b1, void.class);
    }

    @Test
    void updateBuilding_test() {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(1));
        Assertions.assertThat(buildingController.updateBuilding(b2, 1)).isTrue();
        verify(restTemplate, times(1)).put(uri, b2);
    }

    @Test
    void deleteBuilding_test() {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(1));
        Assertions.assertThat(buildingController.deleteBuilding(1)).isTrue();
        verify(restTemplate, times(1)).delete(uri);
    }

}
