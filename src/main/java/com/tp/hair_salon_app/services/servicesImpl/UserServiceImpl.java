package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.exception.ResourceNotFoundException;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Role;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.repositories.UserRepository;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public AppUserDto createUser(AppUser appUser) {
        if(appUser.getRole().name().isEmpty()){
            appUser.setRole(Role.CLIENT);
        }
        AppUser userResp = this.userRepository.save(appUser);
        return convertToDto(userResp);
    }

    @Override
    public AppUserDto updateUser(AppUser appUser, Long userId) {
        AppUser appUser1 = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
        appUser1.setEmail(appUser.getEmail());
        appUser1.setNom(appUser.getNom());
        appUser1.setPrenom(appUser.getPrenom());
        appUser1.setNumeroTelephone(appUser.getNumeroTelephone());

        return convertToDto(this.userRepository.save(appUser1));
    }

    AppUserDto convertToDto(AppUser appUser) {
        AppUserDto appUserDto = this.modelMapper.map(appUser, AppUserDto.class);
        return appUserDto;
    }
}
