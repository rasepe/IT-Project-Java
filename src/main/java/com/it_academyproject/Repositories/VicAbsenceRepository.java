package com.it_academyproject.repositories;

import com.it_academyproject.Domains.VicAbsence;
import com.it_academyproject.Domains.VicMyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VicAbsenceRepository extends JpaRepository<VicAbsence, Integer>
{
    //student id y date missing
    List<VicAbsence> findByUserStudentAndDateMissing (VicMyAppUser userStudent , Date dateMissing );
}
