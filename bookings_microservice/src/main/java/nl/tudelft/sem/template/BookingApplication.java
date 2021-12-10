package nl.tudelft.sem.template;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.services.BookingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    private transient String name1 = "npietnoczko";
    private transient String name2 = "bserbanescu";

    @Bean
    CommandLineRunner run(BookingService bookingService) {
        return args -> {
            bookingService.addBooking(new Booking(name1, 0, 36,
                    LocalDate.of(2021, 12, 3),
                    LocalTime.of(12, 0),
                    LocalTime.of(13, 0), "Studying", List.of(name2)));
            bookingService.addBooking(new Booking(name2, 0, 36,
                    LocalDate.of(2021, 12, 4),
                    LocalTime.of(11, 0),
                    LocalTime.of(12, 30), "Studying", List.of(name1)));
            bookingService.addBooking(new Booking(name2, 0, 36,
                    LocalDate.of(2021, 12, 16),
                    LocalTime.of(9, 0),
                    LocalTime.of(11, 0), "Studying", List.of(name1, "dsavastre")));
            bookingService.addBooking(new Booking(name2, 1, 36,
                    LocalDate.of(2021, 10, 4),
                    LocalTime.of(11, 0),
                    LocalTime.of(12, 30), "Studying in the past", List.of(name1)));
            bookingService.addBooking(new Booking(name2, 1, 24,
                    LocalDate.of(2021, 10, 4),
                    LocalTime.of(11, 0),
                    LocalTime.of(12, 30), "Studying in the past", List.of(name1)));
            bookingService.addBooking(new Booking(name2, 1, 24,
                    LocalDate.of(2022, 12, 4),
                    LocalTime.of(11, 01),
                    LocalTime.of(12, 30), "Studying in the past", List.of(name1)));
            bookingService.addBooking(new Booking(name2, 1, 24,
                    LocalDate.of(2021, 12, 9),
                    LocalTime.of(11, 01),
                    LocalTime.of(12, 30), "Studying in the past", List.of(name1)));
        };


    }
}
