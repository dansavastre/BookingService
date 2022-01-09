package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.*;
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
import org.springframework.web.server.ResponseStatusException;


public class BookingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;


    @Mock
    private transient BuildingController buildingController;
    @Mock
    private transient BookingController bookingControllerMock;
    @Mock
    private transient RoomController roomController;

    @InjectMocks
    private transient BookingController bookingController;

    transient User user1;
    transient User user2;
    transient User user3;
    transient User user4;
    transient Group group1;
    transient Group group2;
    transient List<Group> groups;

    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
    private transient Room room1;
    private transient Building building;
    private transient Building building1;
    private transient List<Booking> bookings;
    private final transient String token = "token";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        building = new Building(36, LocalTime.of(8, 0),
                LocalTime.of(22, 0), "Building 36");
        building1 = new Building(1, LocalTime.of(8, 0),
                LocalTime.of(22, 0), "Building 1");
        room1 = new Room(1, "Nice room", 4,
                new HashMap<>(), "yes", 1);
        b1 = new Booking(1L, "Mike", 1, 1,
                LocalDate.of(2025, 12, 4),
                LocalTime.of(11, 10),
                LocalTime.of(15, 45),
                "Group study session",
                List.of("user0", "user1"));
        b2 = new Booking(2L, "Mike", 1, 1,
                LocalDate.of(2025, 12, 5),
                LocalTime.of(10, 30),
                LocalTime.of(16, 0),
                "Project room",
                List.of("user2", "user3"));

        b3 = new Booking(2L, "Mike", 1, 36,
                LocalDate.of(2021, 12, 15),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));
        bookings = new ArrayList<>(List.of(b1, b2));

        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group1 = new Group(1L, "1", "TestGroup", List.of(user1, user2));
        group2 = new Group(2L, "2", "Second Group", List.of(user2, user3, user4));
        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
    }

    @Test
    void getAllBookings_test() {
        String uri = "http://localhost:8083/allbookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class)))
                .thenReturn(res);

        Assertions.assertThat(bookingController.getAllBookings(token)).isEqualTo(bookings);
        verify(restTemplate, times(1))
                .exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getFutureBookings_test() {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);

        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class)))
            .thenReturn(res);

        Assertions.assertThat(bookingController.getFutureBookings(token)).isEqualTo(bookings);
        verify(restTemplate, times(1))
            .exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void getBooking_test() {
        String uri = "http://localhost:8083/getBooking/".concat(String.valueOf(1L));
        ResponseEntity<Booking> res = new ResponseEntity<>(b1, HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET),
            entity.capture(), eq(Booking.class)))
            .thenReturn(res);

        Assertions.assertThat(bookingController.getBooking(1L, token)).isEqualTo(b1);
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.GET),
            entity.capture(), eq(Booking.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postBooking_test() throws InvalidBookingException,
            InvalidRoomException, BuildingNotOpenException {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class))).thenReturn(res1);

        when(buildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq("http://localhost:8083/allbookings"),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);
        Assertions.assertThat(bookingController.postBooking(b1, token)).isTrue();
        verify(restTemplate, times(1))
                .exchange(eq(uri), eq(HttpMethod.POST), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postBookingInvalid_test() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(buildingController.getBuilding(36, token)).thenReturn(building);
            bookingController.postBooking(b3, token);
        });
        assertEquals(exception.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void updateBooking_test() {
        String uri = "http://localhost:8083/bookings/".concat(String.valueOf(1L));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.PUT),
            entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(bookingController.updateBooking(b1, 1L, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.PUT),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(b1, entity.getValue().getBody());
    }

    @Test
    void deleteBooking_test() {
        String uri = "http://localhost:8083/bookings/".concat(String.valueOf(1L));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(bookingController.deleteBooking(1L, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postBookingForGroup_test() {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class))).thenReturn(res1);

        when(buildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq("http://localhost:8083/allbookings"),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);
        when(restTemplate.exchange(eq("http://localhost:8081/secretary/checkGroup/1/1/2"), eq(HttpMethod.GET),
                entity.capture(), eq(Void.class))).thenReturn(res1);

        Assertions.assertThat(bookingController
                .postBookingForGroup(b1, 1L, "1", "2", token)).isTrue();
        verify(restTemplate, times(1))
                .exchange(eq("http://localhost:8081/secretary/checkGroup/1/1/2"), eq(HttpMethod.GET), entity.capture(), eq(Boolean.class));
        //verify(restTemplate, times(1))
        //        .exchange(eq(uri), eq(HttpMethod.POST), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

}
