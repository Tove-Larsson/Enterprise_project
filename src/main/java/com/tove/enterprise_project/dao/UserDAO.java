package com.tove.enterprise_project.dao;

import com.tove.enterprise_project.model.AppUser;
import java.util.Optional;

public interface UserDAO {

    Optional<AppUser> findByUsername(String username);
}
