package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {


    @Override
    public List<User> findAll() {
        UserRepository repository = new UserRepository();
        var users = (List<User>) repository.findAll();
        return users;
    }
}
