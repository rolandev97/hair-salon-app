package com.tp.hair_salon_app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.Jour;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.models.dto.EmployeDto;
import com.tp.hair_salon_app.models.dto.RendezVousDto;
import com.tp.hair_salon_app.services.servicesImpl.EmployeeServiceImpl;
import com.tp.hair_salon_app.services.servicesImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "/employe/")
public class EmployeController {
    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeController(EmployeeServiceImpl service) {
        this.employeeService = service;
    }

    @PostMapping(value = "/employe/create")
    public ResponseEntity<?> createUser(@RequestBody Employe employe) {
        return this.employeeService.createEmployee(employe);
    }

    @PutMapping(value = "/employe/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Employe employe, @PathVariable("id") Long userId) {
        return this.employeeService.updateEmployee(employe, userId);
    }

    @PostMapping("/employe/prendre-rendez-vous")
    public ResponseEntity<?> prendreUnRendezVous(@RequestBody Map<String, Object> requestBody) {
        // Initialiser ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        RendezVousDto rendezVous = objectMapper.convertValue(requestBody.get("rendezVous"), RendezVousDto.class);
        List<Long> servicesId = objectMapper.convertValue(requestBody.get("servicesId"), new TypeReference<List<Long>>() {
        });

        return this.employeeService.prendreRendezVois(rendezVous, servicesId);
    }

    @GetMapping("/emloye/deplacer-rendez-vous/{id}/{newDate}/{newHour}")
    public ResponseEntity<?> deplacerRendezVous(@PathVariable("id") Long rendezVousId, @PathVariable("newDate") String nouvelleDate, @PathVariable("newHour") String nouvelleHeure) {
        return this.employeeService.deplacerRendezVous(rendezVousId, nouvelleDate, nouvelleHeure);
    }

    @GetMapping("/employe/deplacer-rendez-vous/{id}")
    public ResponseEntity<?> annulerRendezVous(@PathVariable("id") Long rendezVousId) {
        return this.employeeService.annulerRendezVous(rendezVousId);
    }

    @GetMapping("/employe/tous-les-rendez-vous")
    public List<RendezVousDto> tousLesRendezVous() {
        return this.employeeService.getRendezVousList();
    }

    @GetMapping("/employe/create-facture/{rendezVousId}/{employeId}")
    public ResponseEntity<?> createFacture(@PathVariable Long rendezVousId, @PathVariable Long employeId) {
        return employeeService.createFacture(rendezVousId, employeId);
    }

    @GetMapping("/employe/generer")
    public ResponseEntity<?> genererCalendrierAnnuel() {
        employeeService.genererCalendrierAnnuel(LocalDate.now());
        return ResponseEntity.ok("Calendrier généré pour l'année à venir");
    }

    @PostMapping("/employe/ajouter-jour-ferie")
    public ResponseEntity<?> ajouterJourFerie(@RequestParam LocalDate date) {
        employeeService.ajouterJourFerie(date);
        return ResponseEntity.ok("Jour férié ajouté");
    }

    @PostMapping("/employe/ajouter-jour-conge")
    public ResponseEntity<?> ajouterJourConge(@RequestParam Long employeId, @RequestParam Date date, @RequestParam boolean demiJour) {
        return employeeService.ajouterJourConge(employeId, date, demiJour);
    }

    @DeleteMapping("/employe/supprimer-jour-ferie/{jourId}")
    public ResponseEntity<?> supprimerJourFirie(@PathVariable Long jourId) {
        employeeService.supprimerJourFerie(jourId);
        return ResponseEntity.ok("Jour ferié supprimé");
    }

    @DeleteMapping("/employe/supprimer-jour-conge/{jourId}")
    public ResponseEntity<?> supprimerJourConge(@PathVariable Long jourId) {
        employeeService.supprimerJourConge(jourId);
        return ResponseEntity.ok("Jour congé supprimé");
    }

    @PutMapping("/employe/modifier-horaire-employe/{employeId}")
    public ResponseEntity<?> modifierHoraireEmploye(@PathVariable Long employeId, @RequestBody Jour jour) {
        return employeeService.modifierHoraire(employeId, jour);
    }

    @GetMapping("/employe/{employeId}")
    public EmployeDto getEmployeById(@PathVariable Long employeId) {
        return employeeService.employeDetailParId(employeId);
    }

    @GetMapping("/employe/{email}")
    public EmployeDto getEmployeByEmail(@PathVariable String email) {
        return employeeService.employeDetailParEmail(email);
    }


}