package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppService;
import com.tp.hair_salon_app.models.RendezVous;
import org.springframework.http.ResponseEntity;

public interface IClientService {
    ResponseEntity<?> makeAnAppointment(RendezVous rendezVous, AppService service);
    ResponseEntity<?> updateAnAppointment(Long id, RendezVous rendezVous);
    ResponseEntity<?> deleteAnAppointment(Long id);
}
