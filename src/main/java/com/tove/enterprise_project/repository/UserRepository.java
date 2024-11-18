package com.tove.enterprise_project.repository;

import com.tove.enterprise_project.dao.UserDAO;
import com.tove.enterprise_project.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>, UserDAO {

}
