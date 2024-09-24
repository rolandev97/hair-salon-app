package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    AppUserDto createUser(AppUser appUser);
    AppUserDto updateUser(AppUser appUser, Long userId);
    ResponseEntity<?> changePassword(Long id, String oldPass, String newPass);
    AppUserDto findByEmail(String email);
}
