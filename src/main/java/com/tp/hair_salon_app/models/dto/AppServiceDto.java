package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.ServiceRendezVous;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Set;

@Getter @Setter
public class AppServiceDto {
    private Long id;
    private String nom;
    private double montant;
    private Time duree;
    private String devise;

    private Set<ServiceRendezVousDto> serviceRendezVousDtos;

}
