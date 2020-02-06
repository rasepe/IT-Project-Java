package com.it_academyproject.services;

import com.google.gson.Gson;
import com.it_academyproject.domains.Absence;
import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.Itinerary;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.UserNotFoundException;

import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class StatisticsService
{

    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ItineraryRepository itineraryRepository;
    @Autowired
    MyAppUserRepository myAppUserRepository;


    
    public String perItinerary() throws Exception{
        List<Itinerary> numberOfItinerary = new ArrayList<>();
        itineraryRepository.findAll().forEach(numberOfItinerary::add);
        List<String> listPerItinerary = new ArrayList<>();
        for(int i=0;i<numberOfItinerary.size();i++) {
        	Itinerary itinerary = numberOfItinerary.get(i);       	
        	Integer numberPerItinerary = courseRepository.findByItinerary(itinerary).size();
        	listPerItinerary.add(numberPerItinerary.toString());        	
        }        
        ArrayList<ArrayList<String>> peopleByItinerary = new ArrayList<ArrayList<String>>();
        for (int i=0;i<numberOfItinerary.size();i++) {
        	peopleByItinerary.add(peopleByItineraryMethod(numberOfItinerary.get(i).getName(),listPerItinerary.get(i)));
        }
        Gson Json= new Gson();
        String sendData = Json.toJson(peopleByItinerary);
    	return sendData;
    }
    
    public String perGender() throws Exception {
        List<Character> typeOfGender = new ArrayList<Character>();
        	typeOfGender.add('M');
        	typeOfGender.add('F');
    	List<String> listNumberOfUsers = new ArrayList<>();
        for(int i=0;i<typeOfGender.size();i++) {
        	Character gender = typeOfGender.get(i);
        	Integer numberPerUsers = myAppUserRepository.findByGender(gender).size();
        	listNumberOfUsers.add(numberPerUsers.toString());
        }
        ArrayList<ArrayList<String>> peopleByGender = new ArrayList<ArrayList<String>>();
        for(int i=0;i<typeOfGender.size();i++) {
        	peopleByGender.add(peopleByGenderMethod(typeOfGender.get(i),listNumberOfUsers.get(i)));
        }
        Gson Json = new Gson();        
        String sendData = Json.toJson(peopleByGender);
        return sendData;
    }
    
    public String perAbsence() throws Exception
    {
    	List<Absence> absence = new ArrayList<>();
    	absenceRepository.findAll().forEach(absence::add);
    	HashMap<String,Integer>numberOfAbsence = new HashMap<>();
    	for(int i = 0; i < absence.size(); i++) {  
            Integer counter = 0; 
    		for(int j = i + 1; j < absence.size(); j++) {  
                 if(absence.get(i).getUserStudent().getId().equals(absence.get(j).getUserStudent().getId())) {  
                    counter++;  
                 }
             }
    		if(counter>=8) {
    			numberOfAbsence.put(absence.get(i).getUserStudent().getId(), counter);
    		}
    	}
    	Gson Json = new Gson();
    	String sendData = Json.toJson(numberOfAbsence);
        return sendData;
    }
    
    public Integer finishInXdays() throws Exception
    {
        List<Course> finishInXDays = new ArrayList<>();
        courseRepository.findAll().forEach(finishInXDays::add);
        Date currentDate = Calendar.getInstance().getTime();
        /*Calendar cal = Calendar.getInstance();        
        for(Integer i=1;i<15;i++) {
        	cal.setTime(currentDate);
        	cal.add(Calendar.DATE,i);
	        courseRepository.findByEndDate(cal.getTime()).forEach(finishInXDays::add);
        }
    	Integer numberOfPeopleThatFinishInXDays = finishInXDays.size();*/
        Calendar comparatorDate = Calendar.getInstance();
        comparatorDate.setTime(currentDate);
        comparatorDate.add(Calendar.DATE, 14);
        Date comparatorDate1 = comparatorDate.getTime();
        Integer counter=0;
        for(int i=0;i<finishInXDays.size();i++) {
        	if (finishInXDays.get(i).getEndDate().compareTo(comparatorDate1)<=14) {
        		counter++;
        	}
        }
    	
    	//JSONObject sendData = new JSONObject();
        return counter;
    }
    public List<MyAppUser> getAllActiveStudents ( )
    {

        List<Course> courseList = courseRepository.findByEndDate( null );
        List<MyAppUser> activeStudents = new ArrayList<>();
        Course course;
        MyAppUser myAppUser;
        for (int i = 0; i < courseList.size() ; i++)
        {
            course = courseList.get(i);
            MyAppUser student = course.getUserStudent();
            myAppUser = myAppUserRepository.findOneById( student.getId() );
            if ( myAppUser != null )
            {
                activeStudents.add( myAppUser );
            }
            else
            {
                throw (new UserNotFoundException( student.getId() ));
            }
        }
        return (activeStudents);
    }
    public static ArrayList<String> peopleByItineraryMethod(String itinerary, String numberOfPeople) {
    	ArrayList<String> itineraryObject = new ArrayList<>();
    	itineraryObject.add(itinerary);
    	itineraryObject.add(numberOfPeople);
    	return itineraryObject;
    }
    public static ArrayList<String> peopleByGenderMethod (Character typeOfGender, String numberOfUsers){
    	ArrayList<String> peopleByGenderList = new ArrayList<String>();
    	peopleByGenderList.add(typeOfGender.toString());
    	peopleByGenderList.add(numberOfUsers);
    	return peopleByGenderList;
    }
}
