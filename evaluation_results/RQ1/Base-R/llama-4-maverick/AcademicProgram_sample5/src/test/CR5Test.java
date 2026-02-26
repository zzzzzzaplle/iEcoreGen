import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Instructor increases the quota by 5
        course.setQuota(25);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Calculate remaining quota (should be 25)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the result
        assertEquals("Remaining quota for single course with increased quota should be 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Instructor decreases the quota by 8
        course.setQuota(22);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Calculate remaining quota (should be 22)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the result
        assertEquals("Remaining quota for single course with decreased quota should be 22", 22, result);
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
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the result
        assertEquals("Remaining quota for multiple courses with no quota changes should be 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Instructor increases quota of first course by 10
        course1.setQuota(30);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Calculate remaining quota (should be 30 + 25 = 55)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the result
        assertEquals("Remaining quota for multiple courses with quota increase on one course should be 55", 55, result);
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
        
        // Instructor increases quota of second course by 6 and decreases third course by 4
        course2.setQuota(28); // 22 + 6 = 28
        course3.setQuota(24); // 28 - 4 = 24
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int result = instructor.remainingQuotaInCourses();
        
        // Verify the result
        assertEquals("Remaining quota for multiple courses with quota increase and decrease should be 70", 70, result);
    }
}