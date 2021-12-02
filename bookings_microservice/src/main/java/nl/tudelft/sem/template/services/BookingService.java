package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.Booking;
import nl.tudelft.sem.template.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings(){
        List<Booking> b = new ArrayList<>();
        bookingRepository.findAll().forEach(b::add);
        return b;
    }

    public Booking getBooking(Long id){
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
