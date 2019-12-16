package com.it_academyproject.repositories;


import com.it_academyproject.Domains.VicMyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;


@Repository
public interface VicMyAppUserRepository extends JpaRepository <VicMyAppUser, String>
{
    VicMyAppUser findOneById(String id );

    VicMyAppUser findByEmail(String email);

    @Query("UPDATE MyAppUser u SET u.lastLogin=:lastLogin WHERE u.email = ?#{ principal?.email }")
    @Modifying
    @Transactional
    public void updateLastLogin (@Param("lastLogin") Date lastLogin);

    List<VicMyAppUser> findByFirstNameAndLastName (String name , String lastName );

    List<VicMyAppUser> findByFirstName (String firstName );

    @Query(value="SELECT u from MyAppUser u WHERE u.firstName like '%:name%'")
    List<VicMyAppUser> findUserByNameLike(@Param("name") String name);
}


