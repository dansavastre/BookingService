package nl.tudelft.sem.users.repositories;

import nl.tudelft.sem.users.objects.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(String type);
}