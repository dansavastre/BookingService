package nl.tudelft.sem.template.Repositories;

import nl.tudelft.sem.template.Objects.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    List<Building> findAllByNameContains(String s);
}
