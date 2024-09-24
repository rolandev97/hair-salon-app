package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.Jour;
import com.tp.hair_salon_app.models.JoursFerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JourFerieRepository extends JpaRepository<JoursFerie, Long> {

}
