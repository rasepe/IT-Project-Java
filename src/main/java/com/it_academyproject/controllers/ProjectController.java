package com.it_academyproject.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.it_academyproject.domains.Iteration;
import com.it_academyproject.domains.Project;
import com.it_academyproject.repositories.IterationRepository;
import com.it_academyproject.repositories.ProjectRepository;

@RestController
public class ProjectController {
	
	@Autowired
	IterationRepository iterationRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@GetMapping("/api/projects")
	public List<Project> getAllProjects() {
		List<Project> pr = projectRepository.findAll();
		return pr;
	}
	
	@PostMapping("/api/projects")                                  
	public Project createProject(@Valid @RequestBody Project project) {
		boolean isFound=false;
		List <Project> checkProject=  projectRepository.findAll();
		
		
		//foreach loop to check if the new project is already in the repository
		for (Project one : checkProject) {
			
			
			boolean isSame=false;
			
			if ((project.getName().equals(one.getName()) )) {
				isFound=true;
				String name="This project is already set up";
				project.setName(name);		
							
			}
		}
			
		//if the new project is found, the object returned will alert this in its name.
		if (isFound) {
			return project;
		}else{
			return projectRepository.save(project);
		}
	
	}
}	
