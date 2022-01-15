package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.services.BuildingService;
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

}

