import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class CR5Test {
    
    private Instructor instructor;
    private Course course1, course2, course3;
    
    @Before
    public void setUp() {
        // Initialize instructor and courses before each test
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Test Case 1: Single course with quota increase
        // Setup: Instructor with single course having initial quota of 20
        course1.setQuotaLimit(20);
        instructor.addCourse(course1);
        
        // Action: Increase quota by 5
        instructor.increaseCourseQuota(course1, 5);
        
        // Verify: Remaining quota should be 25
        int expected = 25;
        int actual = instructor.getTotalRemainingQuota();
        assertEquals("Remaining quota after increase should be 25", expected, actual);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Test Case 2: Single course with quota decrease
        // Setup: Instructor with single course having initial quota of 30
        course1.setQuotaLimit(30);
        instructor.addCourse(course1);
        
        // Action: Decrease quota by 8
        instructor.decreaseCourseQuota(course1, 8);
        
        // Verify: Remaining quota should be 22
        int expected = 22;
        int actual = instructor.getTotalRemainingQuota();
        assertEquals("Remaining quota after decrease should be 22", expected, actual);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Test Case 3: Multiple courses with no quota changes
        // Setup: Instructor with three courses having quotas 15, 25, and 35
        course1.setQuotaLimit(15);
        course2.setQuotaLimit(25);
        course3.setQuotaLimit(35);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // No quota changes made
        
        // Verify: Total remaining quota should be 15 + 25 + 35 = 75
        int expected = 75;
        int actual = instructor.getTotalRemainingQuota();
        assertEquals("Total remaining quota should be 75", expected, actual);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Test Case 4: Multiple courses with quota increase on one course
        // Setup: Instructor with two courses having quotas 20 and 25
        course1.setQuotaLimit(20);
        course2.setQuotaLimit(25);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Action: Increase quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Verify: Total remaining quota should be 30 + 25 = 55
        int expected = 55;
        int actual = instructor.getTotalRemainingQuota();
        assertEquals("Total remaining quota should be 55", expected, actual);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Test Case 5: Multiple courses with quota increase and decrease
        // Setup: Instructor with three courses having quotas 18, 22, and 28
        course1.setQuotaLimit(18);
        course2.setQuotaLimit(22);
        course3.setQuotaLimit(28);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: Increase second course by 6 and decrease third course by 4
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Verify: Total remaining quota should be 18 + 28 + 24 = 70
        int expected = 70;
        int actual = instructor.getTotalRemainingQuota();
        assertEquals("Total remaining quota should be 70", expected, actual);
    }
}