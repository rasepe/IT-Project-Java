package com.it_academyproject.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.it_academyproject.domains.Exercice;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;

@Repository
public interface IterationRepository extends JpaRepository<Iteration, String> {

	
	List<Iteration> findAllByUsers(MyAppUser user);
	List<Iteration> findByUsers_Id(String id);
	Iteration findById(Long iterationId);
	List<Iteration> findAll();
}