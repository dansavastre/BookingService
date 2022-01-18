package nl.tudelft.sem.main.validators;

import nl.tudelft.sem.main.controllers.BookingController;
import nl.tudelft.sem.main.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.main.exceptions.InvalidBookingException;
import nl.tudelft.sem.main.exceptions.InvalidRoomException;
import nl.tudelft.sem.main.objects.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RoomValidatorTest {

    @Mock
    private transient BookingController bookingController;

    @Mock
    private transient BuildingValidator buildingValidator;

    private transient Validator validator;
    private transient String token;
    private transient Booking b1;
    private transient Booking b2;
    private transient Booking b3;
    private transient Booking b4;
    private transient Booking b5;
    private transient Booking b6;
    private transient Booking b7;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        validator = new RoomValidator(bookingController);
        token = "Security_Token";
        validator.setToken(token);

        b1 = new Booking(1L, "ltwubben",
                1, 24, LocalDate.now().plusDays(1),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                "studying", List.of("bserbanescu", "vmadhu"));
        b2 = new Booking(2L, "james",
                2, 24, LocalDate.now().plusDays(2),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                "World Domination", List.of("Frank"));
        b3 = new Booking(3L, "Andy",
                1, 24, LocalDate.now().plusDays(1),
                LocalTime.of(12, 30), LocalTime.of(14, 0),
                "Research Group", List.of());
        b4 = new Booking(4L, "Mauricio",
                1, 24, LocalDate.now().plusDays(1),
                LocalTime.of(11, 30), LocalTime.of(12, 0),
                "Mutation Testing Session", List.of("Annibale", "Lofi"));
        b5 = new Booking(5L, "bserbanescu",
                1, 24, LocalDate.now().plusDays(2),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                "studying", List.of());
        b6 = new Booking(6L, "ltwubben",
                1, 24, LocalDate.now().plusDays(1),
                LocalTime.of(11, 0), LocalTime.of(12, 0),
                "research", List.of("vmadhu"));
        b7 = new Booking(7L, "keshavnair",
                1, 24, LocalDate.now().plusDays(1),
                LocalTime.of(12, 0), LocalTime.of(13, 0),
                "meeting", List.of());
    }

    @Test
    void setTokenTest() {
        String newToken = "New_Security_Token";
        assertEquals(token, validator.getToken());
        validator.setToken(newToken);
        assertEquals("New_Security_Token", validator.getToken());
    }

    @Test
    void bookingsOverlapTest() {
        assertTrue(validator.bookingsOverlap(b1, b3));
        assertTrue(validator.bookingsOverlap(b1, b7));
        b7.setStartTime(LocalTime.of(10, 0));
        assertTrue(validator.bookingsOverlap(b1, b7));
        b7.setStartTime(LocalTime.of(12, 0));
        b7.setEndTime(LocalTime.of(13, 1));
        assertTrue(validator.bookingsOverlap(b1, b7));
    }


    @Test
    void bookingsDoNotOverlapTest() {
        assertFalse(validator.bookingsOverlap(b1, b2));
        assertFalse(validator.bookingsOverlap(b2, b3));
        assertFalse(validator.bookingsOverlap(b1,b5));
        assertFalse(validator.bookingsOverlap(b3, b4));
        assertFalse(validator.bookingsOverlap(b1, b6));
        assertFalse(validator.bookingsOverlap(b6, b7));
    }

    @Test
    void bookPossibleBookingTest() throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException {
        when(bookingController.getAllBookings(token)).thenReturn(List.of(b2));
        assertTrue(validator.handle(b1));
    }

    @Test
    void bookPossibleBookingBuildingAcceptsTest() throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException {
        validator.setNext(buildingValidator);
        when(bookingController.getAllBookings(token)).thenReturn(List.of(b2));
        when(buildingValidator.handle(b1)).thenReturn(true);
        assertTrue(validator.handle(b1));
    }

    @Test
    void bookPossibleBookingBuildingRejectsTest() throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException {
        validator.setNext(buildingValidator);
        when(bookingController.getAllBookings(token)).thenReturn(List.of(b2));
        when(buildingValidator.handle(b1)).thenReturn(false);
        assertFalse(validator.handle(b1));
    }

    @Test
    void bookImpossibleBookingTest() {
        when(bookingController.getAllBookings(token)).thenReturn(List.of(b2, b3));
        assertThrows(InvalidRoomException.class, () -> validator.handle(b1));
    }
}
