package com.it_academyproject.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it_academyproject.Domains.MyAppUser;

public interface MyAppUserRepository extends JpaRepository<MyAppUser, String>{

	public ArrayList<MyAppUser> findByFirstName(String firstName);
	public ArrayList<MyAppUser> findByLastName(String lastName);
	public MyAppUser findByIdDocument(String idDocument);
	
}
