package nl.tudelft.sem.users.repositories;

import nl.tudelft.sem.users.objects.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

}
