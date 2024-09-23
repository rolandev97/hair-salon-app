package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Set;

@Entity(name = "service")
@Getter @Setter
public class AppService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double montant;
    private Time duree;

    @OneToMany(mappedBy = "services")
    private Set<ServiceRendezVous> serviceRendezVous;

}
