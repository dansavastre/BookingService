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
import org.mockito.MockitoAnnotations;


public class MyBookingsControllerTest {

    @Mock
    private transient BookingService bookingService;
    @Mock
    private transient Authorization auth;

    @InjectMocks
    private transient MyBookingsController myBookingsController;

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
    void updateMyBooking_Test() {
        myBookingsController.updateMyBooking(b1, "A", 1L, token);
        verify(bookingService, times(1)).updateMyBooking("A", 1L, b1);
        verify(auth, times(1)).authorizeWithUsername(Authorization.EMPLOYEE, token, "A");
    }

    @Test
    void deleteMyBooking_test() {
        myBookingsController.deleteBooking("A", 1L, token);
        verify(bookingService, times(1)).deleteMyBooking("A", 1L);
        verify(auth, times(1)).authorizeWithUsername(Authorization.EMPLOYEE, token, "A");
    }


    @Test
    void getMyBookingsDefault_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b2);
        bookings.add(b1);
        when(bookingService.getBookingsForUser(any(String.class), any(DefaultSortStrategy.class)))
                .thenReturn(bookings);
        List<Booking> b = myBookingsController.getMyBookingsDefault("A", token);
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
        List<Booking> b = myBookingsController.getMyBookingsChrono("A", token);
        Assertions.assertEquals(bookings, b);
    }

    @Test
    void getMyBookingsLocation_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b2);
        bookings.add(b1);
        when(bookingService.getBookingsForUser(any(String.class), any(LocationStrategy.class)))
                .thenReturn(bookings);
        List<Booking> b = myBookingsController.getMyBookingsLocation("A", token);
        Assertions.assertEquals(bookings, b);
    }
}
