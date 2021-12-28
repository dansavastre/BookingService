package nl.tudelft.sem.template.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.repositories.BookingRepository;
import nl.tudelft.sem.template.schedule.DefaultSortStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class BookingServiceTest {

    @Mock
    private transient BookingRepository bookingRepository;

    @InjectMocks
    private transient BookingService bookingService;

    transient Booking b1;
    transient Booking b2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        b1 = new Booking("A", 1, 36, LocalDate.of(2020,  1, 8),
            LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                "Studying", List.of("user0", "user1"));
        b1.setId(1L);
        b2 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
            LocalTime.of(8, 20, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of("user0", "user1"));
        b2.setId(2L);
    }

    @Test
    void getAllBookings_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingRepository.findAll()).thenReturn(bookings);
        Assertions.assertEquals(bookings, bookingService.getAllBookings());
    }

    @Test
    void getBooking_test() {
        when(bookingRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(b1));
        Assertions.assertEquals(b1, bookingService.getBooking(1L));
    }

    @Test
    void addBooking_test() {
        bookingService.addBooking(b1);
        verify(bookingRepository, times(1)).save(b1);
    }

    @Test
    void updateBooking_test() {
        bookingService.updateBooking(1L, b1);
        verify(bookingRepository, times(1)).save(b1);
    }

    @Test
    void updateMyBooking_test() {
        bookingService.updateMyBooking("A", 1L, b1);
        verify(bookingRepository, times(1)).save(b1);
    }

    @Test
    void updateNotMyBooking_test() {
        bookingService.updateMyBooking("B", 1L, b1);
        verify(bookingRepository, times(0)).save(b1);
    }

    @Test
    void getFutureBookings_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingRepository.findFutureBookings()).thenReturn(bookings);
        Assertions.assertEquals(bookings, bookingService.getFutureBookings());
    }

    @Test
    void getBookingsForUser_test() {
        List<Booking> bookings = new ArrayList<>();
        bookings.add(b1);
        bookings.add(b2);
        when(bookingRepository.findFutureBookings()).thenReturn(bookings);
        Assertions.assertEquals(bookings,
                bookingService.getBookingsForUser("A", new DefaultSortStrategy()));
    }

}
