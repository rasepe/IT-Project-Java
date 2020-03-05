package com.it_academyproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.it_academyproject.services.EmailService;

@Controller
public class EmailController {
	
	@Autowired
	public EmailService emailService;
	
	@Scheduled(cron = "0 0 9 * * ?")
	@PostMapping("/api/notification") //TO TEST THIS CALL YOU HAVE TO EDIT THE COURSE TABLE (END DATE TO 15 DAYS OR 30 DAYS FROM NOW)
	public String sendMail() throws Exception {		

		emailService.notificationEmail();
		
		return "Successfully sent email";
	}
	
	
	

}