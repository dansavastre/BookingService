package nl.tudelft.sem.rooms.controllers;

import java.util.List;
import nl.tudelft.sem.rooms.objects.Building;
import nl.tudelft.sem.rooms.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BuildingController {

    @Autowired
    private transient BuildingService buildingService;
    @Autowired
    private transient Authorization auth;

    private final transient String authorization = "Authorization";

    @GetMapping("/sayHiToBuilding")
    @ResponseBody
    public String sayHi() {
        return "Hello from the building!";
    }

    @GetMapping("/buildings")
    @ResponseBody
    public List<Building> getAllBuildings(@RequestHeader(authorization) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return buildingService.getAllBuildings();
    }

    @GetMapping("/getBuilding/{id}")
    @ResponseBody
    public Building getBuilding(@PathVariable("id") int id,
                                @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return buildingService.getBuilding(id);
    }

    @PostMapping("/buildings")
    @ResponseBody
    public void addBuilding(@RequestBody Building building,
                            @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        buildingService.addBuilding(building);
    }

    /**
     * Update an existing building.
     *
     * @param building The building to be updated with new info
     * @param id       The ID of the building to be updated
     * @param token    Security token for authentication
     */
    @PutMapping("/buildings/{id}")
    @ResponseBody
    public void updateBuilding(@RequestBody Building building, @PathVariable("id") int id,
                               @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        Building toUpdate = buildingService.getBuilding(id);
        if (toUpdate.getId() == building.getId()) {
            buildingService.updateBuilding(id, building);
        }
    }

    @DeleteMapping("/buildings/{id}")
    @ResponseBody
    public void deleteBuilding(@PathVariable("id") int id,
                               @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        buildingService.deleteBuilding(id);
    }

}

