import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Test Case 1: SingleCourseWithSingleQuotaIncrease
        // Input: An Instructor object with a single course having an initial quota of 20. 
        // The instructor increases the quota of this course by 5.
        // Expected Output: The remaining quota of the instructor's single course should be 25.
        
        // Create course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota (should be 25 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Test Case 2: SingleCourseWithSingleQuotaDecrease
        // Input: An Instructor object with a single course having an initial quota of 30.
        // The instructor decreases the quota of this course by 8.
        // Expected Output: The remaining quota of the instructor's single course should be 22.
        
        // Create course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota (should be 22 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Test Case 3: MultipleCoursesWithNoQuotaChanges
        // Input: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively.
        // There are no quota changes made.
        // Expected Output: The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75.
        
        // Create three courses with specified quotas
        Course course1 = new Course();
        course1.setQuota(15);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        Course course3 = new Course();
        course3.setQuota(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Calculate remaining quota (should be 75 since no enrollments and no quota changes)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 75 (15+25+35) with no quota changes", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Test Case 4: MultipleCoursesWithQuotaIncreaseOnOneCourse
        // Input: An Instructor object with two courses having initial quotas of 20 and 25.
        // The instructor increases the quota of the first course by 10.
        // Expected Output: The remaining quotas of the instructor's courses should be 30 + 25 = 55.
        
        // Create two courses with specified quotas
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota (should be 55 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 55 (30+25) after increasing first course quota by 10", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Test Case 5: MultipleCoursesWithQuotaIncreaseAndDecrease
        // Input: An Instructor object with three courses having initial quotas of 18, 22, and 28.
        // The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4.
        // Expected Output: The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70.
        
        // Create three courses with specified quotas
        Course course1 = new Course();
        course1.setQuota(18);
        
        Course course2 = new Course();
        course2.setQuota(22);
        
        Course course3 = new Course();
        course3.setQuota(28);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Decrease quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota (should be 70 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 70 (18+28+24) after quota adjustments", 70, remainingQuota);
    }
}