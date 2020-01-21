package com.it_academyproject.controllers;
import com.it_academyproject.domains.*;

import com.it_academyproject.services.StatisticsService;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController
{
    @Autowired
    StatisticsService statisticsService;

    @GetMapping( "/api/statistics/per-itinerary" )
    public ResponseEntity<String> perItinerary( )
    {
        try
        {
            JSONObject sendData = statisticsService.perItinerary();
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , exceptionMessage);
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping( "/api/statistics/per-gender" )
    public ResponseEntity<String> perGender( )
    {
        try
        {
            JSONObject sendData = statisticsService.perGender();
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , exceptionMessage);
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping( "/api/statistics/per-absence" )
 
    public List<Absence> getAbsences(@RequestBody MyAppUser student){
    	   	return  statisticsService.absencesByIdDocument(student.getIdDocument());
    	  
    }
    
    
    
    @GetMapping( "/api/statistics/finish-in-x-days" )
    public ResponseEntity<String> finishInXdays( @RequestBody String body )
    {
        try
        {
            JSONObject sendData = statisticsService.finishInXdays( body );
            return new ResponseEntity( sendData.toString() , HttpStatus.FOUND);
        }
        catch (Exception e)
        {
            String exceptionMessage = e.getMessage();
            JSONObject sendData = new JSONObject();
            JSONObject message = new JSONObject();
            message.put("type" , "error");
            message.put("message" , exceptionMessage);
            sendData.put("Message" , message);
            return new ResponseEntity( sendData.toString() , HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @GetMapping( "/api/statistics" )

    public List<Absence> getallAbs(){
    	return statisticsService.getAllAbsences();
    }

}

