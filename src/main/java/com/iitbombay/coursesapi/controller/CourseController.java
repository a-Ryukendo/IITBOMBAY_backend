package com.iitbombay.coursesapi.controller;

import com.iitbombay.coursesapi.model.Course;
import com.iitbombay.coursesapi.model.CourseType;
import com.iitbombay.coursesapi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{courseCode}")
    public ResponseEntity<Course> getCourseByCode(@PathVariable String courseCode) {
        Optional<Course> course = courseService.getCourseByCode(courseCode);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<Course>> getCoursesByDepartment(@PathVariable String department) {
        List<Course> courses = courseService.getCoursesByDepartment(department);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/instructor/{instructor}")
    public ResponseEntity<List<Course>> getCoursesByInstructor(@PathVariable String instructor) {
        List<Course> courses = courseService.getCoursesByInstructor(instructor);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<Course>> getCoursesBySemester(@PathVariable Integer semester) {
        List<Course> courses = courseService.getCoursesBySemester(semester);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/type/{courseType}")
    public ResponseEntity<List<Course>> getCoursesByType(@PathVariable CourseType courseType) {
        List<Course> courses = courseService.getCoursesByType(courseType);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourses(@RequestParam String keyword) {
        List<Course> courses = courseService.searchCoursesByKeyword(keyword);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/no-prerequisites")
    public ResponseEntity<List<Course>> getCoursesWithoutPrerequisites() {
        List<Course> courses = courseService.getCoursesWithoutPrerequisites();
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Course>> getAvailableCourses() {
        List<Course> courses = courseService.getAvailableCourses();
        return ResponseEntity.ok(courses);
    }
    
    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody Course course) {
        try {
            Course createdCourse = courseService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody Course courseDetails) {
        try {
            Course updatedCourse = courseService.updateCourse(id, courseDetails);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (javax.persistence.EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            boolean deleted = courseService.deleteCourse(id);
            if (deleted) {
                Map<String, String> message = new HashMap<>();
                message.put("message", "Course deleted successfully");
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (javax.persistence.EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}/prerequisite-for")
    public ResponseEntity<List<Course>> getCoursesByPrerequisite(@PathVariable Long id) {
        List<Course> courses = courseService.getCoursesByPrerequisite(id);
        return ResponseEntity.ok(courses);
    }
    
    @GetMapping("/{id}/is-prerequisite")
    public ResponseEntity<Map<String, Boolean>> isPrerequisiteForOtherCourses(@PathVariable Long id) {
        boolean isPrerequisite = courseService.isPrerequisiteForOtherCourses(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isPrerequisite", isPrerequisite);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{id}/enroll")
    public ResponseEntity<?> enrollStudent(@PathVariable Long id) {
        try {
            courseService.enrollStudent(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Student enrolled successfully");
            return ResponseEntity.ok(message);
        } catch (IllegalStateException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (javax.persistence.EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{id}/unenroll")
    public ResponseEntity<?> unenrollStudent(@PathVariable Long id) {
        try {
            courseService.unenrollStudent(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "Student unenrolled successfully");
            return ResponseEntity.ok(message);
        } catch (javax.persistence.EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 