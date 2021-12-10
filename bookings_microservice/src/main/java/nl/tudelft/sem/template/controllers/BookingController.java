package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.schedule.ChronologicalSortStrategy;
import nl.tudelft.sem.template.schedule.DefaultSortStrategy;
import nl.tudelft.sem.template.schedule.LocationStrategy;
import nl.tudelft.sem.template.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class BookingController {

    @Autowired
    private transient BookingService bookingService;

    private transient RestTemplate template = new RestTemplate();

    @GetMapping("/sayHi")
    @ResponseBody
    public String sayHi() {
        return "Hello from the bookings microservice!";
    }

    /**
     * Test API call that checks if communication between microservices is functional.
     *
     * @return A string telling the user that a connection has been made,
     *         or not and for what reason.
     */
    @GetMapping("/confirmRoomsConnection")
    @ResponseBody
    public String checkIfRoomsConnected() {
        String uri = "http://localhost:8082/getConnectionStatus";
        try {
            return template.getForObject(uri, String.class);
        } catch (Exception e) {
            return "Not connected due to Error: " + e + ")";
        }
    }

    @GetMapping("/allbookings")
    @ResponseBody
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/bookings")
    @ResponseBody
    public List<Booking> getFutureBookings() {
        return bookingService.getFutureBookings();
    }

    @GetMapping("/getBooking/{id}")
    @ResponseBody
    public Booking getBooking(@PathVariable("id") Long id) {
        return bookingService.getBooking(id);
    }

    @PostMapping("/bookings")
    @ResponseBody
    public void addBooking(@RequestBody Booking booking) {
        bookingService.addBooking(booking);
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public void updateBooking(@RequestBody Booking booking, @PathVariable("id") Long id) {
        bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public void deleteBooking(@PathVariable("id") Long id) {
        bookingService.deleteBooking(id);
    }

    @GetMapping("/myBookings/default/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsDefault(@PathVariable("userId") String userId) {
        return bookingService.getBookingsForUser(userId, new DefaultSortStrategy());
    }

    @GetMapping("/myBookings/chrono/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsChrono(@PathVariable("userId") String userId) {
        return bookingService.getBookingsForUser(userId, new ChronologicalSortStrategy());
    }

    @GetMapping("/myBookings/location/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsLocation(@PathVariable("userId") String userId) {
        return bookingService.getBookingsForUser(userId, new LocationStrategy());
    }
}
