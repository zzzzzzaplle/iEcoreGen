import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    
    @Before
    public void setUp() {
        // Initialize common objects for tests
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: An Instructor object with a single course having an initial quota of 20
        course1.setQuotaLimit(20);
        instructor.addCourse(course1);
        
        // Action: The instructor increases the quota of this course by 5
        instructor.increaseQuotaLimit(course1, 5);
        
        // Verify: The remaining quota of the instructor's single course should be 25
        int expectedRemainingQuota = 25;
        int actualRemainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota after increasing quota by 5 should be 25", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: An Instructor object with a single course having an initial quota of 30
        course1.setQuotaLimit(30);
        instructor.addCourse(course1);
        
        // Action: The instructor decreases the quota of this course by 8
        instructor.decreaseQuotaLimit(course1, 8);
        
        // Verify: The remaining quota of the instructor's single course should be 22
        int expectedRemainingQuota = 22;
        int actualRemainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota after decreasing quota by 8 should be 22", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively
        course1.setQuotaLimit(15);
        course2.setQuotaLimit(25);
        course3.setQuotaLimit(35);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: There are no quota changes made (no operations performed)
        
        // Verify: The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75
        int expectedRemainingQuota = 75;
        int actualRemainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota with three courses (15, 25, 35) should be 75", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: An Instructor object with two courses having initial quotas of 20 and 25
        course1.setQuotaLimit(20);
        course2.setQuotaLimit(25);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Action: The instructor increases the quota of the first course by 10
        instructor.increaseQuotaLimit(course1, 10);
        
        // Verify: The remaining quotas of the instructor's courses should be 30 + 25 = 55
        int expectedRemainingQuota = 55;
        int actualRemainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota after increasing first course quota by 10 should be 55", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: An Instructor object with three courses having initial quotas of 18, 22, and 28
        course1.setQuotaLimit(18);
        course2.setQuotaLimit(22);
        course3.setQuotaLimit(28);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4
        instructor.increaseQuotaLimit(course2, 6);
        instructor.decreaseQuotaLimit(course3, 4);
        
        // Verify: The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70
        int expectedRemainingQuota = 70;
        int actualRemainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota after quota adjustments should be 70", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
}