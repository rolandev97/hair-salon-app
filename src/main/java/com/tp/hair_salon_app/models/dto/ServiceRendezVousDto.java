package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.RendezVous;
import com.tp.hair_salon_app.models.Service;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ServiceRendezVousDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RendezVous rendezVous;
    @ManyToOne
    private Service services;
}
