package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.Jour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourRepository extends JpaRepository<Jour, Long> {

}
