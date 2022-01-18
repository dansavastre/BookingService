package nl.tudelft.sem.booking.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import nl.tudelft.sem.booking.objects.Booking;

public class ChronologicalSortStrategy implements SortStrategy {

    @Override
    public List<Booking> sortBookings(List<Booking> bookings) {
        bookings.sort(new DateComparator());
        return bookings;
    }

    protected class DateComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Booking b1 = (Booking) o1;
            Booking b2 = (Booking) o2;
            LocalDate dateB1 = b1.getDate();
            LocalDate dateB2 = b2.getDate();

            if (dateB1.isBefore(dateB2)) {
                return -1;
            }
            if (dateB1.isAfter(dateB2)) {
                return 1;
            } else if (dateB1.isEqual(dateB2)) {
                LocalTime timeB1 = b1.getStartTime();
                LocalTime timeB2 = b2.getStartTime();
                if (timeB1.isBefore(timeB2)) {
                    return -1;
                }
                if (timeB1.isAfter(timeB2)) {
                    return 1;
                }
            }
            return 0;
        }
    }
}

