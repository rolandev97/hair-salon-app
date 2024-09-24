package com.tp.hair_salon_app.services.servicesImpl;

import com.tp.hair_salon_app.models.AppClient;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.models.dto.AppUserDto;
import com.tp.hair_salon_app.models.dto.ClientDto;
import com.tp.hair_salon_app.models.dto.JwtResponse;
import com.tp.hair_salon_app.security.TokenProvider;
import com.tp.hair_salon_app.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private UserServiceImpl userService;
    private ClientServiceImpl clientService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserServiceImpl userService, ClientServiceImpl clientService){
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<?> authenticateUser(AppUser appUser) {
        //Recupere un objet Authentication
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getMotDePasse()));

        //Modifie le principal actuellement authentifié, ou supprime les informations d'authentification.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Creer un token lorsque l'authentification a ete effectuer avec succes
        String token = tokenProvider.createToken(authentication);

        //Creer un refreshToken lorsque l'authentification a ete effectuer avec succes
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        //Retourne le token dans l'entete de la requete
        AppUserDto user = this.userService.findByEmail(appUser.getEmail());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setRole(user.getRole());
        jwtResponse.setToken(token);
        jwtResponse.setRefreshToken(refreshToken);
        jwtResponse.setUsername(user.getNom());

        return ResponseEntity.ok(jwtResponse);
    }

    @Override
    public ResponseEntity<?> registerUser(AppClient appClient) {
        ClientDto clientDto = this.clientService.create(appClient);
        return ResponseEntity.ok().body(clientDto);
    }
}
