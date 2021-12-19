package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.validators.Validator;
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



public class BookingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @Mock
    private transient Validator handler;

    @InjectMocks
    private transient BuildingController buildingController;

    @InjectMocks
    private transient BookingController bookingController;

    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
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
    }

    @Test
    void getAllBookings_test() {
        String uri = "http://localhost:8083/allbookings";
        when(restTemplate.getForObject(uri, List.class))
                .thenReturn(bookings);

        Assertions.assertThat(bookingController.getAllBookings(token)).isEqualTo(bookings);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
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

    //TODO: fix this test
    @Test
    void postBooking_test() throws InvalidBookingException, InvalidRoomException,
            BuildingNotOpenException {
        String uri = "http://localhost:8083/bookings";
        //security version
        Assertions.assertThat(bookingController.postBooking(b1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.POST),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(b1, entity.getValue().getBody());
        //development version
        when(handler.handle(any(Booking.class))).thenReturn(true);
        Mockito.doThrow(new RuntimeException("error")).when(restTemplate)
                        .postForObject(eq(anyString()), any(Booking.class), void.class);
        Assertions.assertThat(bookingController.postBooking(b1, token)).isTrue();
        verify(restTemplate, times(1)).postForObject(uri, b1, void.class);
    }

    @Test
    void postBookingInvalid_test() {
        MockitoAnnotations.initMocks(this);
        when(buildingController.getBuilding(36, token)).thenReturn(building);
        when(restTemplate.getForObject("http://localhost:8082/getBuilding/36", Building.class))
                .thenReturn(building);
        Assertions.assertThat(bookingController.postBooking(b3, token)).isFalse();
    }

    @Test
    void updateBooking_test() {
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(1L));
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
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(1L));
        ResponseEntity<Void> res = new ResponseEntity<>(HttpStatus.OK);
        when(restTemplate.exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class))).thenReturn(res);
        Assertions.assertThat(bookingController.deleteBooking(1L, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.DELETE),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

}
