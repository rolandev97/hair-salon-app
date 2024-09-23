package com.tp.hair_salon_app.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("employe")
@Getter @Setter
public class Employe extends AppUser {

    @OneToMany(mappedBy = "employe")
    private Set<RendezVous> rendezVous;

    @OneToMany(mappedBy = "employe")
    private List<Jour> planning;

}
