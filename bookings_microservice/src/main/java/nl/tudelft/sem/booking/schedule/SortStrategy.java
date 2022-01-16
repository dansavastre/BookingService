package nl.tudelft.sem.booking.schedule;

import java.util.List;
import nl.tudelft.sem.booking.objects.Booking;


public interface SortStrategy {

    List<Booking> sortBookings(List<Booking> bookings);

}
