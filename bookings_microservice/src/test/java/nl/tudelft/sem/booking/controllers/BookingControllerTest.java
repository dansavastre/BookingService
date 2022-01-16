package nl.tudelft.sem.booking.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.sem.booking.objects.Booking;
import nl.tudelft.sem.booking.services.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


public class BookingControllerTest {

    @Mock
    private transient BookingService bookingService;
    @Mock
    private transient RestTemplate restTemplate;
    @Mock
    private transient Authorization auth;

    @InjectMocks
    private transient BookingController bookingController;

    transient Booking b1;
    transient Booking b2;
    transient String token;

    @BeforeEach
    void setup() {
        List<String> p = new ArrayList<>();
        p.add("user0");
        p.add("user1");
        MockitoAnnotations.initMocks(this);
        b1 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 8),
            LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                "Studying", p);
        b1.setId(1L);
        b2 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
            LocalTime.of(8, 20, 0), LocalTime.of(15, 45, 0),
                "Project meeting", p);
        token = "token";
        b2.setId(2L);
    }

    @Test
    void roomNotConnected_test() {
        when(restTemplate.getForObject("http://localhost:8082/getConnectionStatus", String.class))
            .thenThrow(HttpServerErrorException.class);
        String message = bookingController.checkIfRoomsConnected().substring(0, 26);
        Assertions.assertEquals("Not connected due to Error", message);
    }

    @Test
    void roomConnected_test() {
        when(restTemplate.getForObject("http://localhost:8082/getConnectionStatus", String.class))
            .thenReturn("Rooms Microservice is connected!");
        Assertions.assertEquals("Rooms Microservice is connected!",
                bookingController.checkIfRoomsConnected());
    }

    @Test
    void getAllBookings_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingService.getAllBookings()).thenReturn(bookings);
        Assertions.assertEquals(bookings, bookingController.getAllBookings(token));
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);
    }

    @Test
    void getFutureBookings_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingService.getFutureBookings()).thenReturn(bookings);
        Assertions.assertEquals(bookings, bookingController.getFutureBookings(token));
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);
    }

    @Test
    void getBooking_test() {
        when(bookingService.getBooking(1L)).thenReturn(b1);
        Assertions.assertEquals(b1, bookingController.getBooking(1L, token));
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);
    }

    @Test
    void addBooking_test() {
        bookingController.addBooking(b1, token);
        verify(bookingService, times(1)).addBooking(b1);
        verify(auth, times(1)).authorize(Authorization.EMPLOYEE, token);
    }

    @Test
    void updateBooking_Test() {
        bookingController.updateBooking(b1, 1L, token);
        verify(bookingService, times(1)).updateBooking(1L, b1);
        verify(auth, times(1)).authorize(Authorization.ADMIN, token);
    }

    @Test
    void getFutureBooking_test() {
        when(bookingService.getFutureBookings()).thenReturn(List.of(b2));
        Assertions.assertEquals(List.of(b2), bookingController.getFutureBookings(token));
    }
}
