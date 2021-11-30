package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.Booking;
import nl.tudelft.sem.template.Objects.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BookingController {

    @RequestMapping("/getBookings")
    public List<Booking> getBookings(){
        String uri = "http://localhost:8083/bookings";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }


    @RequestMapping("/getBooking/{id}")
    public Booking getBooking(@PathVariable("id") String id){
        String uri = "http://localhost:8083/getBooking/".concat(id);
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Booking.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postBooking")
    public boolean postBooking(@RequestBody Booking booking){
        try {
            String uri = "http://localhost:8083/bookings";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, booking, void.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putBooking/{id}")
    public boolean updateBooking(@RequestBody Booking booking, @PathVariable("id") String id){
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            RestTemplate template = new RestTemplate();
            template.put(uri, booking);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBooking/{id}")
    public boolean deleteBooking(@PathVariable("id") String id){
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
