package nl.tudelft.sem.main.controllers;

import static org.mockito.Mockito.when;

import nl.tudelft.sem.main.controllers.HelloController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class HelloControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient HelloController helloController;

    private transient String okResponse;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        okResponse = "<200 OK OK,[]>";
    }

    @Test
    void helloUser_test() {
        when(restTemplate.getForObject("http://localhost:8081/sayHi", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals(okResponse, helloController.helloUser());
    }

    @Test
    void helloRoom_test() {
        when(restTemplate.getForObject("http://localhost:8082/sayHiToRoom", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals(okResponse, helloController.helloRoom());
    }

    @Test
    void helloBuilding_test() {
        when(restTemplate.getForObject("http://localhost:8082/sayHiToBuilding", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals(okResponse, helloController.helloBuilding());
    }

    @Test
    void helloBookings_test() {
        when(restTemplate.getForObject("http://localhost:8083/sayHi", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals(okResponse, helloController.helloBookings());
    }

    @Test
    void checkConnectionBookingsRooms_test() {
        when(restTemplate.getForObject("http://localhost:8083/confirmRoomsConnection", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals(okResponse, helloController.checkConnectionBookingsRooms());
    }

}
