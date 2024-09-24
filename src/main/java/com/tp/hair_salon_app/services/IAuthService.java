package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppUser;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> authenticateUser(AppUser appUser);
    ResponseEntity<?> registerUser(AppClient appClient);
}
