package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppUserDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String numeroTelephone;
    private String role;

}

