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
        // Create a single course with initial quota of 20
        course1 = new Course();
        course1.setQuotaLimit(20);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Instructor increases quota by 5
        instructor.increaseQuota(course1, 5);
        
        // Calculate remaining quota (should be 25 since no students enrolled)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        course1 = new Course();
        course1.setQuotaLimit(30);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Instructor decreases quota by 8
        instructor.decreaseQuota(course1, 8);
        
        // Calculate remaining quota (should be 22 since no students enrolled)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with initial quotas of 15, 25, and 35
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
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75 since no students enrolled)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 75
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas of 20 and 25
        course1 = new Course();
        course1.setQuotaLimit(20);
        
        course2 = new Course();
        course2.setQuotaLimit(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Instructor increases quota of first course by 10
        instructor.increaseQuota(course1, 10);
        
        // Calculate remaining quota (should be 30 + 25 = 55 since no students enrolled)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 55
        assertEquals("Remaining quota should be 55 after increasing first course quota by 10", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas of 18, 22, and 28
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
        
        // Instructor increases quota of second course by 6 and decreases quota of third course by 4
        instructor.increaseQuota(course2, 6);
        instructor.decreaseQuota(course3, 4);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70 since no students enrolled)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify the remaining quota is 70
        assertEquals("Remaining quota should be 70 after quota adjustments", 70, remainingQuota);
    }
}