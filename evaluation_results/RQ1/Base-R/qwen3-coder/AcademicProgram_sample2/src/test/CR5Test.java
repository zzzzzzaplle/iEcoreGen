import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuotaLimit(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor increases quota by 5
        instructor.increaseQuota(course, 5);
        
        // Calculate remaining quota (should be 25)
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuotaLimit(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor decreases quota by 8
        instructor.decreaseQuota(course, 8);
        
        // Calculate remaining quota (should be 22)
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing from 30 by 8", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuotaLimit(15);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        Course course3 = new Course();
        course3.setQuotaLimit(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75)
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 75
        assertEquals("Remaining quota should be 75 (15+25+35) with no quota changes", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuotaLimit(20);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Instructor increases quota of first course by 10
        instructor.increaseQuota(course1, 10);
        
        // Calculate remaining quota (should be 30 + 25 = 55)
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 55
        assertEquals("Remaining quota should be 55 (30+25) after increasing first course by 10", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuotaLimit(18);
        
        Course course2 = new Course();
        course2.setQuotaLimit(22);
        
        Course course3 = new Course();
        course3.setQuotaLimit(28);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Instructor increases quota of second course by 6
        instructor.increaseQuota(course2, 6);
        
        // Instructor decreases quota of third course by 4
        instructor.decreaseQuota(course3, 4);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 70
        assertEquals("Remaining quota should be 70 (18+28+24) after quota changes", 70, result);
    }
}