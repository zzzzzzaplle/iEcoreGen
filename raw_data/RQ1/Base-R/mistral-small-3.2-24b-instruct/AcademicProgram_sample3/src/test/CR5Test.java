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
        // Initialize common objects for tests
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Input: An Instructor object with a single course having an initial quota of 20. 
        // The instructor increases the quota of this course by 5.
        
        // Setup
        course1.setQuota(20);
        instructor.addCourse(course1);
        
        // Action: Increase quota by 5
        course1.increaseQuota(5);
        
        // Expected Output: The remaining quota of the instructor's single course should be 25.
        int expectedRemainingQuota = 25;
        int actualRemainingQuota = instructor.getRemainingQuota();
        
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Input: An Instructor object with a single course having an initial quota of 30. 
        // The instructor decreases the quota of this course by 8.
        
        // Setup
        course1.setQuota(30);
        instructor.addCourse(course1);
        
        // Action: Decrease quota by 8
        course1.decreaseQuota(8);
        
        // Expected Output: The remaining quota of the instructor's single course should be 22.
        int expectedRemainingQuota = 22;
        int actualRemainingQuota = instructor.getRemainingQuota();
        
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Input: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively. 
        // There are no quota changes made.
        
        // Setup
        course1.setQuota(15);
        course2.setQuota(25);
        course3.setQuota(35);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // No quota changes made
        
        // Expected Output: The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75.
        int expectedRemainingQuota = 75;
        int actualRemainingQuota = instructor.getRemainingQuota();
        
        assertEquals("Remaining quota should be 75 (15+25+35) with no quota changes", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Input: An Instructor object with two courses having initial quotas of 20 and 25. 
        // The instructor increases the quota of the first course by 10.
        
        // Setup
        course1.setQuota(20);
        course2.setQuota(25);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Action: Increase quota of first course by 10
        course1.increaseQuota(10);
        
        // Expected Output: The remaining quotas of the instructor's courses should be 30 + 25 = 55.
        int expectedRemainingQuota = 55;
        int actualRemainingQuota = instructor.getRemainingQuota();
        
        assertEquals("Remaining quota should be 55 (30+25) after increasing first course quota by 10", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Input: An Instructor object with three courses having initial quotas of 18, 22, and 28. 
        // The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4.
        
        // Setup
        course1.setQuota(18);
        course2.setQuota(22);
        course3.setQuota(28);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: Increase quota of second course by 6 and decrease quota of third course by 4
        course2.increaseQuota(6);
        course3.decreaseQuota(4);
        
        // Expected Output: The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70.
        int expectedRemainingQuota = 70;
        int actualRemainingQuota = instructor.getRemainingQuota();
        
        assertEquals("Remaining quota should be 70 (18+28+24) after quota changes", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
}