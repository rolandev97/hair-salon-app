package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.exception.ResourceNotFoundException;
import com.tp.hair_salon_app.models.*;
import com.tp.hair_salon_app.models.dto.*;
import com.tp.hair_salon_app.repositories.*;
import com.tp.hair_salon_app.services.IEmployeeService;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private EmployeRepository employeRepository;
    private JourRepository jourRepository;
    private ModelMapper modelMapper;
    private ClientRepository clientRepository;
    private RendezVousRepository rendezVousRepository;
    private ServiceRendezVousRepository serviceRendezVousRepository;
    private FactureRepository factureRepository;
    private JourFerieRepository jourFerieRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(ClientRepository clientRepository, JourFerieRepository jourFerieRepository,FactureRepository factureRepository,EmployeRepository employeRepository, JourRepository jourRepository,RendezVousRepository rendezVousRepository, ServiceRendezVousRepository serviceRendezVousRepository,ModelMapper modelMapper, PasswordEncoder passwordEncoder1){
        this.clientRepository = clientRepository;
        this.rendezVousRepository = rendezVousRepository;
        this.serviceRendezVousRepository = serviceRendezVousRepository;
        this.employeRepository = employeRepository;
        this.jourFerieRepository = jourFerieRepository;
        this.modelMapper = modelMapper;
        this.factureRepository = factureRepository;
        this.jourRepository = jourRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    @Override
    public ResponseEntity<?> createEmployee(Employe employe) {
        if (employe.getPlanning() != null && !employe.getPlanning().isEmpty()) {
            employe.getPlanning()
                    .forEach(planning -> planning.setEmploye(employe));
        }
        // Enregistrer l'employé et ses plannings
        employe.setMotDePasse(passwordEncoder.encode(employe.getMotDePasse()));
        Employe employe1 = this.employeRepository.findEmployeByEmail(employe.getEmail());
        if(employe1 != null){
            throw new ResourceNotFoundException("Employe", employe1.getEmail(),"Un utilisateur avec cette adresse mail existe deja!");
        }
        Employe savedEmployee = this.employeRepository.save(employe);
        return ResponseEntity.status(HttpStatus.OK).body("L'employé à été enregistré avec succès !");
    }

    @Override
    public ResponseEntity<?> updateEmployee(Employe employeDetails, Long id) {
        // Récupérer l'employé existant dans la base de données
        Optional<Employe> employeOptional = employeRepository.findById(id);

        if (employeOptional.isEmpty()) {
            // Si l'employé n'existe pas, retourner une réponse 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé non trouvé");
        }

        Employe existingEmploye = employeOptional.get();

        // Mettre à jour les informations de l'employé
        existingEmploye.setNom(employeDetails.getNom());
        existingEmploye.setPrenom(employeDetails.getPrenom());
        existingEmploye.setEmail(employeDetails.getEmail());
        existingEmploye.setNumeroTelephone(employeDetails.getNumeroTelephone());
        existingEmploye.setRole(employeDetails.getRole());
        existingEmploye.setMotDePasse(employeDetails.getMotDePasse());

        // Mettre à jour le planning
        if (employeDetails.getPlanning() != null && !employeDetails.getPlanning().isEmpty()) {
            // Supprimer les anciens plannings (ou les mettre à jour si nécessaire)
            existingEmploye.getPlanning().clear();

            // Associer chaque planning au nouvel employé et les ajouter à la liste
            employeDetails.getPlanning().stream()
                    .forEach(planning -> {
                        planning.setEmploye(existingEmploye);
                        existingEmploye.getPlanning().add(planning);
                    });
        }

        // Sauvegarder l'employé mis à jour
        Employe updatedEmploye = employeRepository.save(existingEmploye);

        // Retourner la réponse avec l'employé mis à jour
        return ResponseEntity.ok().body("Succès");
    }

    @Override
    public ResponseEntity<?> prendreRendezVois(RendezVousDto rendezVousDto, List<Long> serviceIds) {
        // Vérifier si l'employé est disponible pour la plage horaire choisie
        Optional<Employe> employeOpt = employeRepository.findById(rendezVousDto.getEmployeDto().getId());
        if (employeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé non trouvé");
        }
        Employe employe = employeOpt.get();

        if (!isEmployeDisponible(employe, rendezVousDto.getDate(), rendezVousDto.getHeure(), rendezVousDto.getDuree())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'employé n'est pas disponible à cette heure");
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

    public boolean isEmployeDisponible(Employe employe, Date date, Time heure, Time duree) {
        List<RendezVous> rendezVousList = rendezVousRepository.findByEmployeAndDateAndHeureAndDuree(employe, date, heure, duree);

        // Si aucun rendez-vous n'existe pour cette période, l'employé est disponible
        return rendezVousList.isEmpty();
    }

    @Override
    public ResponseEntity<?> deplacerRendezVous(Long rendezVousId, String nouvelleDate, String nouvelleHeure) {
        Optional<RendezVous> rendezVousOpt = rendezVousRepository.findById(rendezVousId);
        if (rendezVousOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rendez-vous non trouvé");
        }
        RendezVous rendezVous = rendezVousOpt.get();

//        // Vérifier si le déplacement est effectué au moins 24h à l'avance
//        if (ChronoUnit.HOURS.between(LocalTime.now(), rendezVous.getHeure().toLocalTime()) >= 24) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez déplacer le rendez-vous au moins 24h à l'avance");
//        }

        Date convertDate = Date.valueOf(nouvelleDate);
        rendezVous.setDate(convertDate);

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

//        // Vérifier si l'annulation est effectuée au moins 24h à l'avance
//        if (ChronoUnit.HOURS.between(LocalTime.now(), rendezVous.getHeure().toLocalTime()) >= 24) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vous devez annuler le rendez-vous au moins 24h à l'avance");
//        }

        rendezVousRepository.delete(rendezVous);

        return ResponseEntity.ok().body("Rendez-vous annulé avec succès");
    }

    @Override
    public List<RendezVousDto> getRendezVousList() {
        return this.rendezVousRepository.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public ResponseEntity<?> createFacture(Long rendezVousId, Long employeeId) {
        // Récupérer le rendez-vous par son ID
        RendezVous rendezVous = rendezVousRepository.findById(rendezVousId)
                .orElseThrow(() -> new ResourceNotFoundException("RendezVous", rendezVousId.toString(),"Rendez-vous non trouvé"));

        // Récupérer l'employé par son ID
        Employe employe = employeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeException", employeeId.toString(),"Employé non trouvé"));

        // Calculer le montant total à partir des services associés au rendez-vous
        List<ServiceRendezVous> servicesRendezVous = serviceRendezVousRepository.findByRendezVous(rendezVous);

        Double montantTotal = servicesRendezVous.stream()
                .mapToDouble(serviceRendezVous -> serviceRendezVous.getServices().getMontant())
                .sum();

        // Créer la facture
        Facture facture = new Facture();
        facture.setMontant(montantTotal);
        facture.setDate(new java.util.Date());
        facture.setEstPayee(false);
        facture.setRendezVous(rendezVous);

        // Enregistrer la facture
        factureRepository.save(facture);

        return ResponseEntity.ok("Facture créée avec succès");
    }

    @Override
    public ResponseEntity<?> modifierHoraire(Long employeeId, Jour jour) {
        Employe employe = employeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe ID : ", employeeId.toString(),"Employé non trouvé"));
        Jour jour1 = new Jour();
        jour1.setTypeDeJour(jour.getTypeDeJour());
        jour1.setEmploye(employe);
        jour1.setHeureDebut(jour.getHeureDebut());
        jour1.setHeureFin(jour.getHeureFin());
        jour1.setDateDuJour(jour.getDateDuJour());

        this.jourRepository.save(jour1);
        return ResponseEntity.ok("Horaire employé mis a jour!");
    }

    @Override
    public void genererCalendrierAnnuel(LocalDate startDate) {
        LocalDate endDate = startDate.plusYears(1);
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            // Ajouter des jours de fermeture réguliers (par exemple, dimanche et lundi)
            if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY || currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                ajouterJourFermetureRegulier(Date.valueOf(currentDate));
            } else {
                ajouterJourDeTravail(Date.valueOf(currentDate));
            }
            currentDate = currentDate.plusDays(1);
        }
    }

    // Ajouter un jour de fermeture régulier (ex : dimanche, lundi)
    private void ajouterJourFermetureRegulier(Date date) {
        Jour jour = new Jour();
        jour.setDateDuJour(date);
        jour.setTypeDeJour(TypeDeJour.JC);  // Jour de congé régulier
        jourRepository.save(jour);
    }

    // Ajouter un jour de travail régulier
    private void ajouterJourDeTravail(Date date) {
        Jour jour = new Jour();
        jour.setDateDuJour(date);
        jour.setTypeDeJour(TypeDeJour.T);  // Jour de travail
        jourRepository.save(jour);
    }

    // Ajouter un jour férié
    public void ajouterJourFerie(LocalDate date) {
        JoursFerie jourFerie = new JoursFerie();
        jourFerie.setDate(Date.valueOf(date));
        jourFerieRepository.save(jourFerie);
    }

    // Supprimer un jour férié
    public void supprimerJourFerie(Long jourId) {
        jourFerieRepository.deleteById(jourId);
    }

    @Override
    public ResponseEntity<?> ajouterJourConge(Long employeId, Date date, boolean demiJour) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employe ID : ", employeId.toString(),"Employé non trouvé"));
        Jour jour = new Jour();
        jour.setDateDuJour(date);
        jour.setEmploye(employe);
        if (demiJour) {
            jour.setTypeDeJour(TypeDeJour.DC);  // Demi-jour de congé
        } else {
            jour.setTypeDeJour(TypeDeJour.JC);  // Jour de congé complet
        }
        if(verifierConflitsRendezVous(employeId, date)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Impossible d'attribuer le jour de congé à cette date car l'employé :"+employeId+" n'est pas libre!");
        }else {
            jourRepository.save(jour);
        }

        return ResponseEntity.ok().body("Jour de congé ajouté pour l'employé!");
    }

    @Override
    public void supprimerJourConge(Long jourId) {
        jourRepository.deleteById(jourId);
    }

    @Override
    public EmployeDto employeDetailParId(Long employeId) {
        return convertEmplToDto(this.employeRepository.findById(employeId).get());
    }

    @Override
    public EmployeDto employeDetailParEmail(String email) {
        return convertEmplToDto(this.employeRepository.findEmployeByEmail(email));
    }

    public boolean verifierConflitsRendezVous(Long employeId, Date date) {
        List<RendezVous> rendezVousList = rendezVousRepository.findByEmployeIdAndDate(employeId, date);

        // Si des rendez-vous existent à cette date, retourner un message d'erreur
        return !rendezVousList.isEmpty();
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

    EmployeDto convertEmplToDto(Employe employe){
        Set<RendezVousDto> rendezVousDtos = employe.getRendezVous()
                .stream()
                .map(rendezVous -> this.modelMapper.map(rendezVous, RendezVousDto.class))
                .collect(Collectors.toSet());
        List<JourDto> jourDtos = employe.getPlanning().stream().map(jour -> this.modelMapper.map(jour, JourDto.class)).toList();
        EmployeDto employeDto = this.modelMapper.map(employe, EmployeDto.class);
        employeDto.setPlanningDto(jourDtos);
        employeDto.setRendezVousDtos(rendezVousDtos);

        return employeDto;
    }

}
