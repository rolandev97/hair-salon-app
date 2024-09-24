package com.tp.hair_salon_app.controller;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.services.servicesImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "/user/")
public class UserController {
    private UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl service){
        this.userService = service;
    }

    @PostMapping(value = "/create-user")
    public AppUserDto createUser(@RequestBody AppUser appUser){
        return this.userService.createUser(appUser);
    }

    @PatchMapping(value = "/update-user/{id}")
    public AppUserDto updateUser(@RequestBody AppUser appUser, @PathVariable("id") Long userId){
        return  this.userService.updateUser(appUser, userId);
    }

    @GetMapping(value = "/update-user-password/{id}/{oldPass}/{newPass}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @PathVariable String oldPass, @PathVariable String newPass){
        return  this.userService.changePassword(id, oldPass, newPass);
    }
}
