package com.tp.hair_salon_app.models.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServiceRendezVousDto {
    private Long id;
    private RendezVousDto rendezVousDto;
    private AppServiceDto servicesDto;
}
