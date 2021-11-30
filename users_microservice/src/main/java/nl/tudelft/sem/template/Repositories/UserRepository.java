package nl.tudelft.sem.template.Repositories;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByFirstNameContains(String s);
}
