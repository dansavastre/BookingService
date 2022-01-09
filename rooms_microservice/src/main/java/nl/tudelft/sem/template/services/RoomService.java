package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.repositories.BuildingRepository;
import nl.tudelft.sem.template.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private transient RoomRepository roomRepository;

    @Autowired
    private transient BuildingRepository buildingRepository;

    /**
     * Gets all rooms from the database and returns them in a List of Room.
     *
     * @return A List of Room Objects
     */
    public List<Room> getAllRooms() {
        List<Room> r = new ArrayList<>();
        roomRepository.findAll().forEach(r::add);
        return r;
    }

    public Room getRoom(String id) {
        return roomRepository.findById(id).get();
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    /**
     * Update a room by checking if IDs match and then requesting a save from database.
     *
     * @param id   ID of the building
     * @param room New updated room entity
     */
    public void updateRoom(String id, Room room) {
        if (Objects.equals(room.getId(), id)) {
            roomRepository.save(room);
        }
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }
}
