package com.tp.hair_salon_app.repositories;

import com.tp.hair_salon_app.models.AppService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<AppService, Long> {

}
