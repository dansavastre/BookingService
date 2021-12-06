package nl.tudelft.sem.template.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private transient RoomRepository roomRepository;

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

    public Room getRoom(int id) {
        return roomRepository.findById(id).get();
    }

    public void addRoom(Room room) {
        roomRepository.save(room);
    }

    public void updateRoom(int id, Room room) {
        roomRepository.deleteById(id);
        roomRepository.save(room);
    }

    public void deleteRoom(int id) {
        roomRepository.deleteById(id);
    }
}
