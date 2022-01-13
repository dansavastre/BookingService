package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.validators.BookingValidator;
import nl.tudelft.sem.template.validators.BuildingValidator;
import nl.tudelft.sem.template.validators.RoomValidator;
import nl.tudelft.sem.template.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired
    private transient RestTemplate restTemplate;
    @Autowired
    private transient BuildingController buildingController;

    @Autowired
    private transient BookingController autowiredBookingController;

    @Autowired
    private transient MainRoomController mainRoomController;

    @Bean
    public RestTemplate templateCreator() {
        return new RestTemplate();
    }

    private static final String userIdPath = "userId";



    /**
     * Create bean for validator, so it can be mocked for tests.
     *
     * @return A new instance of a Validator.
     */
    public Validator validatorCreator(String token) {
        Validator handler = new BookingValidator(buildingController,
                mainRoomController,
                autowiredBookingController);
        handler.setToken(token);
        Validator buildingValidator = new BuildingValidator(buildingController);
        buildingValidator.setToken(token);
        Validator roomValidator = new RoomValidator(autowiredBookingController);
        roomValidator.setToken(token);
        buildingValidator.setNext(roomValidator);
        handler.setNext(buildingValidator);
        return handler;
    }

    /**
     * Validates the booking.
     *
     * @param booking   booking to be validated
     * @param token     users authorization token
     * @return          true if the booking is valid (can be added), false otherwise
     * @throws InvalidBookingException      if the booking doesn't meet the requirements
     * @throws InvalidRoomException         if the room in the booking is invalid
     * @throws BuildingNotOpenException     if the building in the booking is invalid
     */
    protected boolean validateBooking(Booking booking, String token)
            throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException {
        Validator handler = validatorCreator(token);
        return handler.handle(booking);
    }

    /**
     * Returns list of all bookings in system.
     *
     * @return list of all bookings.
     */
    @GetMapping("/getallbookings")
    @ResponseBody
    public List getAllBookings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/allbookings";
        return getList(token, uri);
    }

    /**
     * Returns a list of all future bookings, requires token.

     * @param token for authentication
     * @return list of future bookings
     */
    @GetMapping("/getBookings")
    @ResponseBody
    public List getFutureBookings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/bookings";
        return getList(token, uri);
    }

    /**
     * Returns a specific booking with respect to its id.
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
        return sendGetBookingRequest(headers, uri);
    }

    protected Booking sendGetBookingRequest(HttpHeaders headers, String uri) {
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<Booking> res = restTemplate
                    .exchange(uri, HttpMethod.GET, entity, Booking.class);
            if (res.getBody() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else {
                return res.getBody();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Method for a secretary to add a booking for an employee in their research group.
     *
     * @param booking the booking we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBooking/{groupId}/{secretaryId}")
    @ResponseBody
    public boolean postBookingForGroup(@PathVariable("groupId") Long groupId,
                                       @PathVariable("secretaryId") String secretaryId,
                                       @RequestBody Booking booking,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/secretary/checkGroup/"
                + groupId + "/"
                + secretaryId + "/"
                + booking.getBookingOwner();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        try {
            ResponseEntity<Boolean> res =
                    restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class);
            boolean isGroupSecretary = Boolean.TRUE.equals(res.getBody());
            if (isGroupSecretary) {

                postBooking(booking, token);
                return true;
            } else {
                return false;
            }
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Adds a booking to the system.
     *
     * @param booking the booking we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBooking")
    @ResponseBody
    public boolean postBooking(@RequestBody Booking booking,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (validateBooking(booking, token)) {
                String uri = "http://localhost:8083/bookings";
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, token);
                booking.setStatus("valid");
                HttpEntity<Booking> entity = new HttpEntity<>(booking, headers);
                restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    /**
     * Update any booking as an admin.
     *
     * @param booking the new booking.
     * @param id      the id of the booking to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBooking/{id}")
    @ResponseBody
    public boolean updateBooking(@RequestBody Booking booking, @PathVariable("id") Long id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            if (validateBooking(booking, token)) {
                String uri = "http://localhost:8083/bookings/".concat(String.valueOf(id));
                return sendPutBookingRequest(booking, token, uri);
            }
            return false;

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Update your booking as an employee.
     *
     * @param booking the new booking.
     * @param id      the id of the booking to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBooking/{userId}/{id}")
    @ResponseBody
    public boolean updateBooking(@RequestBody Booking booking,
                                 @PathVariable(userIdPath) String userId,
                                 @PathVariable("id") Long id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        try {
            if (validateBooking(booking, token) && !booking.getStatus().startsWith("cancelled")) {
                String uri = "http://localhost:8083/myBookings/".concat(userId + "/" + String.valueOf(id));
                return sendPutBookingRequest(booking, token, uri);
            }
            return false;

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Deletes your booking from the system as an employee.
     *
     * @param id the id of the booking to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteBooking/{userId}/{id}")
    @ResponseBody
    public boolean deleteBooking(@PathVariable(userIdPath) String userId,
                                 @PathVariable("id") Long id,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/myBookings/".concat(userId + "/" + String.valueOf(id));
        try {
            return sendDeleteBookingRequest(token, uri);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Helper method for sending delete request for a booking.
     *
     * @param token the user's authorization token
     * @param uri address to send the request to
     * @return true if request is successful
     * @throws HttpClientErrorException when response was not 200 OK
     */
    protected boolean sendDeleteBookingRequest(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                                   String token,
                                                   String uri) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        restTemplate.exchange(uri, HttpMethod.DELETE, entity, void.class);
        return true;
    }

    @GetMapping("/myBookings/default/{userId}")
    @ResponseBody
    public List getMyBookingsDefault(@PathVariable(userIdPath) String userId,
                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/myBookings/default/" + userId;
        return getList(token, uri);
    }

    @GetMapping("/myBookings/chrono/{userId}")
    @ResponseBody
    public List getMyBookingsChrono(@PathVariable(userIdPath) String userId,
                                    @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/myBookings/chrono/" + userId;
        return getList(token, uri);
    }

    @GetMapping("/myBookings/location/{userId}")
    @ResponseBody
    public List getMyBookingsLocation(@PathVariable(userIdPath) String userId,
                                      @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8083/myBookings/location/" + userId;
        return getList(token, uri);
    }

    /** Sends a request for retrieving a list.
     *
     * @param token     user's token
     * @param uri       url path
     * @return          retrieved list
     */
    private List getList(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        try {
            ResponseEntity<List> res =
                    restTemplate.exchange(uri, HttpMethod.GET, entity, List.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Sends a put request with a booking and the authorization token.
     *
     * @param booking       booking to be put
     * @param token         users authorization token
     * @param uri           address to send the request
     * @return              true if everything when ok
     * @throws HttpClientErrorException  when the response was not 200 OK
     */
    protected boolean sendPutBookingRequest(@RequestBody Booking booking,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                          String uri) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Booking> entity = new HttpEntity<>(booking, headers);
        restTemplate.exchange(uri, HttpMethod.PUT, entity, void.class);
        return true;
    }

}
