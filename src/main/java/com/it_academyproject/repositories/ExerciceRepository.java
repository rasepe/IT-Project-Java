package com.it_academyproject.repositories;


import com.it_academyproject.Domains.Exercice;
import com.it_academyproject.Domains.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExerciceRepository extends JpaRepository<Exercice, Integer>
{
    List<Exercice> findAllByNameAndItinerary (String name , Itinerary itinerary);
    Exercice findOneById ( Integer exerciceId );
}

