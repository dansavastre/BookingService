package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class BookingController {

    /** Returns list of all bookings in system.
     *
     * @return list of all bookings.
     */
    @GetMapping("/getBookings")
    @ResponseBody
    public List getBookings() {
        String uri = "http://localhost:8083/bookings";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }

    /** Returns a specific booking with respect to its id.
     *
     * @param id the id of the booking we want.
     * @return the booking we are searching for.
     */
    @GetMapping("/getBooking/{id}")
    @ResponseBody
    public Booking getBooking(@PathVariable("id") String id) {
        String uri = "http://localhost:8083/getBooking/".concat(id);
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Booking.class);
    }

    /** Adds a booking to the system.
     *
     * @param booking the booking we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBooking")
    @ResponseBody
    public boolean postBooking(@RequestBody Booking booking) {
        try {
            String uri = "http://localhost:8083/bookings";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, booking, void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Update a booking.
     *
     * @param booking the new booking.
     * @param id the id of the booking to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBooking/{id}")
    @ResponseBody
    public boolean updateBooking(@RequestBody Booking booking, @PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            RestTemplate template = new RestTemplate();
            template.put(uri, booking);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Deletes a booking from the system.
     *
     * @param id the id of the booking to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteBooking/{id}")
    @ResponseBody
    public boolean deleteBooking(@PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
