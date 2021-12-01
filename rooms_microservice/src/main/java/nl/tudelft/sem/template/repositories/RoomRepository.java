package nl.tudelft.sem.template.repositories;

import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByNameContains(String s);
}
