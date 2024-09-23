package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<AppClient, Long> {

}
