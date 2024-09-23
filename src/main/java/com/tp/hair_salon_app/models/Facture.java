package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private double montant;
    private boolean estPayee;

    @OneToOne
    @JoinColumn(name = "rendezvous_id")
    private RendezVous rendezVous;
}
