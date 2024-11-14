package com.tove.enterprise_project.dao;

import com.tove.enterprise_project.model.AppUser;
import java.util.Optional;

public interface UserDAO {

    void save(AppUser appUser);
    void update(AppUser appUser);
    void delete(AppUser appUser);
    Optional<AppUser> findById(Long id);
    Optional<AppUser> findByUsername(String username);
}
