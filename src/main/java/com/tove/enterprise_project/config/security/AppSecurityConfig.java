package com.tove.enterprise_project.config.security;

import com.tove.enterprise_project.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import static com.tove.enterprise_project.authorities.UserRole.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;
    private  final JwtFilter jwtFilter;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, CorsConfigurationSource corsConfigurationSource, JwtFilter jwtFilter) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.corsConfigurationSource = corsConfigurationSource;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/dev/**", "/user/**", "/auth/**").permitAll()
                        .requestMatchers("/userpage").hasRole(USER.name())
                        .requestMatchers("/adminpage").hasRole(ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                )
                .authenticationProvider(authProvider());

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(customUserDetailsService);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
