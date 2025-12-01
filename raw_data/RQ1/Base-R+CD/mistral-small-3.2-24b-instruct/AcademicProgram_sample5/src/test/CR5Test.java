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
        // Create a course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Verify remaining quota is 25
        assertEquals(25, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Verify remaining quota is 22
        assertEquals(22, instructor.calculateRemainingQuota());
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
        
        // Add courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Verify remaining quota is 75 (15 + 25 + 35)
        assertEquals(75, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify remaining quota is 55 (30 + 25)
        assertEquals(55, instructor.calculateRemainingQuota());
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
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Increase quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Decrease quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Verify remaining quota is 70 (18 + 28 + 24)
        assertEquals(70, instructor.calculateRemainingQuota());
    }
}