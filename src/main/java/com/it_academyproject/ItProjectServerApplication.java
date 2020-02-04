package com.it_academyproject;

import com.it_academyproject.jwt_security.configuration.SecurityConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages={"com.it_academyproject" , "com.it_academyproject.controllers", "com.it_academyproject.jwt_security"})
@EntityScan(basePackages = {"com.it_academyproject.domains" , "com.it_academyproject.jwt_security.model"} )
@EnableJpaRepositories(basePackages = {"com.it_academyproject.repositories" , "com.it_academyproject.jwt_security.repository"})
@EnableWebMvc
@Import(SecurityConfiguration.class)
public class ItProjectServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItProjectServerApplication.class, args);
		
		
		try {
		      File myObj = new File("LOG/log.txt");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        
		        FileWriter myWriter = new FileWriter("LOG/log.txt",true);
		        myWriter.write("===================================\n");
		        myWriter.write(new Date()+": SERVER INIT\n");
		        myWriter.write("===================================\n\n");

		        myWriter.close();
		        
		        
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
		
	}
	
	

