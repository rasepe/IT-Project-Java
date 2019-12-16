package com.it_academyproject.repositories;

import com.it_academyproject.Domains.Exercice;
import com.it_academyproject.Domains.VicMyAppUser;
import com.it_academyproject.Domains.UserExercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VicUserExerciceRepository extends JpaRepository <UserExercice , Integer>
{
    UserExercice findOneByUserStudentAndExercice(VicMyAppUser user , Exercice exercice );
}
