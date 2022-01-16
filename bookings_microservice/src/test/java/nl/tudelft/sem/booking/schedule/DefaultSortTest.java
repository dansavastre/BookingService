package nl.tudelft.sem.booking.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.booking.objects.Booking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DefaultSortTest {
    private transient DefaultSortStrategy def;
    private transient List<Booking> bookingList;
    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
    private transient Booking b4;
    private transient String studying = "Studying";

    @BeforeEach
    void setup() {
        def = new DefaultSortStrategy();
        bookingList = new ArrayList<>();
        List<String> p = new ArrayList<>();
        p.add("user0");
        p.add("user1");
        b1 = new Booking("A", 1, 36, LocalDate.of(2022, 1, 8),
                LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                studying, p);
        b2 = new Booking("B", 3, 36, LocalDate.of(2020, 8, 8),
                LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                studying, p);
        b3 = new Booking("C", 1, 42, LocalDate.of(2021, 4, 8),
                LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                studying, p);
        b4 = new Booking("D", 3, 42, LocalDate.of(2021, 1, 8),
                LocalTime.of(18, 45, 0), LocalTime.of(12, 45, 0),
                studying, p);

    }

    @Test
    void sortTest() {
        List<Booking> result = new ArrayList<>();
        result.add(b2);
        result.add(b4);
        result.add(b3);
        result.add(b1);
        b1.setId(123L);
        b2.setId(2L);
        b3.setId(56L);
        b4.setId(12L);
        bookingList.add(b1);
        bookingList.add(b2);
        bookingList.add(b3);
        bookingList.add(b4);
        Assertions.assertEquals(result, def.sortBookings(bookingList));
    }
}
