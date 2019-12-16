package com.it_academyproject.repositories;

import com.it_academyproject.Domains.Exercice;
import com.it_academyproject.Domains.StatusExercice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusExerciceRepository extends JpaRepository<StatusExercice, Integer>
{
        StatusExercice findOneById( Integer id );
}
