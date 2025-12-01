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
        // Test Case 1: Single course with quota increase
        Course course = new Course();
        course.setQuota(20);
        course.setQuota(25); // Increase quota by 5
        instructor.addCourse(course);
        
        // Calculate remaining quota (should be 25 since no students enrolled)
        int result = instructor.remainingQuotaInCourses();
        assertEquals("Remaining quota should be 25 for single course with increased quota", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Test Case 2: Single course with quota decrease
        Course course = new Course();
        course.setQuota(30);
        course.setQuota(22); // Decrease quota by 8
        instructor.addCourse(course);
        
        // Calculate remaining quota (should be 22 since no students enrolled)
        int result = instructor.remainingQuotaInCourses();
        assertEquals("Remaining quota should be 22 for single course with decreased quota", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Test Case 3: Multiple courses with no quota changes
        Course course1 = new Course();
        course1.setQuota(15);
        instructor.addCourse(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        instructor.addCourse(course2);
        
        Course course3 = new Course();
        course3.setQuota(35);
        instructor.addCourse(course3);
        
        // Calculate remaining quota (15 + 25 + 35 = 75)
        int result = instructor.remainingQuotaInCourses();
        assertEquals("Remaining quota should be 75 for three courses with no quota changes", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Test Case 4: Multiple courses with quota increase on one course
        Course course1 = new Course();
        course1.setQuota(20);
        course1.setQuota(30); // Increase quota by 10
        instructor.addCourse(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        instructor.addCourse(course2);
        
        // Calculate remaining quota (30 + 25 = 55)
        int result = instructor.remainingQuotaInCourses();
        assertEquals("Remaining quota should be 55 for two courses with quota increase on first course", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Test Case 5: Multiple courses with quota increase and decrease
        Course course1 = new Course();
        course1.setQuota(18);
        instructor.addCourse(course1);
        
        Course course2 = new Course();
        course2.setQuota(22);
        course2.setQuota(28); // Increase quota by 6
        instructor.addCourse(course2);
        
        Course course3 = new Course();
        course3.setQuota(28);
        course3.setQuota(24); // Decrease quota by 4
        instructor.addCourse(course3);
        
        // Calculate remaining quota (18 + 28 + 24 = 70)
        int result = instructor.remainingQuotaInCourses();
        assertEquals("Remaining quota should be 70 for three courses with quota changes", 70, result);
    }
}