package nl.tudelft.sem.template.repositories;

import nl.tudelft.sem.template.objects.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(String type);
}
