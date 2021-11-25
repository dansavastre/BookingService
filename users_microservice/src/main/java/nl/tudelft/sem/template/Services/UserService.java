package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        var users = (List<User>) repository.findAll();
        return users;
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
