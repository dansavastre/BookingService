package nl.tudelft.sem.template;

import nl.tudelft.sem.template.objects.User;
import nl.tudelft.sem.template.security.NoEncoding;
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
    NoEncoding passwordEncoder() {
        return new NoEncoding();
    }

    private transient String employee = "Employee";

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            // password: 1234
            userService.addUser(new User("ltwubben", "MTIzNA==",
                    "Luuk", "Wubben", employee));
            // password: 5678
            userService.addUser(new User("vmadhu", "NTY3OA==",
                    "Veena", "Madhu", employee));
            // password: 9123
            userService.addUser(new User("keshavnair", "OTEyMw==",
                    "Keshav", "Nair", employee));
            // password: 4567
            userService.addUser(new User("npietnoczko", "NDU2Nw==",
                    "Natalia", "Pietnoczko", employee));
            // password: 8912
            userService.addUser(new User("bserbanescu", "ODkxMg==",
                    "Bianca", "Serbanescu", employee));
            // password: 3456
            userService.addUser(new User("dsavastre", "MzQ1Ng==",
                    "Dan", "Savastre", employee));
        };
    }
}
