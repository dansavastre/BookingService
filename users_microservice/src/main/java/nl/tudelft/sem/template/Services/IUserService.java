package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    void save(User user);
}
