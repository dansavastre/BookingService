package nl.tudelft.sem.rooms.repositories;

import java.util.List;
import nl.tudelft.sem.rooms.objects.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {

    List<Room> findAllByNameContains(String s);
}
