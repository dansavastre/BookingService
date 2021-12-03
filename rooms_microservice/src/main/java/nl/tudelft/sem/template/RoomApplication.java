package nl.tudelft.sem.template;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.BuildingService;
import nl.tudelft.sem.template.services.RoomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomApplication.class, args);
    }

    @Bean
    CommandLineRunner run1(RoomService roomService) {
        return args -> {
            Map<String, Boolean> equipmentMap = new HashMap<>();
            roomService.addRoom(new Room(0, "Europe", 12, equipmentMap, "yes", 36));
            roomService.addRoom(new Room(1, "Australia", 6, equipmentMap, "yes", 36));
            roomService.addRoom(new Room(2, "Africa", 8, equipmentMap, "maintenance", 36));
            roomService.addRoom(new Room(1, "Steve Jobs", 12, equipmentMap, "yes", 24));
        };
    }

    @Bean
    CommandLineRunner run2(BuildingService buildingService) {
        return args -> {
            buildingService.addBuilding(new Building(36, LocalTime.of(8, 0),
                    LocalTime.of(17, 0), "b36"));
            buildingService.addBuilding(new Building(24,
                    LocalTime.of(9, 0), LocalTime.of(18, 0), "b24"));
        };
    }
}
