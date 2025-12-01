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
        
        // Add the course to instructor's courses
        instructor.getCourses().add(course);
        
        // Increase the quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota and verify it's 25
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add the course to instructor's courses
        instructor.getCourses().add(course);
        
        // Decrease the quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota and verify it's 22
        int result = instructor.calculateRemainingQuota();
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
        
        // Add all courses to instructor's courses
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Calculate remaining quota and verify it's 75 (15 + 25 + 35)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add both courses to instructor's courses
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        
        // Increase the quota of the first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota and verify it's 55 (30 + 25)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 55 after increasing first course from 20 to 30", 55, result);
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
        
        // Add all courses to instructor's courses
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Increase the quota of the second course by 6 and decrease the third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota and verify it's 70 (18 + 28 + 24)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 70 after increasing second course from 22 to 28 and decreasing third course from 28 to 24", 70, result);
    }
}