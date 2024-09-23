package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Jour;
import com.tp.hair_salon_app.models.RendezVous;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

public class EmployeDto extends AppUserDto {

    private Set<RendezVous> rendezVous;
    private List<Jour> planning;

}
