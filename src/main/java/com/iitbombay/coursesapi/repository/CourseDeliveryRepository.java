package com.iitbombay.coursesapi.repository;

import com.iitbombay.coursesapi.model.CourseDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDeliveryRepository extends JpaRepository<CourseDelivery, Long> {
    
    List<CourseDelivery> findByYear(Integer year);
    
    List<CourseDelivery> findBySemester(Integer semester);
    
    List<CourseDelivery> findByYearAndSemester(Integer year, Integer semester);
    
    List<CourseDelivery> findByCourseId(Long courseId);
    
    @Query("SELECT cd FROM CourseDelivery cd WHERE cd.course.id = :courseId AND cd.year = :year AND cd.semester = :semester")
    List<CourseDelivery> findByCourseAndYearAndSemester(@Param("courseId") Long courseId, 
                                                       @Param("year") Integer year, 
                                                       @Param("semester") Integer semester);
    
    @Query("SELECT cd FROM CourseDelivery cd JOIN cd.course c WHERE c.department = :department")
    List<CourseDelivery> findByDepartment(@Param("department") String department);
    
    @Query("SELECT cd FROM CourseDelivery cd JOIN cd.course c WHERE c.instructor = :instructor")
    List<CourseDelivery> findByInstructor(@Param("instructor") String instructor);
    
    @Query("SELECT cd FROM CourseDelivery cd ORDER BY cd.year DESC, cd.semester DESC")
    List<CourseDelivery> findAllOrderByYearAndSemesterDesc();
} 