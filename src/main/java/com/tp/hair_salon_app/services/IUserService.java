package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.dto.AppUserDto;

public interface IUserService {
    AppUserDto createUser(AppUser appUser);
    AppUserDto updateUser(AppUser appUser, Long userId);
}
