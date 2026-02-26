package edu.academic.academic1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.academic.AcademicFactory;
import edu.academic.Instructor;
import edu.academic.Course;

public class CR5Test {
    
    private AcademicFactory factory;
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = AcademicFactory.eINSTANCE;
        // Create a new instructor using the factory
        instructor = factory.createInstructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a single course with initial quota of 20
        Course course = factory.createCourse();
        course.setQuota(20);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Instructor increases quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota (should be 25)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = factory.createCourse();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Instructor decreases quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota (should be 22)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with different quotas
        Course course1 = factory.createCourse();
        course1.setQuota(15);
        
        Course course2 = factory.createCourse();
        course2.setQuota(25);
        
        Course course3 = factory.createCourse();
        course3.setQuota(35);
        
        // Add all courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // No quota changes made - calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the expected output (15 + 25 + 35 = 75)
        assertEquals("Remaining quota should be 75 for three courses with no changes", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas
        Course course1 = factory.createCourse();
        course1.setQuota(20);
        
        Course course2 = factory.createCourse();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        
        // Instructor increases quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota (30 + 25 = 55)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 55 after increasing first course by 10", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas
        Course course1 = factory.createCourse();
        course1.setQuota(18);
        
        Course course2 = factory.createCourse();
        course2.setQuota(22);
        
        Course course3 = factory.createCourse();
        course3.setQuota(28);
        
        // Add all courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Instructor increases quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Instructor decreases quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota (18 + 28 + 24 = 70)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 70 after mixed quota changes", 70, remainingQuota);
    }
}