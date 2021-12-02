package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
<<<<<<< HEAD
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        List<User> u = new ArrayList<>();
        userRepository.findAll().forEach(u::add);
        return u;
    }

    public User getUser(String id){
        return userRepository.findById(id).get();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(String id, User user) {
        userRepository.deleteById(id);
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
=======
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
>>>>>>> eb7baabddc49031655f42754d2fc7d6924b01132
    }
}
