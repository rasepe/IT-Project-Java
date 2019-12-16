package com.it_academyproject.jwt_security.controller;
import com.it_academyproject.jwt_security.repository.MyAppUserRepository;
import com.it_academyproject.repositories.VicAbsenceRepository;
import com.it_academyproject.repositories.VicCourseRepository;
import com.it_academyproject.tools.dataImporter.DataImporter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/public/")
public class PublicController
{
    @Autowired
    DataImporter dataImporter;
    private final String SLASH = "\\";

    @GetMapping
    public String getMessage()
    {
        JSONObject errorData = new JSONObject();
        dataImporter.manualCreation();
        dataImporter.importAlumnesActius();
        errorData.put( "Exercises" , dataImporter.importEjerciciosporalumno() );
        dataImporter.importTaules();

        return errorData.toString();
    }

}
