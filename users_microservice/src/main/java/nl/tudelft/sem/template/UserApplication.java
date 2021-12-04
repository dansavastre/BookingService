package nl.tudelft.sem.template;

import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private transient String employee = "Employee";

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.addUser(new User("ltwubben", "1234", "Luuk", "Wubben", employee));
            userService.addUser(new User("vmadhu", "5678", "Veena", "Madhu", employee));
            userService.addUser(new User("keshavnair", "9123", "Keshav", "Nair", employee));
            userService.addUser(new User("npietnoczko", "4567", "Natalia", "Pietnoczko", employee));
            userService.addUser(new User("bserbanescu", "8912", "Bianca", "Serbanescu", employee));
            userService.addUser(new User("dsavastre", "3456", "Dan", "Savastre", employee));
        };
    }
}
