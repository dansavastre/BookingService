package nl.tudelft.sem.template.controllers;

import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

public class SecondBuildingController {

    @Autowired
    private transient BuildingService buildingService;
    @Autowired
    private transient Authorization auth;

    private final transient String authorization = "Authorization";

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
