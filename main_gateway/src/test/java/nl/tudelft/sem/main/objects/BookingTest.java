package nl.tudelft.sem.main.objects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookingTest {

    private transient Booking booking;

    @BeforeEach
    void setup() {
        booking = new Booking(1L, "Dan", 1, 36,
                LocalDate.of(2021, 12, 4),
                LocalTime.of(11, 10),
                LocalTime.of(15, 45),
                "Group study session",
                List.of("user0", "user1"));
        booking.setStatus("cancelled");
    }

    @Test
    void getId_test() {
        Assertions.assertThat(booking.getId()).isEqualTo(1L);
    }

    @Test
    void getBookingOwner_test() {
        Assertions.assertThat(booking.getBookingOwner()).isEqualTo("Dan");
    }

    @Test
    void getRoom_test() {
        Assertions.assertThat(booking.getRoom()).isEqualTo(1);
    }

    @Test
    void getDate_test() {
        Assertions.assertThat(booking.getDate()).isEqualTo(LocalDate.of(2021, 12, 4));
    }

    @Test
    void getStartTime_test() {
        Assertions.assertThat(booking.getStartTime()).isEqualTo(LocalTime.of(11, 10));
    }

    @Test
    void getEndTime_test() {
        Assertions.assertThat(booking.getEndTime()).isEqualTo(LocalTime.of(15, 45));
    }

    @Test
    void getPurpose_test() {
        Assertions.assertThat(booking.getPurpose()).isEqualTo("Group study session");
    }

    @Test
    void getParticipants_test() {
        Assertions.assertThat(booking.getParticipants()).isEqualTo(List.of("user0", "user1"));
    }

    @Test
    void getStatus_test() {
        Assertions.assertThat(booking.getStatus()).isEqualTo("cancelled");
    }


    @Test
    void setBookingOwner_test() {
        booking.setBookingOwner("John");
        Assertions.assertThat(booking.getBookingOwner()).isEqualTo("John");
    }

    @Test
    void setRoom_test() {
        booking.setRoom(1);
        Assertions.assertThat(booking.getRoom()).isEqualTo(1);
    }

    @Test
    void setDate_test() {
        booking.setDate(LocalDate.of(2022, 1, 12));
        Assertions.assertThat(booking.getDate()).isEqualTo(LocalDate.of(2022, 1, 12));
    }

    @Test
    void setStartTime_test() {
        booking.setStartTime(LocalTime.of(14, 20));
        Assertions.assertThat(booking.getStartTime()).isEqualTo(LocalTime.of(14, 20));
    }

    @Test
    void setEndTime_test() {
        booking.setEndTime(LocalTime.of(20, 0));
        Assertions.assertThat(booking.getEndTime()).isEqualTo(LocalTime.of(20, 0));
    }

    @Test
    void setPurpose_test() {
        booking.setPurpose("Team meeting");
        Assertions.assertThat(booking.getPurpose()).isEqualTo("Team meeting");
    }

    @Test
    void setStatus_test() {
        booking.setStatus("edited");
        Assertions.assertThat(booking.getStatus()).isEqualTo("edited");
    }

}
