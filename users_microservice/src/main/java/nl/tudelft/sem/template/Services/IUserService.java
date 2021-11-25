package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUserService {

    List<User> findAll();
}
