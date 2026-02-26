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
        
        // Add course to instructor
        courses.add(course);
        
        // Instructor increases quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        courses.add(course);
        
        // Instructor decreases quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22", 22, result);
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
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        
        // Calculate remaining quota (no quota changes made)
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 75 (15 + 25 + 35)
        assertEquals("Remaining quota should be 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        courses.add(course1);
        courses.add(course2);
        
        // Instructor increases quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 55 (30 + 25)
        assertEquals("Remaining quota should be 55", 55, result);
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
        
        // Add all courses to instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        
        // Instructor increases quota of second course by 6
        course2.increaseQuotaBy(6);
        
        // Instructor decreases quota of third course by 4
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 70 (18 + 28 + 24)
        assertEquals("Remaining quota should be 70", 70, result);
    }
}