package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
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
    }
}
