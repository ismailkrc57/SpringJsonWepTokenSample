package com.karaca.jwt.api.controller;

import com.karaca.jwt.business.abstracts.UserService;
import com.karaca.jwt.entities.Role;
import com.karaca.jwt.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/getusers")
    ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/saveuser")
    ResponseEntity<?> save(@RequestBody User user) {
        return new ResponseEntity(userService.saveUser(user), HttpStatus.OK);
    }

    @PostMapping("/saverole")
    ResponseEntity<?> save(@RequestBody Role role) {
        return new ResponseEntity(userService.saveRole(role), HttpStatus.OK);
    }

    @PostMapping("/addroletouser")
    ResponseEntity<?> addRoleToUser(@RequestBody RelatedFormRole form) {
        userService.addRoleToUser(form.username, form.roleName);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    class RelatedFormRole {
        String username;
        String roleName;
    }

}
