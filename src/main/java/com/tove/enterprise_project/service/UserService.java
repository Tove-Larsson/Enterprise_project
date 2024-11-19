package com.tove.enterprise_project.service;

import com.tove.enterprise_project.model.AppUser;
import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.tove.enterprise_project.authorities.UserRole.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<AppUserDTO> createUser(AppUserDTO appUserDTO) {

        AppUser appUser = new AppUser(
                appUserDTO.username(),
                passwordEncoder.encode(appUserDTO.password()),
                USER,
                true,
                true,
                true,
                true
        );

        if (userRepository.findByUsername(appUser.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userRepository.save(appUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(appUserDTO);
    }

    public ResponseEntity<AppUserDTO> deleteAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        userRepository.delete(appUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new AppUserDTO(appUser.getUsername()));
    }
}
