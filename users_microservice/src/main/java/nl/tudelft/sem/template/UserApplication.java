package nl.tudelft.sem.template;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.security.NoEncoding;
import nl.tudelft.sem.template.services.RoleService;
import nl.tudelft.sem.template.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    NoEncoding passwordEncoder() {
        return new NoEncoding();
    }

    @Bean
    CommandLineRunner run2(RoleService roleService) {
        return args -> {
            roleService.addRole(new Role("employee"));
            roleService.addRole(new Role("secretary"));
            roleService.addRole(new Role("admin"));

        };
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            List<Role> roles = new ArrayList<Role>(Arrays.asList(new Role("employee")));
            User u1 = new User("ltwubben", "MTIzNA==", "Luuk", "Wubben");
            u1.setRoles(roles);
            User u2 = new User("vmadhu", "NTY3OA==", "Veena", "Madhu");
            u2.setRoles(roles);
            User u3 = new User("keshavnair", "OTEyMw==", "Keshav", "Nair");
            u3.setRoles(roles);
            User u4 = new User("npietnoczko", "NDU2Nw==", "Natalia", "Pietnoczko");
            u4.setRoles(roles);
            User u5 = new User("bserbanescu", "ODkxMg==", "Bianca", "Serbanescu");
            u5.setRoles(roles);
            User u6 = new User("dsavastre", "MzQ1Ng==", "Dan", "Savastre");
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
