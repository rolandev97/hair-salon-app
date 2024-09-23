package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.ServiceRendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRendezVousRepository extends JpaRepository<ServiceRendezVous, Long> {

}
