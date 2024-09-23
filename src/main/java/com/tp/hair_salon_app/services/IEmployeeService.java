package com.tp.hair_salon_app.services;

import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import org.springframework.http.ResponseEntity;

public interface IEmployeeService {
    ResponseEntity<?> createEmployee(Employe employe);
    ResponseEntity<?> updateEmployee(Employe employe, Long id);
}
