package nl.tudelft.sem.template.Repositories;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

}
