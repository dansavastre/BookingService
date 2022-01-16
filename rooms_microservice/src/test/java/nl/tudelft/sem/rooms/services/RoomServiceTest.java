package nl.tudelft.sem.rooms.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.rooms.RoomApplication;
import nl.tudelft.sem.rooms.objects.Building;
import nl.tudelft.sem.rooms.objects.Room;
import nl.tudelft.sem.rooms.repositories.RoomRepository;
import nl.tudelft.sem.rooms.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;




@AutoConfigureMockMvc
@SpringBootTest(classes = RoomApplication.class)
public class RoomServiceTest {

    @Mock
    private transient RoomRepository roomRepository;

    @InjectMocks
    private transient RoomService roomService;

    transient Room r0;
    transient Room r1;
    transient Room r2;
    transient Map<String, String> equipmentMap;
    transient Building building1;
    transient Building building2;
    transient String r1Id = "36-11";

    @BeforeEach
    void setup() {
        equipmentMap = new HashMap<>();
        equipmentMap.put("projector", "True");
        equipmentMap.put("smartBoard", "True");
        building1 = new Building(36, LocalTime.of(8, 30),
                LocalTime.of(18, 00), "name1");
        building2 = new Building(24, LocalTime.of(10, 30),
                LocalTime.of(17, 30), "name2");
        r0 = new Room(12, "Europe", 12, equipmentMap, "yes", building1);
        r1 = new Room(11, "Australia", 6, equipmentMap, "no", building1);
        r2 = new Room(11, "Australia", 6, equipmentMap, "yes", building1);
    }

    @Test
    void getAllRooms_test() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(r0);
        rooms.add(r1);
        when(roomRepository.findAll()).thenReturn(rooms);
        assertEquals(rooms, roomService.getAllRooms());
    }

    @Test
    void getRoom_test() {
        when(roomRepository.findById("36-12")).thenReturn(java.util.Optional.ofNullable(r0));
        assertEquals(r0, roomService.getRoom("36-12"));
    }

    @Test
    void addRoom_test() {
        roomService.addRoom(r1);
        verify(roomRepository, times(1)).save(r1);
    }

    @Test
    void updateRoom_test() {
        roomService.updateRoom(r1Id, r2);
        verify(roomRepository, times(1)).save(r2);
    }

    @Test
    void deleteRoom_test() {
        when(roomRepository.getOne(r1Id)).thenReturn(r1);
        Room updated = r1;
        updated.setBuilding(null);
        when(roomRepository.save(any(Room.class))).thenReturn(updated);
        roomService.deleteRoom(r1Id);
        verify(roomRepository, times(1)).deleteById(r1Id);
    }
}
