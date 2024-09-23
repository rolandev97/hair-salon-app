package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.models.Employe;
import com.tp.hair_salon_app.repositories.EmployeRepository;
import com.tp.hair_salon_app.services.IEmployeeService;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private EmployeRepository employeRepository;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeRepository employeRepository, ModelMapper modelMapper){
        this.employeRepository = employeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<?> createEmployee(Employe employe) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateEmployee(Employe employe, Long id) {
        return null;
    }
}
