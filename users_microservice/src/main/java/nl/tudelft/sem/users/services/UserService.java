package nl.tudelft.sem.users.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.objects.User;
import nl.tudelft.sem.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private transient UserRepository userRepository;
    @Autowired
    private transient RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findById(username);
        if (u.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        User user = u.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getType()));
        }
        return new org.springframework.security.core.userdetails
                .User(user.getId(), user.getPassword(), authorities);
    }

    /**
     * Returns a list of users which contains all users on the database.

     * @return List of type User
     */
    public List<User> getAllUsers() {
        List<User> u = new ArrayList<>();
        userRepository.findAll().forEach(u::add);
        return u;
    }

    public User getUser(String id) {
        return userRepository.findById(id).get();
    }

    /** Encrypts the password and saves the user.
     *
     * @param user     User to be saved in the db, has raw password
     */
    public void addUser(User user) {
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        for (Role r : user.getRoles()) {
            roleService.addRole(r);
        }
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
