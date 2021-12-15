package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
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

public class BookingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;
    @Captor
    private transient ArgumentCaptor<HttpEntity> entity;

    @InjectMocks
    private transient BookingController bookingController;

    private transient Booking b1;
    private transient Booking b2;
    private transient List<Booking> bookings = new ArrayList<>();
    private final transient String token = "token";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        b1 = new Booking(1L, "Mike", 1, 1,
                LocalDate.of(2021, 12, 4),
                LocalTime.of(11, 10),
                LocalTime.of(15, 45),
                "Group study session",
                List.of("user0", "user1"));
        b2 = new Booking(2L, "Mike", 1, 1,
                LocalDate.of(2021, 12, 5),
                LocalTime.of(10, 30),
                LocalTime.of(16, 0),
                "Project room",
                List.of("user2", "user3"));
        bookings.add(b1);
        bookings.add(b2);
    }

    @Test
    void getBookings_test() {
        String uri = "http://localhost:8083/bookings";
        ResponseEntity<List> res = new ResponseEntity<>(bookings, HttpStatus.OK);

        when(restTemplate.exchange(eq(uri), eq(HttpMethod.GET), entity.capture(), eq(List.class)))
            .thenReturn(res);

        Assertions.assertThat(bookingController.getBookings(token)).isEqualTo(bookings);
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
    void postBooking_test() {
        String uri = "http://localhost:8083/bookings";
        Assertions.assertThat(bookingController.postBooking(b1, token)).isTrue();
        verify(restTemplate, times(1)).exchange(eq(uri), eq(HttpMethod.POST),
            entity.capture(), eq(void.class));
        assertEquals(token, entity.getValue().getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
        assertEquals(b1, entity.getValue().getBody());
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
