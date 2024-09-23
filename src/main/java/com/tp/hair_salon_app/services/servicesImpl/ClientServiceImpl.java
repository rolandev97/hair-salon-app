package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.exception.ResourceNotFoundException;
import com.tp.hair_salon_app.models.*;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.repositories.ClientRepository;
import com.tp.hair_salon_app.repositories.RendezVousRepository;
import com.tp.hair_salon_app.repositories.ServiceRendezVousRepository;
import com.tp.hair_salon_app.repositories.UserRepository;
import com.tp.hair_salon_app.services.IClientService;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {
    private ClientRepository clientRepository;
    private RendezVousRepository rendezVousRepository;
    private ServiceRendezVousRepository serviceRendezVousRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RendezVousRepository rendezVousRepository, ServiceRendezVousRepository serviceRendezVousRepository,ModelMapper modelMapper){
        this.clientRepository = clientRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.serviceRendezVousRepository = serviceRendezVousRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> makeAnAppointment(RendezVous rendezVous, AppService service) {
        //Enregistrement des infos du rendez-vous dans la BD
        RendezVous rendezVousResp = this.rendezVousRepository.save(rendezVous);

        //Enregistrement des services et du rendezvous
        ServiceRendezVous serviceRendezVous = new ServiceRendezVous();
        serviceRendezVous.setRendezVous(rendezVousResp);
        serviceRendezVous.setServices(service);

        return ResponseEntity.ok().body("le rendez vous à été planifié avec succès!");
    }

    @Override
    public ResponseEntity<?> updateAnAppointment(Long id, RendezVous rendezVous) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteAnAppointment(Long id) {
        return null;
    }
}
