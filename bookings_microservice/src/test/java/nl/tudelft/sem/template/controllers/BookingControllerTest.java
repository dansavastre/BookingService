package nl.tudelft.sem.template.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.schedule.ChronologicalSortStrategy;
import nl.tudelft.sem.template.schedule.DefaultSortStrategy;
import nl.tudelft.sem.template.schedule.LocationStrategy;
import nl.tudelft.sem.template.services.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


public class BookingControllerTest {

    @Mock
    private transient BookingService bookingService;
    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient BookingController bookingController;

    transient Booking b1;
    transient Booking b2;

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
        b2.setId(2L);
    }

    @Test
    void sayHi_test() {
        Assertions.assertEquals("Hello from the bookings microservice!",
            bookingController.sayHi());
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
        Assertions.assertEquals("Rooms Microservice is connected!", bookingController.checkIfRoomsConnected());
    }

    @Test
    void getAllBookings_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingService.getAllBookings()).thenReturn(bookings);
        Assertions.assertEquals(bookings, bookingController.getAllBookings());
    }

    @Test
    void getBooking_test() {
        when(bookingService.getBooking(1L)).thenReturn(b1);
        Assertions.assertEquals(b1, bookingController.getBooking(1L));
    }

    @Test
    void addBooking_test() {
        bookingController.addBooking(b1);
        verify(bookingService, times(1)).addBooking(b1);
    }

    @Test
    void updateBooking_Test() {
        bookingController.updateBooking(b1, 1L);
        verify(bookingService, times(1)).updateBooking(1L, b1);
    }

    @Test
    void deleteBooking_test() {
        bookingController.deleteBooking(1L);
        verify(bookingService, times(1)).deleteBooking(1L);
    }

    @Test
    void getFutureBooking_test() {
        when(bookingService.getFutureBookings()).thenReturn(List.of(b2));
        Assertions.assertEquals(List.of(b2), bookingController.getFutureBookings());
    }

    @Test
    void getMyBookingsDefault_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b2);
        bookings.add(b1);
        when(bookingService.getBookingsForUser(any(String.class), any(DefaultSortStrategy.class)))
                .thenReturn(bookings);
        List<Booking> b = bookingController.getMyBookingsDefault("A");
        Assertions.assertEquals(bookings, b);
    }

    @Test
    void getMyBookingsChrono_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b2);
        bookings.add(b1);
        when(bookingService.getBookingsForUser(any(String.class),
                any(ChronologicalSortStrategy.class)))
                .thenReturn(bookings);
        List<Booking> b = bookingController.getMyBookingsChrono("A");
        Assertions.assertEquals(bookings, b);
    }

    @Test
    void getMyBookingsLocation_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b2);
        bookings.add(b1);
        when(bookingService.getBookingsForUser(any(String.class), any(LocationStrategy.class)))
                .thenReturn(bookings);
        List<Booking> b = bookingController.getMyBookingsLocation("A");
        Assertions.assertEquals(bookings, b);
    }

}
