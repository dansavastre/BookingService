package nl.tudelft.sem.template;

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

    private transient String employee = "Employee";

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.addUser(new User("ltwubben", "", "Luuk", "Wubben", employee));
            userService.addUser(new User("vmadhu", "", "Veena", "Madhu", employee));
            userService.addUser(new User("keshavnair", "", "Keshav", "Nair", employee));
            userService.addUser(new User("npietnoczko", "", "Natalia", "Pietnoczko", employee));
            userService.addUser(new User("bserbanescu", "", "Bianca", "Serbanescu", employee));
            userService.addUser(new User("dsavastre", "", "Dan", "Savastre", employee));
        };
    }
}
