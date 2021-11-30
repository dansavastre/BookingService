package nl.tudelft.sem.template.Objects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Booking {

    private Long id;
    private String bookingOwner;
    private String room;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String purpose;

    public Booking(Long id, List<String> participants, String bookingOwner, String room, LocalDate date, LocalTime startTime, LocalTime endTime, String purpose) {
        this.id = id;
        this.bookingOwner = bookingOwner;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.purpose = purpose;
    }

    public Booking(){
        super();
        this.id = null;
        this.bookingOwner = "NOTINSTANTIATED";
        this.date = null;
        this.startTime = null;
        this.endTime = null;
        this.purpose = "NOTINSTANTIATED";
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

