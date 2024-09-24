package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@DiscriminatorValue("CLIENT")
@Getter @Setter
public class AppClient extends AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "client")
    private Set<RendezVous> rendezVous;
}
