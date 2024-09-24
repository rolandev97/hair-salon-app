package com.tp.hair_salon_app.models.dto;

import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.TypeDeJour;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;


@Getter @Setter
public class JourDto {

    private Long id;
    private Date dateDuJour;
    private Time heureDebut;
    private Time heureFin;
    private String typeDeJour;
    private EmployeDto employeDto;

}
