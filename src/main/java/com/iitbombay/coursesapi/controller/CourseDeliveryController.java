package com.iitbombay.coursesapi.controller;

import com.iitbombay.coursesapi.model.CourseDelivery;
import com.iitbombay.coursesapi.service.CourseDeliveryService;
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
@RequestMapping("/api/instances")
@CrossOrigin(origins = "*")
public class CourseDeliveryController {
    
    @Autowired
    private CourseDeliveryService courseDeliveryService;
    
    @GetMapping
    public ResponseEntity<List<CourseDelivery>> getAllCourseDeliveries() {
        List<CourseDelivery> deliveries = courseDeliveryService.getAllCourseDeliveries();
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CourseDelivery> getCourseDeliveryById(@PathVariable Long id) {
        Optional<CourseDelivery> delivery = courseDeliveryService.getCourseDeliveryById(id);
        return delivery.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/year/{year}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesByYear(@PathVariable Integer year) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesByYear(year);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesBySemester(@PathVariable Integer semester) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesBySemester(semester);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/year/{year}/semester/{semester}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesByYearAndSemester(
            @PathVariable Integer year, @PathVariable Integer semester) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesByYearAndSemester(year, semester);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesByCourse(@PathVariable Long courseId) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesByCourse(courseId);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesByDepartment(@PathVariable String department) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesByDepartment(department);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/instructor/{instructor}")
    public ResponseEntity<List<CourseDelivery>> getCourseDeliveriesByInstructor(@PathVariable String instructor) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCourseDeliveriesByInstructor(instructor);
        return ResponseEntity.ok(deliveries);
    }
    
    @PostMapping
    public ResponseEntity<?> createCourseDelivery(@Valid @RequestBody CourseDelivery courseDelivery) {
        try {
            CourseDelivery createdDelivery = courseDeliveryService.createCourseDelivery(courseDelivery);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDelivery);
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
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourseDelivery(@PathVariable Long id, @Valid @RequestBody CourseDelivery deliveryDetails) {
        try {
            CourseDelivery updatedDelivery = courseDeliveryService.updateCourseDelivery(id, deliveryDetails);
            return ResponseEntity.ok(updatedDelivery);
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
    public ResponseEntity<?> deleteCourseDelivery(@PathVariable Long id) {
        try {
            boolean deleted = courseDeliveryService.deleteCourseDelivery(id);
            if (deleted) {
                Map<String, String> message = new HashMap<>();
                message.put("message", "Course delivery deleted successfully");
                return ResponseEntity.ok(message);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (javax.persistence.EntityNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/current/{year}/{semester}")
    public ResponseEntity<List<CourseDelivery>> getCurrentSemesterDeliveries(
            @PathVariable Integer year, @PathVariable Integer semester) {
        List<CourseDelivery> deliveries = courseDeliveryService.getCurrentSemesterDeliveries(year, semester);
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/upcoming/{year}/{semester}")
    public ResponseEntity<List<CourseDelivery>> getUpcomingDeliveries(
            @PathVariable Integer year, @PathVariable Integer semester) {
        List<CourseDelivery> deliveries = courseDeliveryService.getUpcomingDeliveries(year, semester);
        return ResponseEntity.ok(deliveries);
    }
} 