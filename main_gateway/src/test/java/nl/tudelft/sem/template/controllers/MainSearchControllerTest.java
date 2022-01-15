package nl.tudelft.sem.template.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class MainSearchControllerTest {
    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @Mock
    private transient MainRoomController roomController;

    @InjectMocks
    private transient SearchController searchController;

    @Mock
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
    private transient String tenTime = "10:00:00";

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
        room1 = new Room(1, "Steve Jobs Room", 4, equipmentMap, "yes", building);
        room2 = new Room(1, "Marie Currie Room", 8, equipmentMap, "yes", building);
        rooms.add(room1);
        rooms.add(room2);
        b1 = new Booking(1L, "Mike", 1, 1,
                LocalDate.of(2022, 1, 21),
                LocalTime.of(10, 30),
                LocalTime.of(18, 0),
                "Group study session",
                List.of("user0", "user1"));
        b2 = new Booking(2L, "Mike", 1, 1,
                LocalDate.of(2022, 2, 12),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));

        b3 = new Booking(2L, "Mike", 1, 36,
                LocalDate.of(2021, 12, 31),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));
        bookings = new ArrayList<>(List.of(b1, b2, b3));
    }


    @Test
    void availableRoom_validTest() {
        List<Room> result = new ArrayList<>();
        result.add(room1);
        result.add(room2);
        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);
        assertEquals(result, searchController.availableRooms("2023-01-02",
                tenTime, "14:30:00", token));
    }

    @Test
    void availableRoom_invalidStartTimeTest() {

        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);

        assertThrows(ResponseStatusException.class, () -> {
            searchController.availableRooms("2022-01-02",
                    tenTime, "08:30:00", token);
        });
    }

    @Test
    void availableRoom_pastDateTest() {

        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);

        assertThrows(ResponseStatusException.class, () -> {
            searchController.availableRooms("2020-01-02",
                    "08:30:00", tenTime,  token);
        });
    }

    @Test
    void availableRoom_pastTimeTest() {
        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);

        assertThrows(ResponseStatusException.class, () -> {
            searchController.availableRooms(LocalDate.of(2019, 1, 1).toString(),
                    "08:30:00", tenTime,  token);
        });
    }

    @Test
    void searchRoom_dateNull() {
        List<Room> result = new ArrayList<>();
        result.add(room1);
        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);
        String[] arr = {"projector", "wifi"};
        assertEquals(result, searchController.searchRooms(null, null,
                null, "1", "36",
                "Steve Jobs Room", "4", arr, token));
    }

    @Test
    void searchRoom_dateNotNull() {
        List<Room> result = new ArrayList<>();
        Mockito.doReturn(rooms).when(roomController).getRooms(token);
        when(bookingController.getFutureBookings(token)).thenReturn(bookings);
        String[] arr = {"projector", "wifi", "table"};
        assertEquals(result, searchController.searchRooms("2023-01-02",
                tenTime, "14:30:00", null,
                null, null, null, arr, token));
    }
}
