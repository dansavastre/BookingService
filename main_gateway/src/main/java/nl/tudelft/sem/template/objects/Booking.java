package nl.tudelft.sem.template.objects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Booking {

    private transient Long id;
    private transient String bookingOwner;
    private transient int room;
    private transient int building;
    private transient LocalDate date;
    private transient LocalTime startTime;
    private transient LocalTime endTime;
    private transient String purpose;
    private transient List<String> participants;

    /** Constructor for Booking.
     *
     * @param id the id
     * @param bookingOwner netId of person who made booking.
     * @param room the id of the room & building that is booked
     * @param date the date of the booking.
     * @param startTime the start time of the booking.
     * @param endTime the end time of the booking.
     * @param purpose the purpose of the meeting.
     */
    public Booking(Long id, String bookingOwner, int room, int building, LocalDate date,
                   LocalTime startTime, LocalTime endTime, String purpose,
                   List<String> participants) {
        this.id = id;
        this.bookingOwner = bookingOwner;
        this.room = room;
        this.building = building;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
        this.participants = participants;
    }

    /** Empty constructor.
     *
     */
    public Booking() {
        super();
    }

    /** Get the id of the booking.
     *
     * @return id of the booking.
     */
    public Long getId() {
        return id;
    }

    /** Get the booking owner.
     *
     * @return the booking owner.
     */
    public String getBookingOwner() {
        return bookingOwner;
    }

    /** Get the room of the booking.
     *
     * @return the room of the booking.
     */
    public int getRoom() {
        return room;
    }

    /** Get the start time of the booking.
     *
     * @return the start time of the booking.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /** Get the end time of the booking.
     *
     * @return the end time of the booking.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /** Get the purpose of the booking.
     *
     * @return the purpose of the booking.
     */
    public String getPurpose() {
        return purpose;
    }

    /** Get the date of the booking.
     *
     * @return the date of the booking.
     */
    public LocalDate getDate() {
        return date;
    }

    /** Get the participants of the booking.
     *
     * @return the date of the booking.
     */
    public List<String> getParticipants() {
        return participants;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    /** Set the date of the booking.
     *
     * @param date the new date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /** Set the booking owner of the booking.
     *
     * @param bookingOwner the new booking owner.
     */
    public void setBookingOwner(String bookingOwner) {
        this.bookingOwner = bookingOwner;
    }

    /** Set the room of the booking.
     *
     * @param room the new room.
     */
    public void setRoom(int room) {
        this.room = room;
    }

    /** Set the start time of the booking.
     *
     * @param startTime the new start time.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /** Set the end time of the booking.
     *
     * @param endTime the new end time.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /** Set the purpose of the booking.
     *
     * @param purpose the new purpose.
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /** Set the participants of the booking.
     *
     * @param participants the new purpose.
     */
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    /** Checks if the object is equal to this booking.
     *
     * @param o the object to compare to.
     * @return true if they are the same, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(bookingOwner, booking.bookingOwner)
            && Objects.equals(room, booking.room) && Objects.equals(date, booking.date)
            && Objects.equals(startTime, booking.startTime)
            && Objects.equals(endTime, booking.endTime) && Objects.equals(purpose, booking.purpose);
    }

    /** Generates a hash for the booking.
     *
     * @return the hash code for the booking.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, bookingOwner, room, date, startTime, endTime, purpose);
    }

    /** A String version of the booking.
     *
     * @return the booking in form of a String.
     */
    @Override
    public String toString() {
        return new StringBuilder().append("Booking{").append("id=").append(id)
            .append(", bookingOwner='").append(bookingOwner).append('\'').append(", room='")
            .append(room).append('\'').append(", date=").append(date).append(", startTime=")
            .append(startTime).append(", endTime=").append(endTime).append(", purpose='")
            .append(purpose).append('\'').append('}').toString();
    }
}

