package com.tp.hair_salon_app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.RendezVous;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.models.dto.ClientDto;
import com.tp.hair_salon_app.models.dto.EmployeDto;
import com.tp.hair_salon_app.models.dto.RendezVousDto;
import com.tp.hair_salon_app.services.servicesImpl.ClientServiceImpl;
import com.tp.hair_salon_app.services.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ClientController {

    private ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService1){
        this.clientService = clientService1;
    }

    @PostMapping(value = "/client/create")
    public ClientDto createUser(@RequestBody AppClient client){
        return this.clientService.create(client);
    }

    @PostMapping("/client/prendre-rendez-vous")
    public ResponseEntity<?> prendreUnRendezVous(@RequestBody Map<String, Object> requestBody){
        // Initialiser ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        RendezVousDto rendezVous = objectMapper.convertValue(requestBody.get("rendezVous"), RendezVousDto.class);
        List<Long> servicesId = objectMapper.convertValue(requestBody.get("servicesId"), new TypeReference<List<Long>>() {});

        return this.clientService.makeAnAppointment(rendezVous, servicesId);
    }

    @GetMapping("/client/deplacer-rendez-vous/{id}/{newDate}/{newHour}")
    public ResponseEntity<?> deplacerRendezVous(@PathVariable("id") Long rendezVousId, @PathVariable("newDate") String nouvelleDate, @PathVariable("newHour") String nouvelleHeure){
        return this.clientService.deplacerRendezVous(rendezVousId, nouvelleDate, nouvelleHeure);
    }

    @GetMapping("/client/deplacer-rendez-vous/{id}")
    public ResponseEntity<?> annulerRendezVous(@PathVariable("id") Long rendezVousId){
        return this.clientService.annulerRendezVous(rendezVousId);
    }

    @GetMapping("/client/tous-les-rendez-vous")
    public List<RendezVousDto> tousLesRendezVous(){
        return this.clientService.getRendezVousList();
    }

    @GetMapping("/client/{clientId}")
    public ClientDto getEmployeById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId);
    }

    @GetMapping("/client/{email}")
    public ClientDto getClientByEmail(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }
}
