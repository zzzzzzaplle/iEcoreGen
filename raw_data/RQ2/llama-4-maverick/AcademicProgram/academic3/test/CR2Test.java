package edu.academic.academic3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.academic.AcademicFactory;
import edu.academic.Instructor;
import edu.academic.Course;
import edu.academic.Student;
import edu.academic.Enrollment;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private AcademicFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = AcademicFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create instructor using factory pattern
        Instructor instructor = factory.createInstructor();
        
        // Create course using factory pattern
        Course course = factory.createCourse();
        course.setCode("CS101");
        
        // Create student using factory pattern
        Student student = factory.createStudent();
        
        // Create enrollment using factory pattern
        Enrollment enrollment = factory.createEnrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        // Add enrollment to course
        course.getEnrollments().add(enrollment);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create instructor using factory pattern
        Instructor instructor = factory.createInstructor();
        
        // Create course using factory pattern
        Course course = factory.createCourse();
        course.setCode("MATH201");
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = factory.createStudent();
            Enrollment enrollment = factory.createEnrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
        }
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Create instructor using factory pattern
        Instructor instructor = factory.createInstructor();
        
        // Create 3 courses with one student each
        for (int i = 1; i <= 3; i++) {
            Course course = factory.createCourse();
            course.setCode("COURSE" + i);
            
            Student student = factory.createStudent();
            Enrollment enrollment = factory.createEnrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
            
            instructor.getCourses().add(course);
        }
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Create instructor using factory pattern
        Instructor instructor = factory.createInstructor();
        
        // Course enrollment counts as specified
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying student counts
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = factory.createCourse();
            course.setCode("COURSE" + (i + 1));
            
            // Add specified number of students to the course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = factory.createStudent();
                Enrollment enrollment = factory.createEnrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                course.getEnrollments().add(enrollment);
            }
            
            instructor.getCourses().add(course);
        }
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        
        // Create instructor using factory pattern (no courses added)
        Instructor instructor = factory.createInstructor();
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}