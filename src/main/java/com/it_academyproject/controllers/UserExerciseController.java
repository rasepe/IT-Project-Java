
package com.it_academyproject.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.it_academyproject.services.UserExerciseService;
import com.it_academyproject.tools.View;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserExerciseController
{
    @Autowired
    UserExerciseService userExerciseService;


    @GetMapping ("/api/userExercise/{itineraryId}")
    public ResponseEntity<String>  getExerciseStudentByItinerary (@PathVariable String itineraryId )
    {
        try
        {
            JSONObject sendData = userExerciseService.getExerciseStudentByItinerary( itineraryId );
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
    @GetMapping ("/api/userExercise")
    public ResponseEntity  getExerciseStudentByItinerary ( )
    {
        try
        {
            //get a list of all the itnerarys

            JSONObject sendData = userExerciseService.getExerciseStudentByItinerary( );
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
}

