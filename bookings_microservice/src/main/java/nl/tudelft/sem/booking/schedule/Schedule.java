package nl.tudelft.sem.booking.schedule;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.booking.objects.Booking;

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

    public List<Booking> sortBookings() {
        return this.sortStrategy.sortBookings(bookings);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public SortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
}
