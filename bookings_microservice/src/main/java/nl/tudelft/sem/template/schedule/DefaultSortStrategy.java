package nl.tudelft.sem.template.schedule;

import java.util.Comparator;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;

public class DefaultSortStrategy implements SortStrategy {
 
    @Override
    public List<Booking> sortBookings(List<Booking> bookings) {
        bookings.sort(new BookingComparator());
        return bookings;
    }

    protected class BookingComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            Booking b1 = (Booking) o1;
            Booking b2 = (Booking) o2;
            return Long.compare(b1.getId(), b2.getId());
        }
    }
}
