package nl.tudelft.sem.template.objects;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "Booking")
@Table(name = "booking")
public class Booking {

    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = IDENTITY,
            generator = "booking_sequence"
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

    @Column(name = "PARTICIPANTS")
    @ElementCollection(targetClass = String.class)
    private List<String> participants;

    /**
     * Parameterised constructor for the Booking class.
     *
     * @param bookingOwner NetID of the user who made the booking
     * @param room         Building number and room number of room
     * @param date         Date of the booking
     * @param startTime    Start time of booking
     * @param endTime      End time of booking
     * @param purpose      Purpose of booking
     */
    public Booking(String bookingOwner,
                   String room, LocalDate date, LocalTime startTime,
                   LocalTime endTime, String purpose, List<String> participants) {
        this.bookingOwner = bookingOwner;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.participants = participants;
    }

    /**
     * Parameterless constructor of booking class. Necessary for database and error handling.
     */
    public Booking() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id)
                && Objects.equals(bookingOwner, booking.bookingOwner)
                && Objects.equals(room, booking.room)
                && Objects.equals(date, booking.date)
                && Objects.equals(startTime, booking.startTime)
                && Objects.equals(endTime, booking.endTime)
                && Objects.equals(purpose, booking.purpose)
                && participants.equals(booking.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingOwner, room, date, startTime,
                endTime, purpose, participants);
    }

    @Override
    public String toString() {
        return "Booking{"
                + "id=" + id
                + ", bookingOwner='" + bookingOwner + '\''
                + ", room='" + room + '\''
                + ", date=" + date
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", purpose='" + purpose + '\''
                + ", participants=" + participants.toString()
                + '}';
    }
}
