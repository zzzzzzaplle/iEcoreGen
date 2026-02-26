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
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        courses.add(course);
        
        // Increase the quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota (should be 25 since no enrollments exist)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 25 for single course with quota increased from 20 to 25", 
                     25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        courses.add(course);
        
        // Decrease the quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota (should be 22 since no enrollments exist)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 22 for single course with quota decreased from 30 to 22", 
                     22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuota(15);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setQuota(35);
        courses.add(course3);
        
        // No quota changes made
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75 since no enrollments exist)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 75 for three courses with quotas 15, 25, and 35", 
                     75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(25);
        courses.add(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota (should be 30 + 25 = 55 since no enrollments exist)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 55 for two courses with quotas increased to 30 and 25", 
                     55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuota(18);
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setQuota(22);
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setQuota(28);
        courses.add(course3);
        
        // Increase quota of second course by 6 and decrease quota of third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70 since no enrollments exist)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 70 for three courses with quotas 18, 28, and 24", 
                     70, remainingQuota);
    }
}