import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    private List<Course> courses;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        courses = new ArrayList<>();
        instructor.setCourses(courses);
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        courses.add(course);
        
        // Action: Increase the quota by 5
        course.increaseQuotaBy(5);
        
        // Verify: The remaining quota should be 25
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota for single course after increase should be 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        courses.add(course);
        
        // Action: Decrease the quota by 8
        course.decreaseQuotaBy(8);
        
        // Verify: The remaining quota should be 22
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota for single course after decrease should be 22", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuota(15);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setQuota(35);
        courses.add(course3);
        
        // Action: No quota changes made
        
        // Verify: The remaining quota should be 15 + 25 + 35 = 75
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota for multiple courses with no changes should be 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        courses.add(course2);
        
        // Action: Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify: The remaining quota should be 30 + 25 = 55
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota after increasing first course should be 55", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create three courses with quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuota(18);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(22);
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setQuota(28);
        courses.add(course3);
        
        // Action: Increase second course by 6 and decrease third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Verify: The remaining quota should be 18 + 28 + 24 = 70
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota after mixed quota changes should be 70", 70, result);
    }
}