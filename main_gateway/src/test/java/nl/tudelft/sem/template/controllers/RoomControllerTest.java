package nl.tudelft.sem.template.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.objects.Room;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class RoomControllerTest {

    @Mock
    private transient RestTemplate restTemplate;

    @InjectMocks
    private transient RoomController roomController;

    private transient Room room1;
    private transient Room room2;
    private transient List<Room> rooms;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        rooms = new ArrayList<>();
        Map<String, String> equipmentMap = new HashMap<>();
        equipmentMap.put("wifi", "True");
        equipmentMap.put("projector", "True");
        equipmentMap.put("smart board", "True");
        room1 = new Room(1, "Steve Jobs Room", 4, equipmentMap, "available", 36);
        room2 = new Room(1, "Marie Currie Room", 8, equipmentMap, "available", 36);
        rooms.add(room1);
        rooms.add(room2);
    }

    @Test
    void getRooms_test() {
        String uri = "http://localhost:8082/rooms";
        when(restTemplate.getForObject(uri, List.class))
                .thenReturn(rooms);

        Assertions.assertThat(roomController.getRooms()).isEqualTo(rooms);
        verify(restTemplate, times(1)).getForObject(uri, List.class);
    }

    @Test
    void getRoom_test() {
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(1));
        when(restTemplate.getForObject(uri, Room.class))
                .thenReturn(room1);
        Assertions.assertThat(roomController.getRoom(1)).isEqualTo(room1);
        verify(restTemplate, times(1)).getForObject(uri, Room.class);
    }

    @Test
    void postRoom_test() {
        String uri = "http://localhost:8082/rooms";
        Assertions.assertThat(roomController.postRoom(room1)).isTrue();
        verify(restTemplate, times(1)).postForObject(uri, room1, void.class);
    }

    @Test
    void updateRoom_test() {
        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(1));
        Assertions.assertThat(roomController.updateRoom(room2, 1)).isTrue();
        verify(restTemplate, times(1)).put(uri, room2);
    }

    @Test
    void deleteRoom_test() {
        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(1));
        Assertions.assertThat(roomController.deleteRoom(1)).isTrue();
        verify(restTemplate, times(1)).delete(uri);
    }
}