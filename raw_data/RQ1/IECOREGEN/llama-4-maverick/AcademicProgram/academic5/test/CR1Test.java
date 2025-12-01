package edu.academic.academic5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.academic.AcademicFactory;
import edu.academic.AcademicProgram;
import edu.academic.Course;
import edu.academic.Classroom;

public class CR1Test {
    
    private AcademicFactory factory;
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create academic program
        factory = AcademicFactory.eINSTANCE;
        academicProgram = new AcademicProgram() {};
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        // Expected Output: "The total classroom capacity in the academic program is 30"
        
        // Create classroom with capacity 30
        Classroom classroom = factory.createClassroom();
        classroom.setCapacity(30);
        
        // Create course and assign classroom
        Course course = factory.createCourse();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify expected output
        assertEquals("Total classroom capacity should be 30", 30, totalCapacity);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create first classroom with capacity 25
        Classroom classroom1 = factory.createClassroom();
        classroom1.setCapacity(25);
        
        // Create second classroom with capacity 40
        Classroom classroom2 = factory.createClassroom();
        classroom2.setCapacity(40);
        
        // Create third classroom with capacity 35
        Classroom classroom3 = factory.createClassroom();
        classroom3.setCapacity(35);
        
        // Create courses and assign classrooms
        Course course1 = factory.createCourse();
        course1.setClassroom(classroom1);
        
        Course course2 = factory.createCourse();
        course2.setClassroom(classroom2);
        
        Course course3 = factory.createCourse();
        course3.setClassroom(classroom3);
        
        // Add courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify expected output
        assertEquals("Total classroom capacity should be 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create classroom with capacity 50
        Classroom classroom = factory.createClassroom();
        classroom.setCapacity(50);
        
        // Create two courses using the same classroom
        Course course1 = factory.createCourse();
        course1.setClassroom(classroom);
        
        Course course2 = factory.createCourse();
        course2.setClassroom(classroom);
        
        // Add courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify expected output
        assertEquals("Total classroom capacity should be 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // Expected Output: "The total classroom capacity in the academic program is 0"
        
        // Academic program is already empty from setup
        // No courses added
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify expected output
        assertEquals("Total classroom capacity should be 0 for empty program", 0, totalCapacity);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        // Expected Output: "The total classroom capacity in the academic program is 130"
        
        // Create classrooms with specified capacities
        Classroom classroom1 = factory.createClassroom();
        classroom1.setCapacity(15);
        
        Classroom classroom2 = factory.createClassroom();
        classroom2.setCapacity(20);
        
        Classroom classroom3 = factory.createClassroom();
        classroom3.setCapacity(25);
        
        Classroom classroom4 = factory.createClassroom();
        classroom4.setCapacity(30);
        
        Classroom classroom5 = factory.createClassroom();
        classroom5.setCapacity(40);
        
        // Create courses and assign classrooms
        Course course1 = factory.createCourse();
        course1.setClassroom(classroom1);
        
        Course course2 = factory.createCourse();
        course2.setClassroom(classroom2);
        
        Course course3 = factory.createCourse();
        course3.setClassroom(classroom3);
        
        Course course4 = factory.createCourse();
        course4.setClassroom(classroom4);
        
        Course course5 = factory.createCourse();
        course5.setClassroom(classroom5);
        
        // Add courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        academicProgram.getCourses().add(course4);
        academicProgram.getCourses().add(course5);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify expected output
        assertEquals("Total classroom capacity should be 130", 130, totalCapacity);
    }
}