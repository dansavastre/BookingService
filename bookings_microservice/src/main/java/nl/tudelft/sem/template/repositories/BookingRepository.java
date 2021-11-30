package nl.tudelft.sem.template.repositories;

import nl.tudelft.sem.template.Objects.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
