import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Create a new Instructor object before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add the course to the instructor
        instructor.addCourse(course);
        
        // Increase the quota by 5
        course.setQuota(25);
        
        // Calculate remaining quota (should be 25 since no students enrolled)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add the course to the instructor
        instructor.addCourse(course);
        
        // Decrease the quota by 8
        course.setQuota(22);
        
        // Calculate remaining quota (should be 22 since no students enrolled)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the expected output
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with initial quotas of 15, 25, and 35
        Course course1 = new Course();
        course1.setQuota(15);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        Course course3 = new Course();
        course3.setQuota(35);
        
        // Add all courses to the instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // No quota changes made - calculate remaining quota
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the expected output (15 + 25 + 35 = 75)
        assertEquals("Remaining quota should be 75 for three courses with quotas 15, 25, and 35", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas of 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add both courses to the instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase the quota of the first course by 10
        course1.setQuota(30);
        
        // Calculate remaining quota
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the expected output (30 + 25 = 55)
        assertEquals("Remaining quota should be 55 after increasing first course quota by 10", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas of 18, 22, and 28
        Course course1 = new Course();
        course1.setQuota(18);
        
        Course course2 = new Course();
        course2.setQuota(22);
        
        Course course3 = new Course();
        course3.setQuota(28);
        
        // Add all courses to the instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase the quota of the second course by 6 (22 + 6 = 28)
        course2.setQuota(28);
        
        // Decrease the quota of the third course by 4 (28 - 4 = 24)
        course3.setQuota(24);
        
        // Calculate remaining quota
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the expected output (18 + 28 + 24 = 70)
        assertEquals("Remaining quota should be 70 after quota adjustments", 70, result);
    }
}