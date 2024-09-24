package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    @NotNull(message = "La date est obligatoire.")
    private Date date;

    @Column(name = "heure")
    @NotNull(message = "L'heure est obligatoire.")
    private Time heure;

    @Column(name = "duree")
    @NotNull(message = "La durée est obligatoire.")
    private Time duree;

    @OneToMany(mappedBy = "rendezVous")
    private Set<ServiceRendezVous> serviceRendezVous;
    @ManyToOne
    private AppClient client;
    @ManyToOne
    private Employe employe;
}
