package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.exception.ResourceNotFoundException;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.Role;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.repositories.ClientRepository;
import com.tp.hair_salon_app.repositories.UserRepository;
import com.tp.hair_salon_app.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    @Override
    public AppUserDto createUser(AppUser appUser) {
        appUser.setRole(Role.CLIENT);
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

    @Override
    public ResponseEntity changePassword(Long id, String oldPass, String newPass) {
        //Check if user exist
        AppUser user = this.userRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("This resource ", "UserID not found ", id));

        //Check if password are the same
        if(!passwordEncoder.matches(oldPass, user.getMotDePasse())){
            return ResponseEntity.badRequest().body("Les mots de passe ne correspondent pas!");
        }

        //Change pass and save
        user.setMotDePasse(passwordEncoder.encode(newPass));
        this.userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @Override
    public AppUserDto findByEmail(String email) {
        return convertToDto(this.userRepository.findByEmail(email).get());
    }

    AppUserDto convertToDto(AppUser appUser) {
        AppUserDto appUserDto = this.modelMapper.map(appUser, AppUserDto.class);
        return appUserDto;
    }
}
