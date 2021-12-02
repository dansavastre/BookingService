package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class BookingService {

    @Autowired
    private transient BookingRepository bookingRepository;

    /**
     * Gets a List of all Bookings from the database.
     *
     * @return A List of Booking objects.
     */
    public List<Booking> getAllBookings() {
        List<Booking> b = new ArrayList<>();
        bookingRepository.findAll().forEach(b::add);
        return b;
    }

    public Booking getBooking(Long id) {
        return bookingRepository.findById(id).get();
    }

    public void addBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void updateBooking(Long id, Booking booking) {
        bookingRepository.deleteById(id);
        bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
