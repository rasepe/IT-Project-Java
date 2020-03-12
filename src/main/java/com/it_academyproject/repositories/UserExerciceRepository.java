package com.it_academyproject.repositories;

import com.it_academyproject.domains.Exercise;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.domains.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserExerciceRepository extends JpaRepository <UserExercise , Integer>
{
    UserExercise findOneByUserStudentAndExercice(MyAppUser user , Exercise exercice );
    List<UserExercise> findByUserStudent(MyAppUser user );

    //List<UserExercice> findBy
}
