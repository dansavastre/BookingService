package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



public class BuildingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @InjectMocks
    private transient BuildingController buildingController;

    private transient Building b1;
    private transient Building b2;
    private final transient List<Building> buildings = new ArrayList<>();
    private final transient String token = "token";

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
        ResponseEntity<List> res = new ResponseEntity<>(buildings, HttpStatus.OK);

        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class)))
                .thenReturn(res);

        Assertions.assertThat(buildingController.getBuildings(token)).isEqualTo(buildings);
        verify(restTemplate, times(1))
                .exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getBuilding_test() {
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(1));

        ResponseEntity<Building> res = new ResponseEntity<>(b1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Building.class)))
                .thenReturn(res);

        Assertions.assertThat(buildingController.getBuilding(1, token)).isEqualTo(b1);
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Building.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postBuilding_test() {
        String uri = "http://localhost:8082/buildings";
        Assertions.assertThat(buildingController.postBuilding(b1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(b1, entity.getValue().getBody());
    }

    @Test
    void updateBuilding_test() {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(2));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.PUT),
                entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(buildingController.updateBuilding(b2, 2, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.PUT),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(b2, entity.getValue().getBody());
    }

    @Test
    void deleteBuilding_test() {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(1));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE),
                entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(buildingController.deleteBuilding(1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.DELETE),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

}
