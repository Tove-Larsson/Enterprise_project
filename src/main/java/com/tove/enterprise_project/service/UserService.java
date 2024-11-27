package com.tove.enterprise_project.service;

import com.tove.enterprise_project.dao.impl.UserDAO;
import com.tove.enterprise_project.jwt.JWTService;
import com.tove.enterprise_project.model.AppUser;
import com.tove.enterprise_project.model.dto.AppUserDTO;
import com.tove.enterprise_project.model.dto.TokenDTO;
import com.tove.enterprise_project.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.tove.enterprise_project.authorities.UserRole.ADMIN;
import static com.tove.enterprise_project.authorities.UserRole.USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserDAO userDAO;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDAO = userDAO;
    }

    @Transactional
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

        if (userDAO.findByUsername(appUser.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userRepository.save(appUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(appUserDTO);
    }

    @Transactional
    public ResponseEntity<AppUserDTO> deleteAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        AppUser appUser = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        userRepository.delete(appUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new AppUserDTO(appUser.getUsername()));
    }

    public TokenDTO verify(AppUserDTO appUserDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserDTO.username(), appUserDTO.password()));

        String generatedToken = jwtService.generateToken(appUserDTO.username());
        System.out.println("Generated token: " + generatedToken);
        return new TokenDTO(generatedToken);

    }

    @Transactional
    public ResponseEntity<String> adminDeleteUser(String username) {

        Optional<AppUser> userToDelete = userDAO.findByUsername(username);

        if (userToDelete.isEmpty()) {
            throw new UsernameNotFoundException(username + " could not be found");
        }

        AppUser appUser = userToDelete.get();
        userRepository.delete(appUser);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(appUser.getUsername());
    }

    public ResponseEntity<AppUserDTO> createAdminUser(AppUserDTO appUserDTO, Authentication authentication) {

        String currentUsername = authentication.getName();
        AppUser currentUser = userDAO.findByUsername(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!currentUser.getUserRole().name().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (userDAO.findByUsername(appUserDTO.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AppUser newAdminUser = new AppUser(
                appUserDTO.username(),
                passwordEncoder.encode(appUserDTO.password()),
                ADMIN,
                true,
                true,
                true,
                true

        );

        userRepository.save(newAdminUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(appUserDTO);

    }



}

