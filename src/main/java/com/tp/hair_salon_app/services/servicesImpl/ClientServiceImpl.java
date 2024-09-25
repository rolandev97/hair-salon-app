package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.exception.BadRequestException;
import com.tp.hair_salon_app.exception.NotFoundException;
import com.tp.hair_salon_app.models.*;
import com.tp.hair_salon_app.models.dto.*;
import com.tp.hair_salon_app.repositories.*;
import com.tp.hair_salon_app.services.IClientService;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements IClientService {
    private ClientRepository clientRepository;
    private RendezVousRepository rendezVousRepository;
    private ServiceRendezVousRepository serviceRendezVousRepository;
    private JourRepository jourRepository;
    private ModelMapper modelMapper;
    private EmployeRepository employeRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, PasswordEncoder passwordEncoder,EmployeRepository employeRepository, JourRepository jourRepository,RendezVousRepository rendezVousRepository, ServiceRendezVousRepository serviceRendezVousRepository,ModelMapper modelMapper){
        this.clientRepository = clientRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.serviceRendezVousRepository = serviceRendezVousRepository;
        this.employeRepository = employeRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jourRepository = jourRepository;
    }

    @Override
    public ResponseEntity<?> makeAnAppointment(RendezVousDto rendezVousDto, List<Long> serviceIds) {
        // Vérifier si l'employé est disponible pour la plage horaire choisie
        Optional<Employe> employeOpt = employeRepository.findById(rendezVousDto.getEmployeDto().getId());
        if (employeOpt.isEmpty()) {
            throw new NotFoundException("User not found ID : "+rendezVousDto.getEmployeDto().getId());
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé non trouvé");
        }
        Employe employe = employeOpt.get();

        if (!isEmployeDisponible(employe, rendezVousDto.getDate(), rendezVousDto.getHeure(), rendezVousDto.getDuree())) {
            throw new BadRequestException("Employe not available at this time! ID : "+rendezVousDto.getEmployeDto().getId());
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'employé n'est pas disponible à cette heure");
        }
        RendezVous rendezVous = new RendezVous();
        rendezVous.setDuree(rendezVousDto.getDuree());
        rendezVous.setDate(rendezVousDto.getDate());
        rendezVous.setHeure(rendezVousDto.getHeure());
        rendezVous.setEmploye(employe);
        rendezVous.setClient(clientRepository.findById(rendezVousDto.getClientDto().getId()).get());
        rendezVous = rendezVousRepository.save(rendezVous);

        // Associer les services demandés au rendez-vous
        for (Long serviceId : serviceIds) {
            ServiceRendezVous serviceRendezVous = new ServiceRendezVous();
            serviceRendezVous.setRendezVous(rendezVous);
            AppService service = new AppService();
            service.setId(serviceId);
            serviceRendezVous.setServices(service);

            serviceRendezVousRepository.save(serviceRendezVous);
        }

        return ResponseEntity.ok().body("Rendez-vous créé avec succès");
    }

    @Override
    public ResponseEntity<?> deplacerRendezVous(Long rendezVousId, String nouvelleDate, String nouvelleHeure) {
        Optional<RendezVous> rendezVousOpt = rendezVousRepository.findById(rendezVousId);
        if (rendezVousOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rendez-vous non trouvé");
        }
        RendezVous rendezVous = rendezVousOpt.get();

        // Vérifier si le déplacement est effectué au moins 24h à l'avance
        if (ChronoUnit.HOURS.between(LocalTime.now(), rendezVous.getHeure().toLocalTime()) >= 24) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez déplacer le rendez-vous au moins 24h à l'avance");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date convertDate = formatter.parse(nouvelleDate);
            rendezVous.setDate(convertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Time newHour = Time.valueOf(nouvelleHeure);
        rendezVous.setHeure(newHour);
        rendezVousRepository.save(rendezVous);

        return ResponseEntity.ok().body("Rendez-vous déplacé avec succès");
    }

    @Override
    public ResponseEntity<?> annulerRendezVous(Long rendezVousId) {
        Optional<RendezVous> rendezVousOpt = rendezVousRepository.findById(rendezVousId);
        if (rendezVousOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rendez-vous non trouvé");
        }
        RendezVous rendezVous = rendezVousOpt.get();

        // Vérifier si l'annulation est effectuée au moins 24h à l'avance
        if (ChronoUnit.HOURS.between(LocalTime.now(), rendezVous.getHeure().toLocalTime()) >= 24) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez annuler le rendez-vous au moins 24h à l'avance");
        }

        rendezVousRepository.delete(rendezVous);

        return ResponseEntity.ok().body("Rendez-vous annulé avec succès");
    }

    @Override
    public List<RendezVousDto> getRendezVousList() {
        List<RendezVousDto> rendezVousDtos = this.rendezVousRepository.findAll().stream().map(this::convertToDto).toList();
        return rendezVousDtos;
    }

    @Override
    public ClientDto create(AppClient client) {
        AppClient appClient = clientRepository.findByEmail(client.getEmail());
        if(appClient != null){
            throw new BadRequestException("User already exist!");
        }
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        return convertClientToDto(this.clientRepository.save(client));
    }

    @Override
    public ClientDto getClientById(Long clientId) {
        return convertClientToDto(this.clientRepository.findById(clientId).get());
    }

    @Override
    public ClientDto getClientByEmail(String email) {
        return convertClientToDto(this.clientRepository.findByEmail(email));
    }

    @Override
    public RendezVousDto getClientRendezVous(Long clientId) {
        RendezVous rendezVous = this.rendezVousRepository.findRendezVousByClientId(clientId).orElseThrow(() -> new NotFoundException("RendezVous not found"));
        return convertToDto(rendezVous);
    }

    @Override
    public List<RendezVousDto> getAllClientRendezVous(Long clientId) {
        List<RendezVous> rendezVous = this.rendezVousRepository.findAllRendezVousByClientId(clientId).orElseThrow(() -> new NotFoundException("User not found : "+clientId));
        return rendezVous.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<AppClient> appClients = this.clientRepository.findAll();
        return appClients.stream().map(this::convertClientToDto).collect(Collectors.toList());
    }

    public boolean isEmployeDisponible(Employe employe, Date date, Time heure, Time duree) {
        List<RendezVous> rendezVousList = rendezVousRepository.findByEmployeAndDateAndHeureAndDuree(employe, date, heure, duree);

        // Si aucun rendez-vous n'existe pour cette période, l'employé est disponible
        return rendezVousList.isEmpty();
    }

    RendezVousDto convertToDto(RendezVous rendezVous) {
        ClientDto clientDto = this.modelMapper.map(rendezVous.getClient(), ClientDto.class);
        EmployeDto employeDto = this.modelMapper.map(rendezVous.getEmploye(), EmployeDto.class);
        Set<ServiceRendezVousDto> serviceRendezVousDtos = rendezVous.getServiceRendezVous()
                .stream()
                .map(serviceRendezVous -> this.modelMapper.map(serviceRendezVous, ServiceRendezVousDto.class))
                .collect(Collectors.toSet());
        RendezVousDto rendezVousDto = this.modelMapper.map(rendezVous, RendezVousDto.class);
        rendezVousDto.setClientDto(clientDto);
        rendezVousDto.setEmployeDto(employeDto);
        rendezVousDto.setServiceRendezVousDtos(serviceRendezVousDtos);
        return rendezVousDto;
    }

    ClientDto convertClientToDto(AppClient appClient) {
        ClientDto appUserDto = this.modelMapper.map(appClient, ClientDto.class);
        return appUserDto;
    }

}
