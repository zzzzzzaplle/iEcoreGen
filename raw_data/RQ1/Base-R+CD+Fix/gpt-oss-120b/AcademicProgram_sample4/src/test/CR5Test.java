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
        
        // Add the course to instructor
        instructor.addCourse(course);
        
        // Increase the quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 25 (20 + 5)
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add the course to instructor
        instructor.addCourse(course);
        
        // Decrease the quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 22 (30 - 8)
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with different quotas
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
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 75 (15 + 25 + 35)
        assertEquals("Remaining quota should be sum of all course quotas", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add both courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 55 (20 + 10 + 25)
        assertEquals("Remaining quota should reflect the quota increase", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas
        Course course1 = new Course();
        course1.setQuota(18);
        
        Course course2 = new Course();
        course2.setQuota(22);
        
        Course course3 = new Course();
        course3.setQuota(28);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Decrease quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 70 (18 + (22 + 6) + (28 - 4))
        assertEquals("Remaining quota should reflect all quota changes", 70, remainingQuota);
    }
}