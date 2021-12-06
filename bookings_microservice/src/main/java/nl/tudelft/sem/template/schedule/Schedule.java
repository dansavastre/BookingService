package nl.tudelft.sem.template.schedule;


import nl.tudelft.sem.template.objects.Booking;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    List<Booking> bookings;
    SortStrategy sortStrategy;

    public Schedule(List<Booking> bookings, SortStrategy sortStrategy) {
        this.bookings = bookings;
        this.sortStrategy = sortStrategy;
    }

    public Schedule(SortStrategy sortStrategy) {
        bookings = new ArrayList<>();
        this.sortStrategy = sortStrategy;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public Booking getNextBooking() {
        return this.sortStrategy.getNextBooking(bookings);
    }
}
