package com.tp.hair_salon_app.security;


import com.tp.hair_salon_app.exception.NotFoundException;
import com.tp.hair_salon_app.models.AppUser;
import com.tp.hair_salon_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUserDetailService() {
        super();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

       return new UserPrincipal(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found : "+id)
        );

        return new UserPrincipal(user);
    }

    

}
