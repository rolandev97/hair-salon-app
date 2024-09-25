package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.Jour;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.models.dto.EmployeDto;
import com.tp.hair_salon_app.models.dto.JourDto;
import com.tp.hair_salon_app.models.dto.RendezVousDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public interface IEmployeeService {
    ResponseEntity<?> createEmployee(Employe employe);
    ResponseEntity<?> updateEmployee(Employe employe, Long id);
    ResponseEntity<?> prendreRendezVois(RendezVousDto rendezVousDto, List<Long> serviceIds);
    ResponseEntity<?> deplacerRendezVous(Long rendezVousId, String nouvelleDate, String nouvelleHeure);
    ResponseEntity<?> annulerRendezVous(Long rendezVousId);
    List<RendezVousDto> getRendezVousList();
    ResponseEntity<?> createFacture(Long rendezVousId, Long employeeId);
    ResponseEntity<?> modifierHoraire(Long employeeId, Jour jour);
    void genererCalendrierAnnuel(LocalDate startDate);
    ResponseEntity<?> ajouterJourConge(Long employeId, Date date, boolean demiJour);
    void supprimerJourConge(Long jourId);
    EmployeDto employeDetailParId(Long employeId);
    EmployeDto employeDetailParEmail(String email);
    List<RendezVousDto> getEmployeeRendezVous(Long employeId);
    List<JourDto> getJourEmploye(Long employeId);
    List<EmployeDto> getAllEmploye();
 }
