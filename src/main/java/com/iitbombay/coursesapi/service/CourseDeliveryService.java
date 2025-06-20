package com.iitbombay.coursesapi.service;

import com.iitbombay.coursesapi.model.Course;
import com.iitbombay.coursesapi.model.CourseDelivery;
import com.iitbombay.coursesapi.repository.CourseDeliveryRepository;
import com.iitbombay.coursesapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseDeliveryService {
    
    @Autowired
    private CourseDeliveryRepository courseDeliveryRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<CourseDelivery> getAllCourseDeliveries() {
        return courseDeliveryRepository.findAllOrderByYearAndSemesterDesc();
    }
    
    public Optional<CourseDelivery> getCourseDeliveryById(Long id) {
        return courseDeliveryRepository.findById(id);
    }
    
    public List<CourseDelivery> getCourseDeliveriesByYear(Integer year) {
        return courseDeliveryRepository.findByYear(year);
    }
    
    public List<CourseDelivery> getCourseDeliveriesBySemester(Integer semester) {
        return courseDeliveryRepository.findBySemester(semester);
    }
    
    public List<CourseDelivery> getCourseDeliveriesByYearAndSemester(Integer year, Integer semester) {
        return courseDeliveryRepository.findByYearAndSemester(year, semester);
    }
    
    public List<CourseDelivery> getCourseDeliveriesByCourse(Long courseId) {
        return courseDeliveryRepository.findByCourseId(courseId);
    }
    
    public List<CourseDelivery> getCourseDeliveriesByDepartment(String department) {
        return courseDeliveryRepository.findByDepartment(department);
    }
    
    public List<CourseDelivery> getCourseDeliveriesByInstructor(String instructor) {
        return courseDeliveryRepository.findByInstructor(instructor);
    }
    
    public CourseDelivery createCourseDelivery(CourseDelivery courseDelivery) {
        // Validate that the course exists
        Course course = courseRepository.findById(courseDelivery.getCourse().getId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseDelivery.getCourse().getId()));
        
        courseDelivery.setCourse(course);
        
        // Check if this course delivery already exists for the same course, year, and semester
        List<CourseDelivery> existingDeliveries = courseDeliveryRepository.findByCourseAndYearAndSemester(
                course.getId(), courseDelivery.getYear(), courseDelivery.getSemester());
        
        if (!existingDeliveries.isEmpty()) {
            throw new IllegalArgumentException("Course delivery already exists for course " + course.getCourseCode() + 
                    " in year " + courseDelivery.getYear() + " semester " + courseDelivery.getSemester());
        }
        
        return courseDeliveryRepository.save(courseDelivery);
    }
    
    public CourseDelivery updateCourseDelivery(Long id, CourseDelivery courseDeliveryDetails) {
        CourseDelivery courseDelivery = courseDeliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course delivery not found with id: " + id));
        
        // Validate that the course exists if it's being changed
        if (courseDeliveryDetails.getCourse() != null && courseDeliveryDetails.getCourse().getId() != null) {
            Course course = courseRepository.findById(courseDeliveryDetails.getCourse().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseDeliveryDetails.getCourse().getId()));
            courseDelivery.setCourse(course);
        }
        
        // Check for duplicate course delivery if year or semester is being changed
        if (!courseDelivery.getYear().equals(courseDeliveryDetails.getYear()) || 
            !courseDelivery.getSemester().equals(courseDeliveryDetails.getSemester())) {
            
            List<CourseDelivery> existingDeliveries = courseDeliveryRepository.findByCourseAndYearAndSemester(
                    courseDelivery.getCourse().getId(), courseDeliveryDetails.getYear(), courseDeliveryDetails.getSemester());
            
            if (!existingDeliveries.isEmpty()) {
                throw new IllegalArgumentException("Course delivery already exists for course " + courseDelivery.getCourse().getCourseCode() + 
                        " in year " + courseDeliveryDetails.getYear() + " semester " + courseDeliveryDetails.getSemester());
            }
        }
        
        courseDelivery.setYear(courseDeliveryDetails.getYear());
        courseDelivery.setSemester(courseDeliveryDetails.getSemester());
        
        return courseDeliveryRepository.save(courseDelivery);
    }
    
    public boolean deleteCourseDelivery(Long id) {
        CourseDelivery courseDelivery = courseDeliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course delivery not found with id: " + id));
        
        courseDeliveryRepository.delete(courseDelivery);
        return true;
    }
    
    public List<CourseDelivery> getCurrentSemesterDeliveries(Integer currentYear, Integer currentSemester) {
        return courseDeliveryRepository.findByYearAndSemester(currentYear, currentSemester);
    }
    
    public List<CourseDelivery> getUpcomingDeliveries(Integer currentYear, Integer currentSemester) {
        // Get deliveries for current year but next semester, or next year
        List<CourseDelivery> nextSemesterDeliveries = courseDeliveryRepository.findByYearAndSemester(currentYear, currentSemester == 1 ? 2 : 1);
        List<CourseDelivery> nextYearDeliveries = courseDeliveryRepository.findByYear(currentYear + 1);
        
        nextSemesterDeliveries.addAll(nextYearDeliveries);
        return nextSemesterDeliveries;
    }
} 