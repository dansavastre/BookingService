package controllers.test;

import nl.tudelft.sem.template.controllers.HelloController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

public class HelloControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient HelloController helloController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void helloUser_test() {
        when(restTemplate.getForObject("http://localhost:8081/sayHi", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals("<200 OK OK,[]>", helloController.helloUser());
    }

    @Test
    void helloRoom_test() {
        when(restTemplate.getForObject("http://localhost:8082/sayHiToRoom", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals("<200 OK OK,[]>", helloController.helloRoom());
    }

    @Test
    void helloBuilding_test() {
        when(restTemplate.getForObject("http://localhost:8082/sayHiToBuilding", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals("<200 OK OK,[]>", helloController.helloBuilding());
    }

    @Test
    void helloBookings_test() {
        when(restTemplate.getForObject("http://localhost:8083/sayHi", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals("<200 OK OK,[]>", helloController.helloBookings());
    }

    @Test
    void checkConnectionBookingsRooms_test() {
        when(restTemplate.getForObject("http://localhost:8083/confirmRoomsConnection", String.class))
                .thenReturn(String.valueOf(new ResponseEntity(HttpStatus.OK)));
        Assertions.assertEquals("<200 OK OK,[]>", helloController.checkConnectionBookingsRooms());
    }

}
