package nl.tudelft.sem.template;

import nl.tudelft.sem.template.Controllers.HelloController;
import nl.tudelft.sem.template.Controllers.UserController;
import nl.tudelft.sem.template.Objects.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
//		SpringApplication app = new SpringApplication(Application.class);
//		app.run(args);
		SpringApplication.run(Application.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Scanner in = new Scanner(System.in);
//
//		UserController userController = new UserController();
//
//		System.out.println("Enter username:");
//		String name = in.next();
//		System.out.println("Enter password:");
//		String password = in.next();
//		System.out.println("Enter first name and last name");
//		String fName = in.next();
//		String lName = in.next();
//		System.out.println("Enter designation");
//		String userType = in.next();
//		//userController.addUser(name, password, fName, lName, userType);
//	}

}
