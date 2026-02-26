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
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota (should be 25 since no enrollments)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota (should be 22 since no enrollments)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 22 after decreasing from 30 by 8", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
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
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 75 (15+25+35) with no quota changes", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota (should be 30 + 25 = 55)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 55 (30+25) after increasing first course quota by 10", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with quotas 18, 22, and 28
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
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 70 (18+28+24) after quota operations", 70, result);
    }
}