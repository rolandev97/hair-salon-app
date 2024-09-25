package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppService;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.RendezVous;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.models.dto.ClientDto;
import com.tp.hair_salon_app.models.dto.RendezVousDto;
import org.springframework.http.ResponseEntity;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IClientService {
    ResponseEntity<?> makeAnAppointment(RendezVousDto rendezVousDto, List<Long> serviceIds);
    ResponseEntity<?> deplacerRendezVous(Long rendezVousId, String nouvelleDate, String nouvelleHeure);
    ResponseEntity<?> annulerRendezVous(Long rendezVousId);
    List<RendezVousDto> getRendezVousList();
    ClientDto create(AppClient client);
    ClientDto getClientById(Long clientId);
    ClientDto getClientByEmail(String email);
    RendezVousDto getClientRendezVous(Long clientId);
    List<RendezVousDto> getAllClientRendezVous(Long clientId);
    List<ClientDto> getAllClients();
}
