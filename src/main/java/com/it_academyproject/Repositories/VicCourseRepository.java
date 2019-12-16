package com.it_academyproject.repositories;

import com.it_academyproject.Domains.VicCourse;
import com.it_academyproject.Domains.VicMyAppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface VicCourseRepository extends JpaRepository<VicCourse, Integer>
{

    public List<VicCourse> findByEndDate(Date endDate );
    public List<VicCourse> findByUserStudent(VicMyAppUser user );
}
