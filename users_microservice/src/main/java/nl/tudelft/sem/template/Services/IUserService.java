package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableAutoConfiguration
public interface IUserService {

    List<User> findAll();
    void save(User user);
}
