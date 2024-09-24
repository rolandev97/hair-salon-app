package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.RendezVous;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class ClientDto extends AppUserDto {
    private Long id;
    private Set<RendezVousDto> rendezVousDtos;
}
