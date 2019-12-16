package com.it_academyproject.repositories;

import com.it_academyproject.Domains.Exercice;
import com.it_academyproject.Domains.MyAppUser;
import com.it_academyproject.Domains.UserExercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExerciceRepository extends JpaRepository <UserExercice , Integer>
{
    UserExercice findOneByUserStudentAndExercice(MyAppUser user , Exercice exercice );
}
