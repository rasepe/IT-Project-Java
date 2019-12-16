package com.it_academyproject.repositories;

import com.it_academyproject.Domains.Absence;
import com.it_academyproject.Domains.MyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Integer>
{
    //student id y date missing
    List<Absence> findByUserStudentAndDateMissing (MyAppUser userStudent , Date dateMissing );
}
