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
        // Initialize instructor and courses before each test
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() throws Exception {
        // Test Case 1: "SingleCourseWithSingleQuotaIncrease"
        // Input: An Instructor object with a single course having an initial quota of 20. 
        // The instructor increases the quota of this course by 5.
        
        // Setup
        course1.setQuotaLimit(20);
        instructor.addCourse(course1);
        instructor.increaseCourseQuota(course1, 5);
        
        // Execute
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify
        assertEquals("The remaining quota of the instructor's single course should be 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() throws Exception {
        // Test Case 2: "SingleCourseWithSingleQuotaDecrease"
        // Input: An Instructor object with a single course having an initial quota of 30. 
        // The instructor decreases the quota of this course by 8.
        
        // Setup
        course1.setQuotaLimit(30);
        instructor.addCourse(course1);
        instructor.decreaseCourseQuota(course1, 8);
        
        // Execute
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify
        assertEquals("The remaining quota of the instructor's single course should be 22", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() throws Exception {
        // Test Case 3: "MultipleCoursesWithNoQuotaChanges"
        // Input: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively. 
        // There are no quota changes made.
        
        // Setup
        course1.setQuotaLimit(15);
        course2.setQuotaLimit(25);
        course3.setQuotaLimit(35);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Execute
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify
        assertEquals("The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() throws Exception {
        // Test Case 4: "MultipleCoursesWithQuotaIncreaseOnOneCourse"
        // Input: An Instructor object with two courses having initial quotas of 20 and 25. 
        // The instructor increases the quota of the first course by 10.
        
        // Setup
        course1.setQuotaLimit(20);
        course2.setQuotaLimit(25);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        instructor.increaseCourseQuota(course1, 10);
        
        // Execute
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify
        assertEquals("The remaining quotas of the instructor's courses should be 30 + 25 = 55", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() throws Exception {
        // Test Case 5: "MultipleCoursesWithQuotaIncreaseAndDecrease"
        // Input: An Instructor object with three courses having initial quotas of 18, 22, and 28. 
        // The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4.
        
        // Setup
        course1.setQuotaLimit(18);
        course2.setQuotaLimit(22);
        course3.setQuotaLimit(28);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Execute
        int result = instructor.getRemainingQuotaInCourses();
        
        // Verify
        assertEquals("The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70", 70, result);
    }
}