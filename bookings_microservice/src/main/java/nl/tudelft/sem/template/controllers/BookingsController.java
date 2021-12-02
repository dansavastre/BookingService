package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BookingsController {

    private final transient BookingService bookingService;

    @Autowired
    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping("/sayHi")
    public String sayHi() {
        return "Hello from the bookings microservice!";
    }

    /**
     * Test API call that checks if communication between microservices is functional.
     *
     * @return A string telling the user that a connection has been made,
     *         or not and for what reason.
     */
    @RequestMapping("/confirmRoomsConnection")
    public String checkIfRoomsConnected() {
        String uri = "http://localhost:8082/getConnectionStatus";
        RestTemplate template = new RestTemplate();
        try {
            return template.getForObject(uri, String.class);
        } catch (Exception e) {
            return "Not connected :(Not connected due to Error: " + e + ")";
        }
    }

    @RequestMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @RequestMapping("/getBooking/{id}")
    public Booking getBooking(@PathVariable("id") String id) {
        Long i = Long.parseLong(id);
        return bookingService.getBooking(i);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bookings")
    public void addBooking(@RequestBody Booking booking) {
        bookingService.addBooking(booking);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateBooking(@RequestBody Booking booking, @PathVariable("id") String id) {
        bookingService.updateBooking(Long.parseLong(id), booking);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public void deleteBooking(@PathVariable("id") String id) {
        bookingService.deleteBooking(Long.parseLong(id));
    }
}
