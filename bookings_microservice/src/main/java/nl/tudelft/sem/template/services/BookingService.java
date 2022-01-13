package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.repositories.BookingRepository;
import nl.tudelft.sem.template.schedule.Schedule;
import nl.tudelft.sem.template.schedule.SortStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private transient BookingRepository bookingRepository;

    /**
     * Gets a List of all Bookings from the database.
     *
     * @return A List of Booking objects.
     */
    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingRepository.findAll());
    }

    public List<Booking> getFutureBookings() {
        return new ArrayList<>(bookingRepository.findFutureBookings());
    }

    public Booking getBooking(Long id) {
       return bookingRepository.findById(id).get();
    }

    public void addBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void updateBooking(Long id, Booking booking) {
        bookingRepository.save(booking);
    }

    /**
     * Method that allows a user to edit their own bookings.
     *
     * @param userId the id of the user
     * @param id the id of the booking
     * @param booking the booking to be edited
     */
    public void updateMyBooking(String userId, Long id, Booking booking) {
        if (booking.getBookingOwner().equals(userId)) {
            bookingRepository.save(booking);
        }
    }

    /**
     * Method that allows a user to delete their own bookings.
     *
     * @param userId the id of the user
     * @param id the id of the booking
     */
    public void deleteMyBooking(String userId, Long id) {
        Booking booking = getBooking(id);
        if (booking.getBookingOwner().equals(userId)) {
            bookingRepository.deleteById(id);
        }
    }

    /** get the schedule for the user using the specified sorting strategy.
     *
     * @param id            id of the user
     * @param sortStrategy  sorting strategy for bookings
     * @return              list of user's bookings in correct order
     */
    public List<Booking> getBookingsForUser(String id, SortStrategy sortStrategy) {
        List<Booking> bookings = getFutureBookings();
        bookings = bookings.stream().filter(b -> b.getBookingOwner().equals(id))
                .collect(Collectors.toList());
        Schedule s = new Schedule(bookings, sortStrategy);
        return s.sortBookings();
    }
}
