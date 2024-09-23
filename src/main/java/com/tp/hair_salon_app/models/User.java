package com.tp.hair_salon_app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    @Id
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String login;
    private String motDePasse;
    private String numeroTelephone;
    private String role;

}

