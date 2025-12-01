package edu.academic.academic1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.academic.AcademicFactory;
import edu.academic.AcademicProgram;
import edu.academic.Course;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private AcademicFactory factory;
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = AcademicFactory.eINSTANCE;
        // Create academic program instance (AcademicProgram is abstract, use anonymous class)
        academicProgram = new AcademicProgram() {
            // Concrete implementation for testing
        };
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        
        // Create a course and set its course days
        Course course = factory.createCourse();
        EList<String> courseDays = new BasicEList<String>();
        courseDays.add("Monday");
        course.getCourseDays().addAll(courseDays);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Call the method with specific day "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 1
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        
        // Create first course with different days
        Course course1 = factory.createCourse();
        EList<String> courseDays1 = new BasicEList<String>();
        courseDays1.add("Tuesday");
        courseDays1.add("Thursday");
        course1.getCourseDays().addAll(courseDays1);
        
        // Create second course with different days
        Course course2 = factory.createCourse();
        EList<String> courseDays2 = new BasicEList<String>();
        courseDays2.add("Wednesday");
        courseDays2.add("Friday");
        course2.getCourseDays().addAll(courseDays2);
        
        // Add courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        
        // Call the method with specific day "Monday" (which none of the courses have)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        
        // Create five courses, all with the same day "Wednesday"
        for (int i = 0; i < 5; i++) {
            Course course = factory.createCourse();
            EList<String> courseDays = new BasicEList<String>();
            courseDays.add("Wednesday");
            course.getCourseDays().addAll(courseDays);
            academicProgram.getCourses().add(course);
        }
        
        // Call the method with specific day "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Expected Output: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        
        // Create three courses with "Friday"
        for (int i = 0; i < 3; i++) {
            Course course = factory.createCourse();
            EList<String> courseDays = new BasicEList<String>();
            courseDays.add("Friday");
            course.getCourseDays().addAll(courseDays);
            academicProgram.getCourses().add(course);
        }
        
        // Create five courses with different days (not Friday)
        for (int i = 0; i < 5; i++) {
            Course course = factory.createCourse();
            EList<String> courseDays = new BasicEList<String>();
            courseDays.add("Monday");
            courseDays.add("Wednesday");
            course.getCourseDays().addAll(courseDays);
            academicProgram.getCourses().add(course);
        }
        
        // Call the method with specific day "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Expected Output: The total number of courses returned should be 3
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        
        // Academic program is already empty from setUp()
        // No courses added to academic program
        
        // Call the method with any specific day
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}