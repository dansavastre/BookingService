package nl.tudelft.sem.template.Repositories;

import nl.tudelft.sem.template.Objects.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByNameContains(String s);
}
