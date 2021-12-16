package nl.tudelft.sem.template.schedule;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;


public interface SortStrategy {

    List<Booking> sortBookings(List<Booking> bookings);

}
