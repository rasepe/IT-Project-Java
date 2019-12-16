package com.it_academyproject.jwt_security.repository;

import com.it_academyproject.Domains.VicMyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface MyAppUserRepository extends JpaRepository<VicMyAppUser, String>
{

    VicMyAppUser findOneById(String id );

    VicMyAppUser findByEmail(String email);

    @Query("UPDATE MyAppUser u SET u.lastLogin=:lastLogin WHERE u.email = ?#{ principal?.email }")
    @Modifying
    @Transactional
    public void updateLastLogin (@Param("lastLogin") Date lastLogin);
}
