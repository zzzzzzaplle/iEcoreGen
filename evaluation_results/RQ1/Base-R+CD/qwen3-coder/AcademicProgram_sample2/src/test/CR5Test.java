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
        
        // Calculate remaining quota (should be 25)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 25, remainingQuota);
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
        
        // Calculate remaining quota (should be 22)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 22 after decreasing from 30 by 8", 22, remainingQuota);
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
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas of 20 and 25
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
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 55 after increasing first course from 20 to 30", 55, remainingQuota);
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
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Decrease quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 70 after operations: 18 + (22+6) + (28-4)", 70, remainingQuota);
    }
}