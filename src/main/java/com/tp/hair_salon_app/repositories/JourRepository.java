package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.Jour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface JourRepository extends JpaRepository<Jour, Long> {
    List<Jour> findByEmployeAndDateDuJour(Employe employe, LocalDate dateDuJour);
    Optional<List<Jour>> findAllByEmployeId(Long employeId);
}
