package com.iitbombay.coursesapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Course code is required")
    @Column(unique = true, nullable = false)
    private String courseCode;
    
    @NotBlank(message = "Course name is required")
    @Column(nullable = false)
    private String courseName;
    
    @NotBlank(message = "Department is required")
    private String department;
    
    @NotBlank(message = "Instructor is required")
    private String instructor;
    
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 12, message = "Credits cannot exceed 12")
    private Integer credits;
    
    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot exceed 8")
    private Integer semester;
    
    @Column(length = 1000)
    private String description;
    
    @Min(value = 1, message = "Maximum students must be at least 1")
    private Integer maxStudents;
    
    @Min(value = 0, message = "Enrolled students cannot be negative")
    private Integer enrolledStudents = 0;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "course_prerequisites",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    @JsonIgnore
    private Set<Course> prerequisites = new HashSet<>();
    
    @Enumerated(EnumType.STRING)
    private CourseType courseType;
    
    // Constructors
    public Course() {}
    
    public Course(String courseCode, String courseName, String department, String instructor, 
                  Integer credits, Integer semester, String description, Integer maxStudents, CourseType courseType) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.department = department;
        this.instructor = instructor;
        this.credits = credits;
        this.semester = semester;
        this.description = description;
        this.maxStudents = maxStudents;
        this.courseType = courseType;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public Integer getCredits() {
        return credits;
    }
    
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getMaxStudents() {
        return maxStudents;
    }
    
    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }
    
    public Integer getEnrolledStudents() {
        return enrolledStudents;
    }
    
    public void setEnrolledStudents(Integer enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    public Set<Course> getPrerequisites() {
        return prerequisites;
    }
    
    public void setPrerequisites(Set<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }
    
    public CourseType getCourseType() {
        return courseType;
    }
    
    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
    
    // Helper methods
    public void addPrerequisite(Course prerequisite) {
        this.prerequisites.add(prerequisite);
    }
    
    public void removePrerequisite(Course prerequisite) {
        this.prerequisites.remove(prerequisite);
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", department='" + department + '\'' +
                ", instructor='" + instructor + '\'' +
                ", credits=" + credits +
                ", semester=" + semester +
                ", courseType=" + courseType +
                '}';
    }
} 