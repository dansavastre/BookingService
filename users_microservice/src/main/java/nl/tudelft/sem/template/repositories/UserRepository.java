package nl.tudelft.sem.template.repositories;

import java.util.List;
import nl.tudelft.sem.template.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
