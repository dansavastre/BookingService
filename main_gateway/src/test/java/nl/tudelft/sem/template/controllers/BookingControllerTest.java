package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.objects.User;
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
import org.springframework.web.client.HttpClientErrorException;
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
    private transient SecondBuildingController secondBuildingController;

    @Mock
    private transient BookingController bookingControllerMock;


    @Mock
    private transient MainRoomController roomController;

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
    private transient Booking b3;
    private transient Room room1;
    private transient Building building;
    private transient Building building1;
    private transient List<Booking> bookings;
    private final transient String token = "token";
    private final transient String user1Name = "FirstName";
    private final transient String allBookings = "http://localhost:8083/allbookings";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        user1 = new User("1", "password", "FirstName", "LastName");
        user2 = new User("2", "password2", "FirstName2", "LastName2");
        user3 = new User("3", "password3", "FirstName3", "LastName3");
        user4 = new User("4", "password4", "FirstName4", "LastName4");
        group1 = new Group(1L, "1", "TestGroup", List.of(user1, user2));
        group2 = new Group(2L, "2", "Second Group", List.of(user2, user3, user4));
        groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        building = new Building(36, LocalTime.of(8, 0),
                LocalTime.of(22, 0), "Building 36");
        building1 = new Building(1, LocalTime.MIN,
                LocalTime.MAX, "Building 1");
        room1 = new Room(1, "Nice room", 4,
                new HashMap<>(), "yes", building1);
        b1 = new Booking(1L, user1Name, 1, 1,
                LocalDate.now().plusDays(2),
                LocalTime.of(12, 0),
                LocalTime.of(13, 9),

                "Group study session",
                List.of("user0", "user1"));
        Booking b2 = new Booking(2L, user1Name, 1, 1,
                LocalDate.now().plusDays(3),
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(2),
                "Project room",
                List.of("user2", "user3"));

        b3 = new Booking(2L, user1Name, 1, 36,
                LocalDate.of(2021, 12, 15),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));

        bookings = new ArrayList<>(List.of(b1, b2));
    }

    @Test
    void getAllBookings_test() {
        String uri = allBookings;
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
    void sendGetBookingRequest_validTest() {
        HttpHeaders headers = new HttpHeaders();
        String uri = "http://localhost:8083/getBooking/1";
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Booking.class)))
                .thenReturn(new ResponseEntity<>(b1, HttpStatus.OK));
        assertEquals(b1, bookingController.sendGetBookingRequest(headers, uri));
    }

    @Test
    void sendGetBookingRequest_notFound() {
        HttpHeaders headers = new HttpHeaders();
        String uri = "http://localhost:8083/getBooking/1";
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET),
                entity.capture(), eq(Booking.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));
        assertThrows(ResponseStatusException.class, () -> {
            bookingController.sendGetBookingRequest(headers, uri);
        });
    }

    @Test
    void sendGetBookingRequest_serverError() {
        HttpHeaders headers = new HttpHeaders();
        String uri = "http://localhost:8083/getBooking/1";
        assertThrows(ResponseStatusException.class, () -> {
            bookingController.sendGetBookingRequest(headers, uri);
        });
    }

    @Test
    void postBooking_test() {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class))).thenReturn(res1);

        when(secondBuildingController.getBuilding(any(Integer.class), any(String.class))).thenReturn(building1);
        when(roomController.getRoom(b1.getBuilding() + "-"
                + b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq(allBookings),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);
        Assertions.assertThat(bookingController.postBooking(b1, token)).isTrue();
        verify(restTemplate, times(1))
                .exchange(eq(uri), eq(HttpMethod.POST), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }


    @Test
    void postBookingInvalid_test() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            when(secondBuildingController.getBuilding(36, token)).thenReturn(building);
            bookingController.postBooking(b3, token);
        });
        assertEquals(exception.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void updateBooking_test() {
        String uri = "http://localhost:8083/bookings/".concat(String.valueOf(1L));
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.PUT),
            entity.capture(), eq(void.class))).thenReturn(res1);

        when(secondBuildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getBuilding() + "-"
                + b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq(allBookings),
            eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);

        Assertions.assertThat(bookingController.updateBooking(b1, 1L, token)).isTrue();
        verify(restTemplate, times(1))
            .exchange(eq(uri), eq(HttpMethod.PUT), entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

    }

    @Test
    void updateMyBooking_test() {
        String uri = "http://localhost:8083/myBookings/".concat("FirstName/" + String.valueOf(1L));
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.PUT),
            entity.capture(), eq(void.class))).thenReturn(res1);

        when(secondBuildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getBuilding() + "-"
                + b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq(allBookings),
            eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);

        Assertions.assertThat(bookingController.updateBooking(b1, user1Name, 1L, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.PUT),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void deleteMyBooking_test() {
        String uri = "http://localhost:8083/myBookings/".concat("FirstName/" + 1L);
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(bookingController.deleteBooking(user1Name, 1L, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void postBookingForGroup_test() {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        ResponseEntity<Boolean> resBool = new ResponseEntity<>(true, HttpStatus.OK);
        ResponseEntity<Void> res1 = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.POST),
                entity.capture(), eq(void.class))).thenReturn(res1);

        when(secondBuildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getId() + "-" + b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq("http://localhost:8083/allbookings"),
                eq(HttpMethod.GET), entity.capture(), eq(List.class))).thenReturn(res);
        when(restTemplate.exchange(eq("http://localhost:8081/secretary/checkGroup/1/1/FirstName"), eq(HttpMethod.GET),
                entity.capture(), eq(Boolean.class))).thenReturn(resBool);
        when(restTemplate.exchange(eq("http://localhost:8083/bookingsForGroup"), eq(HttpMethod.POST),
                entity.capture(), eq(Void.class))).thenReturn(res1);

        Assertions.assertThat(bookingController
                .postBookingForGroup(1L, "1", b1, token)).isTrue();
        verify(restTemplate, times(1))
                .exchange(eq("http://localhost:8081/secretary/checkGroup/1/1/FirstName"), eq(HttpMethod.GET), entity.capture(), eq(Boolean.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void sendDeleteBookingRequestTest() {
        String uri = "uri";
        Assertions.assertThat(bookingController.sendDeleteBookingRequest(token, uri)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
            eq(HttpMethod.DELETE), entity.capture(), eq(void.class));
    }

    @Test
    void sendDeleteBookingRequestExceptionTest() {
        String uri = "uri";
        when(restTemplate.exchange(eq(uri),
            eq(HttpMethod.DELETE), any(), eq(void.class)))
            .thenThrow(HttpClientErrorException.class);
        assertThrows(HttpClientErrorException.class, () -> {
            bookingController.sendDeleteBookingRequest(token, uri);
        });
    }

    @Test
    void validateBookingTest() throws InvalidBookingException,
        InvalidRoomException, BuildingNotOpenException {
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);
        when(buildingController.getBuilding(b1.getBuilding(), token)).thenReturn(building1);
        when(roomController.getRoom(b1.getBuilding() + "-"
                + b1.getRoom(), token)).thenReturn(room1);
        when(restTemplate.exchange(eq(allBookings),
                eq(HttpMethod.GET), any(), eq(List.class))).thenReturn(res);
        Assertions.assertThat(bookingController.validateBooking(b1, token)).isTrue();
    }

    @Test
    void validateBookingExceptionTest() {
        assertThrows(InvalidBookingException.class, () -> {
            bookingController.validateBooking(b3, token);
        });
    }

    @Test
    void sendPutBookingRequestTest() {
        String uri = "example uri";
        Assertions.assertThat(bookingController.sendPutBookingRequest(b1, token, uri)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri),
                eq(HttpMethod.PUT), entity.capture(), eq(void.class));
    }

    @Test
    void sendPutBookingRequestExceptionTest() {
        String uri = "example uri";
        when(restTemplate.exchange(eq(uri),
                eq(HttpMethod.PUT), any(), eq(void.class)))
            .thenThrow(HttpClientErrorException.class);
        assertThrows(HttpClientErrorException.class, () -> {
            bookingController.sendPutBookingRequest(b3, token, uri);
        });
    }

}
