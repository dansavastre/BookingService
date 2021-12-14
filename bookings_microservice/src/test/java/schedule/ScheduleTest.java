package schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.schedule.ChronologicalSortStrategy;
import nl.tudelft.sem.template.schedule.DefaultSortStrategy;
import nl.tudelft.sem.template.schedule.Schedule;
import nl.tudelft.sem.template.schedule.SortStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ScheduleTest {
    transient Schedule sched;
    transient List<Booking> bookingList;
    transient Booking b1;
    transient Booking b2;
    transient Booking b3;
    transient Booking b4;
    transient SortStrategy sort;
    transient String user0;
    transient String user1;

    @BeforeEach
    void setup() {
        bookingList = new ArrayList<>();
        sort = new ChronologicalSortStrategy();
        sched = new Schedule(bookingList, sort);
        user0 = "user0";
        user1 = "user1";
        b1 = new Booking("A", 1, 36, LocalDate.of(2021, 1, 8),
                LocalTime.of(10, 45, 0), LocalTime.of(12, 45, 0),
                "Studying", List.of(user0, user1));
        b2 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
                LocalTime.of(8, 20, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of(user0, user1));
        b3 = new Booking("A", 1, 36, LocalDate.of(2020, 1, 5),
                LocalTime.of(8, 30, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of(user0, user1));
        b4 = new Booking("A", 2, 42, LocalDate.of(2021, 1, 5),
                LocalTime.of(8, 30, 0), LocalTime.of(15, 45, 0),
                "Project meeting", List.of(user0, user1));
    }

    @Test
    void sortBookingsTest() {
        List<Booking> result = new ArrayList<>();
        result.add(b2);
        result.add(b3);
        result.add(b1);
        bookingList.add(b1);
        bookingList.add(b2);
        bookingList.add(b3);
        Assertions.assertEquals(result, sched.sortBookings());
    }

    @Test
    void addBookingTest() {
        List<Booking> result = new ArrayList<>();
        result.add(b1);
        result.add(b2);
        result.add(b3);
        result.add(b4);
        bookingList.add(b1);
        bookingList.add(b2);
        bookingList.add(b3);
        sched.addBooking(b4);
        Assertions.assertEquals(result, bookingList);
    }

    @Test
    void getBookingTest() {
        Assertions.assertEquals(bookingList, sched.getBookings());
    }

    @Test
    void setBookingTest() {
        List<Booking> result = new ArrayList<>();
        result.add(b1);
        result.add(b2);
        result.add(b3);
        result.add(b4);
        sched.setBookings(result);
        Assertions.assertEquals(result, sched.getBookings());
    }

    @Test
    void getSortStrategyTest() {
        Assertions.assertEquals(sort, sched.getSortStrategy());
    }

    @Test
    void setSortStrategy() {
        SortStrategy sort1 = new DefaultSortStrategy();
        sched.setSortStrategy(sort1);
        Assertions.assertEquals(sort1, sched.getSortStrategy());
    }

    @Test
    void emptyConstructor() {
        Schedule emptySched = new Schedule(new DefaultSortStrategy());
        Assertions.assertEquals(new ArrayList<>(), emptySched.getBookings());
    }
}
