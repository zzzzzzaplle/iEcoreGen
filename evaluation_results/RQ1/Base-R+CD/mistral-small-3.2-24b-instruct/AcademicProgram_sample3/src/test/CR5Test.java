import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Action: Increase the quota of this course by 5
        course.increaseQuotaBy(5);
        
        // Verify: The remaining quota of the instructor's single course should be 25
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota after increasing by 5 should be 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Action: Decrease the quota of this course by 8
        course.decreaseQuotaBy(8);
        
        // Verify: The remaining quota of the instructor's single course should be 22
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota after decreasing by 8 should be 22", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create three courses with quotas of 15, 25, and 35
        Course course1 = new Course();
        course1.setQuota(15);
        Course course2 = new Course();
        course2.setQuota(25);
        Course course3 = new Course();
        course3.setQuota(35);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Action: No quota changes made
        
        // Verify: The remaining quotas should be 15 + 25 + 35 = 75
        int result = instructor.calculateRemainingQuota();
        assertEquals("Total remaining quota without changes should be 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create two courses with quotas of 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        Course course2 = new Course();
        course2.setQuota(25);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Action: Increase the quota of the first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify: The remaining quotas should be 30 + 25 = 55
        int result = instructor.calculateRemainingQuota();
        assertEquals("Total remaining quota after increasing first course by 10 should be 55", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create three courses with quotas of 18, 22, and 28
        Course course1 = new Course();
        course1.setQuota(18);
        Course course2 = new Course();
        course2.setQuota(22);
        Course course3 = new Course();
        course3.setQuota(28);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Action: Increase the quota of the second course by 6 and decrease the third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Verify: The remaining quotas should be 18 + 28 + 24 = 70
        int result = instructor.calculateRemainingQuota();
        assertEquals("Total remaining quota after changes should be 70", 70, result);
    }
}