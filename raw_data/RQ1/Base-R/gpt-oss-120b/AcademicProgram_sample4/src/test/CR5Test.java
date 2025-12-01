import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: Create a single course with initial quota of 20
        course1 = new Course();
        course1.setQuotaLimit(20);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Action: Instructor increases quota by 5
        instructor.increaseCourseQuota(course1, 5);
        
        // Verify: Remaining quota should be 25
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create a single course with initial quota of 30
        course1 = new Course();
        course1.setQuotaLimit(30);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Action: Instructor decreases quota by 8
        instructor.decreaseCourseQuota(course1, 8);
        
        // Verify: Remaining quota should be 22
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create three courses with quotas 15, 25, and 35
        course1 = new Course();
        course1.setQuotaLimit(15);
        
        course2 = new Course();
        course2.setQuotaLimit(25);
        
        course3 = new Course();
        course3.setQuotaLimit(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: No quota changes made
        
        // Verify: Remaining quota should be 15 + 25 + 35 = 75
        assertEquals("Remaining quota should be sum of all course quotas: 75", 75, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create two courses with quotas 20 and 25
        course1 = new Course();
        course1.setQuotaLimit(20);
        
        course2 = new Course();
        course2.setQuotaLimit(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Action: Increase quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Verify: Remaining quota should be 30 + 25 = 55
        assertEquals("Remaining quota should be 30 + 25 = 55", 55, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create three courses with quotas 18, 22, and 28
        course1 = new Course();
        course1.setQuotaLimit(18);
        
        course2 = new Course();
        course2.setQuotaLimit(22);
        
        course3 = new Course();
        course3.setQuotaLimit(28);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: Increase second course by 6 and decrease third course by 4
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Verify: Remaining quota should be 18 + 28 + 24 = 70
        assertEquals("Remaining quota should be 18 + 28 + 24 = 70", 70, instructor.getRemainingQuota());
    }
}