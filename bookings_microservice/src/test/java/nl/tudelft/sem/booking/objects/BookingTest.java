package nl.tudelft.sem.booking.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingTest {
    transient Booking b1;
    transient Booking b2;
    transient Booking b3;

    @BeforeEach
    void setup() {

        b1 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 8),
            LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                "Studying", List.of("user0", "user1"));
        b2 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
            LocalTime.of(8, 20, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of("user0", "user1"));
        b3 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
            LocalTime.of(8, 20, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of("user0", "user1"));
    }

    @Test
    void equals_same_object_test() {
        assertTrue(b1.equals(b1));
    }

    @Test
    void equals_not_building_test() {
        assertFalse(b1.equals("b1"));
    }

    @Test
    void equals_different_test() {
        assertFalse(b1.equals(b2));
    }

    @Test
    void equals_same_test() {
        assertTrue(b2.equals(b3));
    }

    @Test
    void toString_test() {
        b1.setId(1L);
        String expected = "Booking{id=1, bookingOwner='A', room='1', building='36', "
            + "date=2020-01-08, startTime=10:45, endTime=12:45, purpose='Studying', "
                + "participants=[user0, user1], status=valid}";
        assertEquals(expected, b1.toString());
    }

}
