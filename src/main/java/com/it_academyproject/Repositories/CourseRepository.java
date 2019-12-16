package com.it_academyproject.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it_academyproject.Domains.Course;
import com.it_academyproject.Domains.MyAppUser;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
