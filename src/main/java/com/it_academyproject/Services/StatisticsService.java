package com.it_academyproject.services;

import com.it_academyproject.Domains.VicCourse;
import com.it_academyproject.Domains.VicMyAppUser;
import com.it_academyproject.Exceptions.UserNotFoundException;
import com.it_academyproject.repositories.VicAbsenceRepository;
import com.it_academyproject.repositories.VicCourseRepository;
import com.it_academyproject.repositories.VicItineraryRepository;
import com.it_academyproject.repositories.VicMyAppUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService
{

    @Autowired
    VicAbsenceRepository absenceRepository;
    @Autowired
    VicCourseRepository courseRepository;
    @Autowired
    VicItineraryRepository itineraryRepository;
    @Autowired
    VicMyAppUserRepository myAppUserRepository;


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
    public List<VicMyAppUser> getAllActiveStudents ( )
    {

        List<VicCourse> vicCourseList = courseRepository.findByEndDate( null );
        List<VicMyAppUser> activeStudents = new ArrayList<>();
        VicCourse vicCourse;
        VicMyAppUser myAppUser;
        for (int i = 0; i < vicCourseList.size() ; i++)
        {
            vicCourse = vicCourseList.get(i);
            VicMyAppUser student = vicCourse.getUserStudent();
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
