package com.it_academyproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.repositories.MyAppUserRepository;

@Service
public class MyAppUserService {
	
	@Autowired
	MyAppUserRepository myAppUserRepository;
	
	//getAll
	public List<MyAppUser> getAll(){
		return myAppUserRepository.findAll();
	}

	//get by name
	public ArrayList<MyAppUser> getByName(String firstName){
		return myAppUserRepository.findByFirstName(firstName);
	}

	//get by surName
	public ArrayList<MyAppUser> getBySurname(String lastName) {
		return myAppUserRepository.findByLastName(lastName);
	}

	//get by dni
	public MyAppUser getByDni(String idDocument) {
		return myAppUserRepository.findByIdDocument(idDocument);
	}

	//get by Id
	public MyAppUser getById(String id) {
		MyAppUser student = null;
		Optional<MyAppUser> studentOptional = myAppUserRepository.findById(id);
		System.out.println(id);
		if(studentOptional.isPresent()) {
			student = studentOptional.get();
		}else {
			System.out.println("Student not found 404");
		}
		System.out.println(student.getFirstName());
		return student;
	}
	
	// Put - Edit by dni
		public MyAppUser editGetByDni(String idDocument, MyAppUser student) {
			
			myAppUserRepository.save(student);
			
			 return student;
		}
	
		
}