import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize a new Instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a course with initial quota of 20
        Course course = new Course();
        course.setQuotaLimit(20);
        
        // Add the course to instructor
        instructor.addCourse(course);
        
        // Instructor increases the quota by 5
        instructor.increaseQuota(course, 5);
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing by 5", 
                     25, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuotaLimit(30);
        
        // Add the course to instructor
        instructor.addCourse(course);
        
        // Instructor decreases the quota by 8
        instructor.decreaseQuota(course, 8);
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing by 8", 
                     22, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuotaLimit(15);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        Course course3 = new Course();
        course3.setQuotaLimit(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // No quota changes made - calculate expected sum: 15 + 25 + 35 = 75
        assertEquals("Total remaining quota should be 75 for three courses", 
                     75, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuotaLimit(20);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        // Add both courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        instructor.increaseQuota(course1, 10);
        
        // Verify total remaining quota: 30 + 25 = 55
        assertEquals("Total remaining quota should be 55 after increasing first course by 10", 
                     55, instructor.getRemainingQuota());
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuotaLimit(18);
        
        Course course2 = new Course();
        course2.setQuotaLimit(22);
        
        Course course3 = new Course();
        course3.setQuotaLimit(28);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6 and decrease third course by 4
        instructor.increaseQuota(course2, 6);
        instructor.decreaseQuota(course3, 4);
        
        // Verify total remaining quota: 18 + 28 + 24 = 70
        assertEquals("Total remaining quota should be 70 after changes", 
                     70, instructor.getRemainingQuota());
    }
}