package com.iitbombay.coursesapi.repository;

import com.iitbombay.coursesapi.model.Course;
import com.iitbombay.coursesapi.model.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    Optional<Course> findByCourseCode(String courseCode);
    
    List<Course> findByDepartment(String department);
    
    List<Course> findByInstructor(String instructor);
    
    List<Course> findBySemester(Integer semester);
    
    List<Course> findByCourseType(CourseType courseType);
    
    List<Course> findByDepartmentAndSemester(String department, Integer semester);
    
    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:keyword% OR c.courseCode LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Course> findByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT c FROM Course c WHERE c.prerequisites IS EMPTY")
    List<Course> findCoursesWithoutPrerequisites();
    
    @Query("SELECT c FROM Course c JOIN c.prerequisites p WHERE p.id = :prerequisiteId")
    List<Course> findCoursesByPrerequisite(@Param("prerequisiteId") Long prerequisiteId);
    
    @Query("SELECT COUNT(c) > 0 FROM Course c JOIN c.prerequisites p WHERE p.id = :courseId")
    boolean isPrerequisiteForOtherCourses(@Param("courseId") Long courseId);
    
    @Query("SELECT c FROM Course c WHERE c.enrolledStudents < c.maxStudents")
    List<Course> findAvailableCourses();
    
    boolean existsByCourseCode(String courseCode);
} 