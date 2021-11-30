package nl.tudelft.sem.template.Services;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>(Arrays.asList(new User("ltwubben", "password", "Luuk", "Wubben", "employee"),
            new User("andy", "I_<3_JavaDoc_xoxoxo123", "Andy", "Zaidman", "god")));

    public List<User> getAllUsers(){
        return users;
    }

    public User getUser(String id){
        return users.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }

    public void addUser(User user) {
        users.add(user);
    }
}
