package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

}
