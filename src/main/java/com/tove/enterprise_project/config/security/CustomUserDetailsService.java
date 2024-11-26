package com.tove.enterprise_project.config.security;

import com.tove.enterprise_project.dao.impl.UserDAO;
import com.tove.enterprise_project.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    @Autowired
    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = userDAO
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        System.out.println(appUser);
        return new CustomUserDetail(appUser);
    }




}
