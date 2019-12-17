package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciceRepository extends JpaRepository <UserExercice , Integer>
{
    UserExercice findOneByUserStudentAndExercice(MyAppUser user , Exercice exercice );
}