package nl.tudelft.sem.rooms.repositories;

import java.util.List;
import nl.tudelft.sem.rooms.objects.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    List<Building> findAllByNameContains(String s);
}
