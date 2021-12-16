package nl.tudelft.sem.template.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.validators.BookingValidator;
import nl.tudelft.sem.template.validators.BuildingValidator;
import nl.tudelft.sem.template.validators.RoomValidator;
import nl.tudelft.sem.template.validators.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

public class BookingControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient BuildingController buildingController;

    @InjectMocks
    private transient BookingController bookingController;

    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
    private transient Building building;
    private transient Building building1;
    private transient List<Booking> bookings = new ArrayList<>();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        building = new Building(36, LocalTime.of(8, 0),
                                LocalTime.of(22, 0), "Building 36");
        building1 = new Building(1, LocalTime.of(8, 0),
                LocalTime.of(22, 0), "Building 1");
        b1 = new Booking(1L, "Mike", 1, 1,
                LocalDate.of(2025, 12, 4),
                LocalTime.of(11, 10),
                LocalTime.of(15, 45),
                "Group study session",
                List.of("user0", "user1"));
        b2 = new Booking(2L, "Mike", 1, 1,
                LocalDate.of(2025, 12, 5),
                LocalTime.of(10, 30),
                LocalTime.of(16, 0),
                "Project room",
                List.of("user2", "user3"));

        b3 = new Booking(2L, "Mike", 1, 36,
                LocalDate.of(2021, 12, 15),
                LocalTime.of(9, 30),
                LocalTime.of(12, 0),
                "Project room",
                List.of("user2", "user3"));
        bookings.add(b1);
        bookings.add(b2);
    }

    @Test
    void getBookings_test() {
        String uri = "http://localhost:8083/bookings";
        when(restTemplate.getForObject(uri, List.class))
                .thenReturn(bookings);

        Assertions.assertThat(bookingController.getBookings()).isEqualTo(bookings);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
    }

    @Test
    void getBooking_test() {
        String uri = "http://localhost:8083/getBooking/".concat(String.valueOf(1L));
        when(restTemplate.getForObject(uri, Booking.class))
                .thenReturn(b1);
        Assertions.assertThat(bookingController.getBooking(String.valueOf(1L))).isEqualTo(b1);
        verify(restTemplate, times(1)).getForObject(uri, Booking.class);
    }

    //TODO: fix this test
//    @Test
//    void postBooking_test() throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException {
//        RestTemplate template = Mockito.mock(RestTemplate.class);
//        BookingValidator validator = Mockito.mock(BookingValidator.class);
//        String uri = "http://localhost:8083/bookings";
//        when(template.getForObject(eq(anyString()), Building.class)).thenReturn(building1);
//        when(buildingController.getBuilding(eq(anyInt()))).thenReturn(building1);
//        when(validator.handle(any(Booking.class))).thenReturn(true);
//        Assertions.assertThat(bookingController.postBooking(b1)).isTrue();
//        verify(restTemplate, times(1)).postForObject(uri, b1, void.class);
//    }

    @Test
    void postBookingInvalid_test() {
        MockitoAnnotations.initMocks(this);
        when(buildingController.getBuilding(36)).thenReturn(building);
        when(restTemplate.getForObject("http://localhost:8082/getBuilding/36", Building.class))
                .thenReturn(building);
        Assertions.assertThat(bookingController.postBooking(b3)).isFalse();
    }

    @Test
    void updateBooking_test() {
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(1L));
        Assertions.assertThat(bookingController.updateBooking(b2, String.valueOf(1L))).isTrue();
        verify(restTemplate, times(1)).put(uri, b2);
    }

    @Test
    void deleteBooking_test() {
        String uri = "http://localhost:8081/bookings/".concat(String.valueOf(1L));
        Assertions.assertThat(bookingController.deleteBooking(String.valueOf(1L))).isTrue();
        verify(restTemplate, times(1)).delete(uri);
    }

}
