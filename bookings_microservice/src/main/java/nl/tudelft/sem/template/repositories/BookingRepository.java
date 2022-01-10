package nl.tudelft.sem.template.repositories;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    public List<Booking> findBookingByBookingOwnerEquals(String id);

    //b.ID,b.BOOKING_OWNER,b.BUILDING,b.DATE,b.END_TIME,b.PURPOSE,b.ROOM,b.START_TIME
    @Query("SELECT b FROM Booking b "
            + "WHERE (b.date > CURRENT_DATE) OR "
            + "(b.date = CURRENT_DATE AND b.startTime > CURRENT_TIME)")

    public List<Booking> findFutureBookings();

    public List<Booking> findAllByStatusIsNotLike(String status);
}
