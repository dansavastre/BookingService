package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.Booking;
import nl.tudelft.sem.template.Services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.List;

@RestController
public class BookingsController {

    private final BookingService bookingService;

    @Autowired
    public BookingsController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the bookings microservice!";
    }

    @RequestMapping("/confirmRoomsConnection")
    public String checkIfRoomsConnected(){
        String uri = "http://localhost:8082/getConnectionStatus";
        RestTemplate template = new RestTemplate();
        String result = "Not connected :(";
        try {
            result = template.getForObject(uri, String.class);
        }catch(Exception e){
            result = "Not connected due to Error: " + e.toString();
        }
        return result;
    }

    @RequestMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @RequestMapping("/getBooking/{id}")
    public Booking getBooking(@PathVariable("id") String id){
        Long i = Long.parseLong(id);
        Booking b = bookingService.getBooking(i);
        return b;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bookings")
    public void addBooking(@RequestBody Booking booking){
        bookingService.addBooking(booking);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public void updateBooking(@RequestBody Booking booking, @PathVariable("id") String id){
        bookingService.updateBooking(Long.parseLong(id), booking);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{id}")
    public void deleteBooking(@PathVariable("id") String id){
        bookingService.deleteBooking(Long.parseLong(id));
    }
}
