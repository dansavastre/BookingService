package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.schedule.ChronologicalSortStrategy;
import nl.tudelft.sem.template.schedule.DefaultSortStrategy;
import nl.tudelft.sem.template.schedule.LocationStrategy;
import nl.tudelft.sem.template.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class BookingController {

    @Autowired
    private transient BookingService bookingService;

    @Autowired
    private transient RestTemplate template;

    @Autowired
    private transient Authorization auth;

    @Bean
    public RestTemplate templateCreator() {
        return new RestTemplate();
    }

    @Bean
    public BookingService serviceCreator() {
        return new BookingService();
    }

    private static final String userIdPath = "userId";

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
    public List<Booking> getAllBookings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return bookingService.getAllBookings();
    }

    @GetMapping("/bookings")
    @ResponseBody
    public List<Booking> getFutureBookings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return bookingService.getFutureBookings();
    }

    @GetMapping("/getBooking/{id}")
    @ResponseBody
    public Booking getBooking(@PathVariable("id") Long id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return bookingService.getBooking(id);
    }

    @PostMapping("/bookings")
    @ResponseBody
    public void addBooking(@RequestBody Booking booking,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        bookingService.addBooking(booking);
    }

    @PostMapping("/bookingsForGroup")
    @ResponseBody
    public void addBookingForGroup(@RequestBody Booking booking,
                           @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.SECRETARY, token);
        bookingService.addBooking(booking);
    }

    @PutMapping("/bookings/{id}")
    @ResponseBody
    public void updateBooking(@RequestBody Booking booking, @PathVariable("id") Long id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorize(Authorization.ADMIN, token);
        bookingService.updateBooking(id, booking);
    }

}
