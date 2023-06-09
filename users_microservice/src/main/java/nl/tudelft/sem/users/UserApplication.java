package nl.tudelft.sem.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tudelft.sem.users.objects.Group;
import nl.tudelft.sem.users.objects.Role;
import nl.tudelft.sem.users.objects.User;
import nl.tudelft.sem.users.security.NoEncoding;
import nl.tudelft.sem.users.services.GroupService;
import nl.tudelft.sem.users.services.RoleService;
import nl.tudelft.sem.users.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserApplication {

    private final transient String employee = "employee";
    private final transient String secretary = "secretary";
    private final transient String admin = "admin";

    private transient List<User> groupMembers;

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
            roleService.addRole(new Role(employee));
            roleService.addRole(new Role(secretary));
            roleService.addRole(new Role(admin));

        };
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            List<Role> rolesVeena = new ArrayList<>(Arrays.asList(new Role(employee),
                    new Role(secretary)));
            List<Role> rolesKeshav = new ArrayList<>(Arrays.asList(new Role(employee),
                    new Role(secretary), new Role(admin)));

            User u1 = new User("ltwubben", "MTIzNA==", "Luuk", "Wubben");
            u1.addRole(new Role(employee));

            User u2 = new User("vmadhu", "NTY3OA==", "Veena", "Madhu");
            for (Role r : rolesVeena) {
                u2.addRole(r);
            }

            User u3 = new User("keshavnair", "OTEyMw==", "Keshav", "Nair");
            for (Role r : rolesKeshav) {
                u3.addRole(r);
            }

            User u4 = new User("npietnoczko", "NDU2Nw==", "Natalia", "Pietnoczko");
            u4.addRole(new Role(employee));

            User u5 = new User("bserbanescu", "ODkxMg==", "Bianca", "Serbanescu");
            u5.addRole(new Role(employee));

            User u6 = new User("dsavastre", "MzQ1Ng==", "Dan", "Savastre");
            u6.addRole(new Role(employee));

            userService.addUser(u1);
            userService.addUser(u2);
            userService.addUser(u3);
            userService.addUser(u4);
            userService.addUser(u5);
            userService.addUser(u6);

            groupMembers = new ArrayList<>();
            groupMembers.add(u1);
            groupMembers.add(u2);
            groupMembers.add(u3);
            groupMembers.add(u4);
            groupMembers.add(u5);
            groupMembers.add(u6);
        };
    }

    @Bean
    CommandLineRunner run3(GroupService groupService) {
        return args -> {
            Group group = new Group("vmadhu",
                  "M.R.B.S. Research Group",
                  groupMembers);
            groupService.addGroup(group);
        };
    }
}
