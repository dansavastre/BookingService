package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableAutoConfiguration
public class UserService {


    @Autowired
    private UserRepository repository;

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> findAll() {

        var users = (List<User>) repository.findAll();
        return users;
    }


    public void save(User user) {

        repository.save(user);
    }
}
