package nl.tudelft.sem.template.Objects;

import jdk.jfr.Timestamp;

import javax.persistence.*;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "Booking")
@Table(name = "booking")
public class Booking {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = IDENTITY,
            generator = "user_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "BOOKING_OWNER")
    private String bookingOwner;

    @Column(name = "ROOM")
    private String room;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(name = "PURPOSE")
    private String purpose;

    public Booking(List<String> participants, String bookingOwner, String room, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose) {
        this.bookingOwner = bookingOwner;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
    }

    public Booking() {

    }

    public Long getId() {
        return id;
    }

    public String getBookingOwner() {
        return bookingOwner;
    }

    public String getRoom() {
        return room;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBookingOwner(String bookingOwner) {
        this.bookingOwner = bookingOwner;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id && Objects.equals(bookingOwner, booking.bookingOwner) && Objects.equals(room, booking.room) && Objects.equals(date, booking.date) && Objects.equals(startTime, booking.startTime) && Objects.equals(endTime, booking.endTime) && Objects.equals(purpose, booking.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingOwner, room, date, startTime, endTime, purpose);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingOwner='" + bookingOwner + '\'' +
                ", room='" + room + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", purpose='" + purpose + '\'' +
                '}';
    }
}
