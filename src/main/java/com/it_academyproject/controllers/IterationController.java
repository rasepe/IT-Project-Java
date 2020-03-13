package com.it_academyproject.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.UserNotFoundException;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.MyAppUserRepository;
import com.it_academyproject.services.MyAppUserService;
import com.it_academyproject.tools.View;

@RestController
public class IterationController {
	
	@Autowired
	IterationRepository iterationRepository;
	
	 
	@Autowired
	private   MyAppUserRepository myAppUserRepository;
	
	// GET method to view which iterations have one user using sql table user_iteration.
	//@JsonView(View.Summary.class)
	@GetMapping("/api/students/{userId}/iterations")
	public List<Iteration> getAllIterationsByUserId(@PathVariable (value = "userId") String userId) {
		return iterationRepository.findByUsers_Id(userId);
	}
	
	
	//GET method to view all iterations
	//@JsonView(View.Summary.class)
	@GetMapping("/api/iterations")
	public List<Iteration> getAllIterations() {
		List<Iteration> it = iterationRepository.findAll();
		return it;
	}
	
	
	//This method is not fully implemented, it is used to add iterations to users.
	//@PutMapping("/api/students/{userId}/iterations")
	public void updateIteration(@Valid @RequestBody Long iterationId, String userId) {
		MyAppUser user = myAppUserRepository.findOneById(userId);
		Iteration iteration = iterationRepository.findById(iterationId);
		iteration.setUsers(user);
		user.setIterations(iteration);	
		
	}
	
	@PostMapping("/api/iterations")                                  
	public Iteration createIteration(@Valid @RequestBody Iteration iteration) {
		boolean isFound=false;
		List <Iteration> checkIteration=  iterationRepository.findAll();
		
		
		for (Iteration one : checkIteration) {
			
			
			
			
			if ((iteration.getName().equals(one.getName()) )) {
				isFound=true;
				String name="This iteration is already set up";
				iteration.setName(name);		
							
			}
		}
			
		//if the new iteration is found, the object returned will alert this in its name.
		if (isFound) {
			return iteration;
		}else{
			return iterationRepository.save(iteration);
		}
		
	

}
}
