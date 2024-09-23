package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    @NotBlank(message = "Le nom est obligatoire.")
    private String nom;

    @Column(name = "prenom")
    @NotBlank(message = "Le prénom est obligatoire.")
    private String prenom;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Adresse email invalide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    @NotBlank(message = "Le mot de passe est obligatoire.")
    private String motDePasse;

    @Column(name = "numero_telephone")
    private String numeroTelephone;

    @Enumerated(EnumType.STRING)
    private Role role; // "CLIENT", "EMPLOYE", "ADMIN"
}

