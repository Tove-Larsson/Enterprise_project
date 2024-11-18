package com.tove.enterprise_project.dao.impl;

import com.tove.enterprise_project.dao.UserDAO;
import com.tove.enterprise_project.model.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<AppUser> findByUsername(String username) {
        String query = "SELECT u FROM AppUser u WHERE u.username = :username";

        return entityManager.createQuery(query, AppUser.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }
}
