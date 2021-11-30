package nl.tudelft.sem.template;

import nl.tudelft.sem.template.Controllers.UserController;
import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
@ComponentScan("nl.tudelft.sem.template.Services")
@ComponentScan("nl.tudelft.sem.template.repositories")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
