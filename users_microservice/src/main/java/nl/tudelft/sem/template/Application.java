package nl.tudelft.sem.template;

import nl.tudelft.sem.template.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan("nl.tudelft.sem.template.services")
@ComponentScan("nl.tudelft.sem.template.repositories")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
