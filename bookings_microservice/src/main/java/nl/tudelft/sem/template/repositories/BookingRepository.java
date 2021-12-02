package nl.tudelft.sem.template.repositories;

import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
