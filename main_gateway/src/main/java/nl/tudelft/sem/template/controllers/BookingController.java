package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class BookingController {

    private transient RestTemplate restTemplate = new RestTemplate();

    /** Returns list of all bookings in system.
     *
     * @return list of all bookings.
     */
    @GetMapping("/getBookings")
    @ResponseBody
    public List getBookings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/bookings";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<List> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, List.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Returns a specific booking with respect to its id.
     *
     * @param id the id of the booking we want.
     * @return the booking we are searching for.
     */
    @GetMapping("/getBooking/{id}")
    @ResponseBody
    public Booking getBooking(@PathVariable("id") Long id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/getBooking/".concat(String.valueOf(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<Booking> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, Booking.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Adds a booking to the system.
     *
     * @param booking the booking we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBooking")
    @ResponseBody
    public boolean postBooking(@RequestBody Booking booking,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/bookings";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Booking> entity = new HttpEntity<>(booking, headers);

        try {
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
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
    public boolean updateBooking(@RequestBody Booking booking, @PathVariable("id") Long id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Booking> entity = new HttpEntity<>(booking, headers);

        try {
            restTemplate.exchange(uri, HttpMethod.PUT, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Deletes a booking from the system.
     *
     * @param id the id of the booking to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteBooking/{id}")
    @ResponseBody
    public boolean deleteBooking(@PathVariable("id") Long id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            restTemplate.exchange(uri, HttpMethod.DELETE, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

}
