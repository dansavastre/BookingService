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
    
    @Autowired
    private final UserRepository userRepository;

    private List<User> users = new ArrayList<>(Arrays.asList(new User("ltwubben", "password", "Luuk", "Wubben", "employee"),
            new User("andy", "I_<3_JavaDoc_xoxoxo123", "Andy", "Zaidman", "god")));

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return users;
    }

    public User getUser(String id){
        return users.stream().filter(x -> x.getId().equals(id)).findFirst().get();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(String id, User user) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId().equals(id)){
                users.set(i, user);
                return;
            }
        }
    }

    public void deleteUser(String id) {
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId().equals(id)){
                users.remove(i);
                return;
            }
        }
    }
}
