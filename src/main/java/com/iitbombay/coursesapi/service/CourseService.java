package com.iitbombay.coursesapi.service;

import com.iitbombay.coursesapi.model.Course;
import com.iitbombay.coursesapi.model.CourseType;
import com.iitbombay.coursesapi.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }
    
    public Optional<Course> getCourseByCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }
    
    public List<Course> getCoursesByDepartment(String department) {
        return courseRepository.findByDepartment(department);
    }
    
    public List<Course> getCoursesByInstructor(String instructor) {
        return courseRepository.findByInstructor(instructor);
    }
    
    public List<Course> getCoursesBySemester(Integer semester) {
        return courseRepository.findBySemester(semester);
    }
    
    public List<Course> getCoursesByType(CourseType courseType) {
        return courseRepository.findByCourseType(courseType);
    }
    
    public List<Course> searchCoursesByKeyword(String keyword) {
        return courseRepository.findByKeyword(keyword);
    }
    
    public List<Course> getCoursesWithoutPrerequisites() {
        return courseRepository.findCoursesWithoutPrerequisites();
    }
    
    public List<Course> getAvailableCourses() {
        return courseRepository.findAvailableCourses();
    }
    
    public Course createCourse(Course course) {
        // Validate course code uniqueness
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            throw new IllegalArgumentException("Course code already exists: " + course.getCourseCode());
        }
        
        // Validate prerequisites exist
        validatePrerequisites(course.getPrerequisites());
        
        return courseRepository.save(course);
    }
    
    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        
        // Check if course code is being changed and if it's unique
        if (!course.getCourseCode().equals(courseDetails.getCourseCode()) && 
            courseRepository.existsByCourseCode(courseDetails.getCourseCode())) {
            throw new IllegalArgumentException("Course code already exists: " + courseDetails.getCourseCode());
        }
        
        // Validate prerequisites exist
        validatePrerequisites(courseDetails.getPrerequisites());
        
        // Update fields
        course.setCourseCode(courseDetails.getCourseCode());
        course.setCourseName(courseDetails.getCourseName());
        course.setDepartment(courseDetails.getDepartment());
        course.setInstructor(courseDetails.getInstructor());
        course.setCredits(courseDetails.getCredits());
        course.setSemester(courseDetails.getSemester());
        course.setDescription(courseDetails.getDescription());
        course.setMaxStudents(courseDetails.getMaxStudents());
        course.setEnrolledStudents(courseDetails.getEnrolledStudents());
        course.setPrerequisites(courseDetails.getPrerequisites());
        course.setCourseType(courseDetails.getCourseType());
        
        return courseRepository.save(course);
    }
    
    public boolean deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
        
        // Check if course is a prerequisite for other courses
        if (courseRepository.isPrerequisiteForOtherCourses(id)) {
            throw new IllegalStateException("Cannot delete course. It is a prerequisite for other courses.");
        }
        
        courseRepository.delete(course);
        return true;
    }
    
    public List<Course> getCoursesByPrerequisite(Long prerequisiteId) {
        return courseRepository.findCoursesByPrerequisite(prerequisiteId);
    }
    
    public boolean isPrerequisiteForOtherCourses(Long courseId) {
        return courseRepository.isPrerequisiteForOtherCourses(courseId);
    }
    
    private void validatePrerequisites(Set<Course> prerequisites) {
        if (prerequisites != null) {
            for (Course prerequisite : prerequisites) {
                if (prerequisite.getId() != null) {
                    if (!courseRepository.existsById(prerequisite.getId())) {
                        throw new IllegalArgumentException("Prerequisite course not found with id: " + prerequisite.getId());
                    }
                }
            }
        }
    }
    
    public void enrollStudent(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        
        if (course.getEnrolledStudents() >= course.getMaxStudents()) {
            throw new IllegalStateException("Course is full. Cannot enroll more students.");
        }
        
        course.setEnrolledStudents(course.getEnrolledStudents() + 1);
        courseRepository.save(course);
    }
    
    public void unenrollStudent(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + courseId));
        
        if (course.getEnrolledStudents() > 0) {
            course.setEnrolledStudents(course.getEnrolledStudents() - 1);
            courseRepository.save(course);
        }
    }
} 