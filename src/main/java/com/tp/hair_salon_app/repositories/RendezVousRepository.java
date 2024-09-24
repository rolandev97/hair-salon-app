package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    @Query("SELECT rv FROM RendezVous rv WHERE rv.employe = :employe AND rv.date = :date " + "AND (:heure < ADDTIME(rv.heure, rv.duree) AND ADDTIME(:heure, :duree) > rv.heure)")
    //@Query("SELECT rv FROM RendezVous rv WHERE rv.employe = :employe AND rv.date = :date AND rv.duree = :duree AND rv.heure = :heure")
    List<RendezVous> findByEmployeAndDateAndHeureAndDuree(@Param("employe") Employe employe, @Param("date") Date date,
                                                 @Param("heure") Time heure,
                                                 @Param("duree") Time duree);

    List<RendezVous> findByEmployeIdAndDate(Long employeId, Date date);
}
