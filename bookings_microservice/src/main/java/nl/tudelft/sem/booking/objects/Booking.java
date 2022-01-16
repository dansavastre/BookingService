package nl.tudelft.sem.booking.objects;

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
    private int room;

    @Column(name = "BUILDING")
    private int building;

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

    @Column(name = "STATUS")
    private String status;

    /**
     * Parameterised constructor for the Booking class.
     *
     * @param bookingOwner NetID of the user who made the booking
     * @param room         Room number of room
     * @param building     Building number
     * @param date         Date of the booking
     * @param startTime    Start time of booking
     * @param endTime      End time of booking
     * @param purpose      Purpose of booking
     * @param participants Ids of the participants of the meeting
     */
    public Booking(String bookingOwner,
                   int room, int building, LocalDate date, LocalTime startTime,
                   LocalTime endTime, String purpose, List<String> participants) {
        this.bookingOwner = bookingOwner;
        this.room = room;
        this.building = building;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.participants = participants;
        this.status = "valid";
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

    public int getRoom() {
        return room;
    }

    public int getBuilding() {
        return building;
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

    public void setRoom(int room) {
        this.room = room;
    }

    public void setBuilding(int building) {
        this.building = building;
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
                && Objects.equals(building, booking.building)
                && Objects.equals(date, booking.date)
                && Objects.equals(startTime, booking.startTime)
                && Objects.equals(endTime, booking.endTime)
                && Objects.equals(purpose, booking.purpose)
                && participants.equals(booking.participants)
                && Objects.equals(status, booking.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingOwner, room, building, date, startTime,
                endTime, purpose, participants);
    }

    @Override
    public String toString() {
        return "Booking{"
                + "id=" + id
                + ", bookingOwner='" + bookingOwner + '\''
                + ", room='" + room + '\''
                + ", building='" + building + '\''
                + ", date=" + date
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", purpose='" + purpose + '\''
                + ", participants=" + participants.toString()
                + ", status=" + status
                + '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
