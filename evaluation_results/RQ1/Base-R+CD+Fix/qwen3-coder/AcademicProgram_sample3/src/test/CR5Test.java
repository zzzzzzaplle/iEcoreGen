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
        
        // Instructor increases the quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota for instructor's courses
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 25
        assertEquals("Single course with quota increase should have remaining quota of 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        courses.add(course);
        
        // Instructor decreases the quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota for instructor's courses
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 22
        assertEquals("Single course with quota decrease should have remaining quota of 22", 22, result);
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
        
        // Calculate remaining quota for instructor's courses
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 75 (15 + 25 + 35)
        assertEquals("Multiple courses with no quota changes should have remaining quota of 75", 75, result);
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
        
        // Instructor increases quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota for instructor's courses
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 55 (30 + 25)
        assertEquals("Multiple courses with quota increase on one course should have remaining quota of 55", 55, result);
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
        
        // Instructor increases quota of second course by 6 and decreases quota of third course by 4
        course2.increaseQuotaBy(6);  // 22 + 6 = 28
        course3.decreaseQuotaBy(4); // 28 - 4 = 24
        
        // Calculate remaining quota for instructor's courses
        int result = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 70 (18 + 28 + 24)
        assertEquals("Multiple courses with quota increase and decrease should have remaining quota of 70", 70, result);
    }
}