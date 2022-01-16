package nl.tudelft.sem.users.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @GetMapping("/employee/authorize")
    @ResponseBody
    public boolean authorizeEmployee() {
        return true;
    }

    @GetMapping("/employee/authorizeWithUsername")
    @ResponseBody
    public boolean authorizeEmployeeWithUsername(@RequestHeader("Username") String username) {
        return true;
    }

    @GetMapping("/secretary/authorize")
    @ResponseBody
    public boolean authorizeSecretary() {
        return true;
    }

    @GetMapping("/secretary/authorizeWithUsername")
    @ResponseBody
    public boolean authorizeSecretaryWithUsername(@RequestHeader("Username") String username) {
        return true;
    }

    @GetMapping("/admin/authorize")
    @ResponseBody
    public boolean authorizeAdmin() {
        return true;
    }

    @GetMapping("/admin/authorizeWithUsername")
    @ResponseBody
    public boolean authorizeAdminWithUsername(@RequestHeader("Username") String username) {
        return true;
    }
}
