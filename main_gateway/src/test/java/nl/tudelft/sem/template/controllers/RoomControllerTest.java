package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class RoomControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @InjectMocks
    private transient RoomController roomController;

    @InjectMocks
    private transient BookingController bookingController;

    private transient Room room1;
    private transient Room room2;
    private transient Building building;
    private final transient String token = "token";
    private transient List<Room> rooms;
    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
    private transient List<Booking> bookings;

    private transient ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        rooms = new ArrayList<>();
        Map<String, String> equipmentMap = new HashMap<>();
        equipmentMap.put("wifi", "True");
        equipmentMap.put("projector", "True");
        equipmentMap.put("smart board", "True");
        building = new Building(36, LocalTime.of(8, 0),
                LocalTime.of(22, 0), "Building 1");
        room1 = new Room(1, "Steve Jobs Room", 4, equipmentMap, "available", building);
        room2 = new Room(1, "Marie Currie Room", 8, equipmentMap, "available", building);
        rooms.add(room1);
        rooms.add(room2);
        b1 = new Booking(1L, "Mike", 1, 1,
                LocalDate.now(),
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(3),
                "Group study session",
                List.of("user0", "user1"));
        b2 = new Booking(2L, "Mike", 1, 1,
                LocalDate.now().plusDays(3),
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(2),
                "Project room",
                List.of("user2", "user3"));

        b3 = new Booking(2L, "Mike", 1, 36,
                LocalDate.of(2021, 12, 31),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));
        bookings = new ArrayList<>(List.of(b1, b2,b3));
    }

    @Test
    void getRooms_test() {
        String uri = "http://localhost:8082/rooms";
        ResponseEntity<List> res = new ResponseEntity<>(rooms, HttpStatus.OK);

        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class)))
                .thenReturn(res);

        Assertions.assertThat(roomController.getRooms(token)).isEqualTo(rooms);
        verify(restTemplate, times(1))
                .exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getRoom_test() {
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(1));

        ResponseEntity<Room> res = new ResponseEntity<>(room1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Room.class)))
                .thenReturn(res);

        Assertions.assertThat(roomController.getRoom("1", token)).isEqualTo(room1);
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Room.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postRoom_test() {
        String uri = "http://localhost:8082/rooms";
        Assertions.assertThat(roomController.postRoom(room1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(room1, entity.getValue().getBody());
    }

    @Test
    void updateRoom_test() {
        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(1));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.PUT),
                entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(roomController.updateRoom(room2, "1", token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.PUT),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(room2, entity.getValue().getBody());
    }

    @Test
    void deleteRoom_test() {
        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(1));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE),
                entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(roomController.deleteRoom("1", token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.DELETE),
                entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void availableRoom_test() {
        RoomController spyRoomController = Mockito.spy(roomController);
        List<Room> result = new ArrayList<>();
        result.add(room1);
        result.add(room2);
        Mockito.doReturn(rooms).when(spyRoomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);
        assertEquals(roomController.availableRooms("2022-12-31", "10:00:00", "18:30:00", token),result);
    }
}
