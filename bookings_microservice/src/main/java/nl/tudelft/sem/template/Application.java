package nl.tudelft.sem.template;


import nl.tudelft.sem.template.controllers.BookingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = BookingController.class)
@ComponentScan("nl.tudelft.sem.template.services")
@ComponentScan("nl.tudelft.sem.template.repositories")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
