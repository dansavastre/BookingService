package nl.tudelft.sem.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            List<Role> roles = new ArrayList<Role>(Arrays.asList(new Role("employee")));
            User u1 = new User("ltwubben", "", "Luuk", "Wubben");
            u1.setRoles(roles);
            User u2 = new User("vmadhu", "", "Veena", "Madhu");
            u2.setRoles(roles);
            User u3 = new User("keshavnair", "", "Keshav", "Nair");
            u3.setRoles(roles);
            User u4 = new User("npietnoczko", "", "Natalia", "Pietnoczko");
            u4.setRoles(roles);
            User u5 = new User("bserbanescu", "", "Bianca", "Serbanescu");
            u5.setRoles(roles);
            User u6 = new User("dsavastre", "", "Dan", "Savastre");
            u6.setRoles(roles);
            userService.addUser(u1);
            userService.addUser(u2);
            userService.addUser(u3);
            userService.addUser(u4);
            userService.addUser(u5);
            userService.addUser(u6);
        };
    }
}
