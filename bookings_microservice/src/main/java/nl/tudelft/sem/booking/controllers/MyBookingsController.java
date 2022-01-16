package nl.tudelft.sem.booking.controllers;

import java.util.List;
import nl.tudelft.sem.booking.objects.Booking;
import nl.tudelft.sem.booking.schedule.ChronologicalSortStrategy;
import nl.tudelft.sem.booking.schedule.DefaultSortStrategy;
import nl.tudelft.sem.booking.schedule.LocationStrategy;
import nl.tudelft.sem.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/myBookings")
public class MyBookingsController {

    @Autowired
    private transient BookingService bookingService;

    @Autowired
    private transient Authorization auth;

    private static final String userIdPath = "userId";

    @PutMapping("/{userId}/{id}")
    @ResponseBody
    public void updateMyBooking(@RequestBody Booking booking,
                                @PathVariable(userIdPath) String userId,
                                @PathVariable("id") Long id,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorizeWithUsername(Authorization.EMPLOYEE, token, userId);
        bookingService.updateMyBooking(userId, id, booking);
    }


    @DeleteMapping("/{userId}/{id}")
    @ResponseBody
    public void deleteBooking(@PathVariable(userIdPath) String userId,
                              @PathVariable("id") Long id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        auth.authorizeWithUsername(Authorization.EMPLOYEE, token, userId);
        bookingService.deleteMyBooking(userId, id);
    }

    @GetMapping("/default/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsDefault(@PathVariable(userIdPath) String userId,
                                              @RequestHeader(HttpHeaders
                                                      .AUTHORIZATION) String token) {
        auth.authorizeWithUsername(Authorization.EMPLOYEE, token, userId);
        return bookingService.getBookingsForUser(userId, new DefaultSortStrategy());
    }

    @GetMapping("/chrono/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsChrono(@PathVariable(userIdPath) String userId,
                                             @RequestHeader(HttpHeaders
                                                     .AUTHORIZATION) String token) {
        auth.authorizeWithUsername(Authorization.EMPLOYEE, token, userId);
        return bookingService.getBookingsForUser(userId, new ChronologicalSortStrategy());
    }

    @GetMapping("/location/{userId}")
    @ResponseBody
    public List<Booking> getMyBookingsLocation(@PathVariable(userIdPath) String userId,
                                               @RequestHeader(HttpHeaders
                                                       .AUTHORIZATION) String token) {
        auth.authorizeWithUsername(Authorization.EMPLOYEE, token, userId);
        return bookingService.getBookingsForUser(userId, new LocationStrategy());
    }
}
