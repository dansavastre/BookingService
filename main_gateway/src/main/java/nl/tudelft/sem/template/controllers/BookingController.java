package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.validators.BookingValidator;
import nl.tudelft.sem.template.validators.BuildingValidator;
import nl.tudelft.sem.template.validators.RoomValidator;
import nl.tudelft.sem.template.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    private transient RestTemplate restTemplate;

    @Bean
    public RestTemplate templateCreator() {
        return new RestTemplate();
    }

    @Autowired
    private transient Validator handler;


    /**
     * Create bean for validator, so it can be mocked for tests.
     *
     * @return A new instance of a Validator.
     */
    @Bean
    public Validator validatorCreator() {
        Validator handler = new BookingValidator();
        handler.setNext(new BuildingValidator());
        handler.setNext(new RoomValidator());
        return handler;
    }

    /**
     * Returns list of all bookings in system.
     *
     * @return list of all bookings.
     */
    @GetMapping("/getBookings")
    @ResponseBody
    public List getBookings() {
        String uri = "http://localhost:8083/bookings";
        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Returns a specific booking with respect to its id.
     *
     * @param id the id of the booking we want.
     * @return the booking we are searching for.
     */
    @GetMapping("/getBooking/{id}")
    @ResponseBody
    public Booking getBooking(@PathVariable("id") String id) {
        String uri = "http://localhost:8083/getBooking/".concat(id);
        return restTemplate.getForObject(uri, Booking.class);
    }

    /**
     * Adds a booking to the system.
     *
     * @param booking the booking we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBooking")
    @ResponseBody
    public boolean postBooking(@RequestBody Booking booking) {
        try {
            boolean isValid = handler.handle(booking);
            if (isValid) {
                String uri = "http://localhost:8083/bookings";
                restTemplate.postForObject(uri, booking, void.class);
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Update a booking.
     *
     * @param booking the new booking.
     * @param id      the id of the booking to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBooking/{id}")
    @ResponseBody
    public boolean updateBooking(@RequestBody Booking booking, @PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            restTemplate.put(uri, booking);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Deletes a booking from the system.
     *
     * @param id the id of the booking to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteBooking/{id}")
    @ResponseBody
    public boolean deleteBooking(@PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/bookings/".concat(id);
            restTemplate.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
