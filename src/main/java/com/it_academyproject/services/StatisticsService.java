package com.it_academyproject.services;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.exceptions.UserNotFoundException;

import com.it_academyproject.repositories.AbsenceRepository;
import com.it_academyproject.repositories.ItineraryRepository;
import com.it_academyproject.repositories.CourseRepository;
import com.it_academyproject.repositories.MyAppUserRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public JSONObject perItinerary() throws Exception
    {
        JSONObject sendData = new JSONObject();
        return sendData;
    }
    public JSONObject perGender() throws Exception
    {
        JSONObject sendData = new JSONObject();
        return sendData;
    }
    public JSONObject perAbsence( String body ) throws Exception
    {
        JSONObject sendData = new JSONObject();
        return sendData;
    }
    public JSONObject finishInXdays( String body ) throws Exception
    {
        JSONObject sendData = new JSONObject();
        return sendData;
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

}
