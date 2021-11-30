package nl.tudelft.sem.template;

import nl.tudelft.sem.template.Controllers.BookingsController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = BookingsController.class)
@ComponentScan("nl.tudelft.sem.template.repositories")
@ComponentScan("nl.tudelft.sem.template.Services")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
