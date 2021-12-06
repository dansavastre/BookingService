package nl.tudelft.sem.template.schedule;


import nl.tudelft.sem.template.objects.Booking;

import java.util.List;

public interface SortStrategy {

    Booking getNextBooking(List<Booking> bookings);

}
