package com.iitbombay.coursesapi.config;

import com.iitbombay.coursesapi.model.Course;
import com.iitbombay.coursesapi.model.CourseDelivery;
import com.iitbombay.coursesapi.model.CourseType;
import com.iitbombay.coursesapi.repository.CourseRepository;
import com.iitbombay.coursesapi.repository.CourseDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private CourseDeliveryRepository courseDeliveryRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Clear existing data
        courseDeliveryRepository.deleteAll();
        courseRepository.deleteAll();
        
        // Create sample courses
        Course cs101 = new Course("CS101", "Introduction to Computer Science", "Computer Science", 
                "Dr. John Smith", 3, 1, "Basic concepts of computer science and programming", 50, CourseType.CORE);
        
        Course cs201 = new Course("CS201", "Data Structures and Algorithms", "Computer Science", 
                "Dr. Jane Doe", 4, 2, "Advanced data structures and algorithm analysis", 40, CourseType.CORE);
        
        Course cs301 = new Course("CS301", "Database Systems", "Computer Science", 
                "Dr. Mike Johnson", 3, 3, "Database design and management", 35, CourseType.CORE);
        
        Course cs401 = new Course("CS401", "Software Engineering", "Computer Science", 
                "Dr. Sarah Wilson", 4, 4, "Software development methodologies and practices", 30, CourseType.CORE);
        
        Course cs501 = new Course("CS501", "Machine Learning", "Computer Science", 
                "Dr. Alex Brown", 3, 5, "Introduction to machine learning algorithms", 25, CourseType.ELECTIVE);
        
        Course math101 = new Course("MATH101", "Calculus I", "Mathematics", 
                "Dr. Robert Lee", 4, 1, "Differential and integral calculus", 60, CourseType.CORE);
        
        Course math201 = new Course("MATH201", "Linear Algebra", "Mathematics", 
                "Dr. Emily Chen", 3, 2, "Vector spaces and linear transformations", 45, CourseType.CORE);
        
        Course phys101 = new Course("PHYS101", "Physics I", "Physics", 
                "Dr. David Kim", 4, 1, "Classical mechanics and thermodynamics", 55, CourseType.CORE);
        
        Course eng101 = new Course("ENG101", "Technical Writing", "English", 
                "Dr. Lisa Garcia", 2, 1, "Technical communication and documentation", 40, CourseType.CORE);
        
        Course lab101 = new Course("LAB101", "Programming Lab", "Computer Science", 
                "Dr. Tom Anderson", 1, 1, "Hands-on programming exercises", 30, CourseType.LABORATORY);
        
        // Save courses first
        courseRepository.saveAll(Arrays.asList(cs101, cs201, cs301, cs401, cs501, math101, math201, phys101, eng101, lab101));
        
        // Set up prerequisites
        cs201.addPrerequisite(cs101);
        cs301.addPrerequisite(cs201);
        cs401.addPrerequisite(cs301);
        cs501.addPrerequisite(cs201);
        cs501.addPrerequisite(math201);
        math201.addPrerequisite(math101);
        
        // Save courses with prerequisites
        courseRepository.saveAll(Arrays.asList(cs201, cs301, cs401, cs501, math201));
        
        // Create course deliveries
        CourseDelivery delivery1 = new CourseDelivery(2024, 1, cs101);
        CourseDelivery delivery2 = new CourseDelivery(2024, 1, math101);
        CourseDelivery delivery3 = new CourseDelivery(2024, 1, phys101);
        CourseDelivery delivery4 = new CourseDelivery(2024, 1, eng101);
        CourseDelivery delivery5 = new CourseDelivery(2024, 1, lab101);
        
        CourseDelivery delivery6 = new CourseDelivery(2024, 2, cs201);
        CourseDelivery delivery7 = new CourseDelivery(2024, 2, math201);
        
        CourseDelivery delivery8 = new CourseDelivery(2023, 1, cs101);
        CourseDelivery delivery9 = new CourseDelivery(2023, 2, cs201);
        CourseDelivery delivery10 = new CourseDelivery(2023, 2, math201);
        
        CourseDelivery delivery11 = new CourseDelivery(2025, 1, cs301);
        CourseDelivery delivery12 = new CourseDelivery(2025, 1, cs401);
        CourseDelivery delivery13 = new CourseDelivery(2025, 2, cs501);
        
        CourseDelivery delivery2025 = new CourseDelivery(2025, 1, cs101);
        
        // Additional course deliveries for more years/semesters (only semesters 1 or 2)
        CourseDelivery delivery14 = new CourseDelivery(2022, 1, cs101);
        CourseDelivery delivery15 = new CourseDelivery(2022, 2, cs201);
        CourseDelivery delivery16 = new CourseDelivery(2022, 1, cs301);
        CourseDelivery delivery17 = new CourseDelivery(2022, 2, cs401);
        CourseDelivery delivery18 = new CourseDelivery(2023, 1, cs301);
        CourseDelivery delivery19 = new CourseDelivery(2023, 2, cs401);
        CourseDelivery delivery20 = new CourseDelivery(2024, 1, cs301);
        CourseDelivery delivery21 = new CourseDelivery(2024, 2, cs401);
        CourseDelivery delivery22 = new CourseDelivery(2025, 1, cs301);
        CourseDelivery delivery23 = new CourseDelivery(2025, 2, cs401);
        CourseDelivery delivery24 = new CourseDelivery(2025, 1, math101);
        CourseDelivery delivery25 = new CourseDelivery(2025, 2, math201);
        CourseDelivery delivery26 = new CourseDelivery(2025, 1, phys101);
        CourseDelivery delivery27 = new CourseDelivery(2025, 2, eng101);
        CourseDelivery delivery28 = new CourseDelivery(2025, 2, lab101);
        
        // Save all deliveries
        courseDeliveryRepository.saveAll(Arrays.asList(
                delivery1, delivery2, delivery3, delivery4, delivery5,
                delivery6, delivery7, delivery8, delivery9, delivery10,
                delivery11, delivery12, delivery13, delivery2025,
                delivery14, delivery15, delivery16, delivery17, delivery18, delivery19, delivery20, delivery21,
                delivery22, delivery23, delivery24, delivery25, delivery26, delivery27, delivery28
        ));
        
        // Add more course deliveries to cover all semesters (1-8) for each year (2022-2025)
        for (int year = 2022; year <= 2025; year++) {
            courseDeliveryRepository.save(new CourseDelivery(year, 1, cs101));
            courseDeliveryRepository.save(new CourseDelivery(year, 1, math101));
            courseDeliveryRepository.save(new CourseDelivery(year, 1, phys101));
            courseDeliveryRepository.save(new CourseDelivery(year, 1, eng101));
            courseDeliveryRepository.save(new CourseDelivery(year, 1, lab101));

            courseDeliveryRepository.save(new CourseDelivery(year, 2, cs201));
            courseDeliveryRepository.save(new CourseDelivery(year, 2, math201));
            courseDeliveryRepository.save(new CourseDelivery(year, 2, cs101));
            courseDeliveryRepository.save(new CourseDelivery(year, 2, phys101));

            courseDeliveryRepository.save(new CourseDelivery(year, 3, cs301));
            courseDeliveryRepository.save(new CourseDelivery(year, 3, cs201));
            courseDeliveryRepository.save(new CourseDelivery(year, 3, math101));

            courseDeliveryRepository.save(new CourseDelivery(year, 4, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 4, cs301));
            courseDeliveryRepository.save(new CourseDelivery(year, 4, math201));

            courseDeliveryRepository.save(new CourseDelivery(year, 5, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 5, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 5, cs301));

            courseDeliveryRepository.save(new CourseDelivery(year, 6, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 6, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 6, math201));

            courseDeliveryRepository.save(new CourseDelivery(year, 7, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 7, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 7, cs301));

            courseDeliveryRepository.save(new CourseDelivery(year, 8, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 8, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 8, math201));
        }
        
        // Add more unique courses for higher semesters
        Course cs601 = new Course("CS601", "Advanced Algorithms", "Computer Science", "Dr. Alan Turing", 4, 7, "Advanced topics in algorithms", 20, CourseType.CORE);
        Course cs701 = new Course("CS701", "Distributed Systems", "Computer Science", "Dr. Leslie Lamport", 4, 8, "Principles of distributed computing", 20, CourseType.CORE);
        Course math301 = new Course("MATH301", "Abstract Algebra", "Mathematics", "Dr. Emmy Noether", 3, 7, "Group theory and ring theory", 25, CourseType.CORE);
        Course phys201 = new Course("PHYS201", "Quantum Mechanics", "Physics", "Dr. Richard Feynman", 4, 8, "Introduction to quantum mechanics", 20, CourseType.CORE);
        Course eng201 = new Course("ENG201", "Creative Writing", "English", "Dr. Maya Angelou", 2, 7, "Advanced creative writing", 15, CourseType.ELECTIVE);
        Course lab201 = new Course("LAB201", "Advanced Programming Lab", "Computer Science", "Dr. Ada Lovelace", 1, 8, "Advanced programming exercises", 15, CourseType.LABORATORY);
        courseRepository.saveAll(Arrays.asList(cs601, cs701, math301, phys201, eng201, lab201));

        // Add more course deliveries for semesters 7 and 8 and fill semesters with at least 3-4 courses
        for (int year = 2022; year <= 2025; year++) {
            // Semester 7
            courseDeliveryRepository.save(new CourseDelivery(year, 7, cs601));
            courseDeliveryRepository.save(new CourseDelivery(year, 7, math301));
            courseDeliveryRepository.save(new CourseDelivery(year, 7, eng201));
            // Add a previous course for variety
            courseDeliveryRepository.save(new CourseDelivery(year, 7, cs501));

            // Semester 8
            courseDeliveryRepository.save(new CourseDelivery(year, 8, cs701));
            courseDeliveryRepository.save(new CourseDelivery(year, 8, phys201));
            courseDeliveryRepository.save(new CourseDelivery(year, 8, lab201));
            // Add a previous course for variety
            courseDeliveryRepository.save(new CourseDelivery(year, 8, cs401));

            // Ensure at least 3-4 courses in all other semesters
            courseDeliveryRepository.save(new CourseDelivery(year, 3, cs301));
            courseDeliveryRepository.save(new CourseDelivery(year, 3, math101));
            courseDeliveryRepository.save(new CourseDelivery(year, 3, phys101));
            courseDeliveryRepository.save(new CourseDelivery(year, 3, eng101));

            courseDeliveryRepository.save(new CourseDelivery(year, 4, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 4, math201));
            courseDeliveryRepository.save(new CourseDelivery(year, 4, phys101));
            courseDeliveryRepository.save(new CourseDelivery(year, 4, eng101));

            courseDeliveryRepository.save(new CourseDelivery(year, 5, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 5, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 5, math201));
            courseDeliveryRepository.save(new CourseDelivery(year, 5, phys101));

            courseDeliveryRepository.save(new CourseDelivery(year, 6, cs501));
            courseDeliveryRepository.save(new CourseDelivery(year, 6, cs401));
            courseDeliveryRepository.save(new CourseDelivery(year, 6, math201));
            courseDeliveryRepository.save(new CourseDelivery(year, 6, eng101));
        }
        
        System.out.println("Sample data initialized successfully!");
        System.out.println("Created " + courseRepository.count() + " courses");
        System.out.println("Created " + courseDeliveryRepository.count() + " course deliveries");
    }
} 