package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.ServiceRendezVous;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

public class RendezVousDto {
    private Long id;
    private Date date;
    private Time heure;
    private Time duree;
    private String nomClient;
    private String numeroTelephoneClient;
    private ClientDto clientDto;
    private EmployeDto employeDto;
    private Set<ServiceRendezVousDto> serviceRendezVousDtos;
}
