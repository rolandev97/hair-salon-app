package com.tp.hair_salon_app.controller;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.services.servicesImpl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "/auth/")
public class AuthController {

    private AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService){
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser (@RequestBody AppUser appUser){
        return this.authService.authenticateUser(appUser);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser (@RequestBody AppClient appClient){
        return this.authService.registerUser(appClient);
    }
}
