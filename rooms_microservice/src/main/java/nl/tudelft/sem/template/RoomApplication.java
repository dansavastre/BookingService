package nl.tudelft.sem.template;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.BuildingService;
import nl.tudelft.sem.template.services.RoomService;
import org.apache.tomcat.jni.Local;
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
            String t = "True";
            Map<String, String> equipmentMap = new HashMap<>();
            equipmentMap.put("Projector", t);
            equipmentMap.put("Whiteboard", t);
            equipmentMap.put("Computer", "False");
            Map<String, String> equipmentMap2 = new HashMap<>();
            equipmentMap2.put("Extra Chairs", t);
            equipmentMap2.put("Debate Stands", t);
            Building building1 = new Building(36, LocalTime.of(8, 30),
                    LocalTime.of(18, 00), "name1");
            Building building2 = new Building(24, LocalTime.of(10, 30),
                    LocalTime.of(17, 30), "name2");

            roomService.addRoom(new Room(0, "Europe", 12, equipmentMap, "yes", building1));
            roomService.addRoom(new Room(1, "Australia", 6, equipmentMap, "yes", building2));
            roomService.addRoom(new Room(2, "Africa", 8, equipmentMap, "maintenance", building1));
            roomService.addRoom(new Room(1, "Steve Jobs", 12, equipmentMap2, "yes", building2));
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
