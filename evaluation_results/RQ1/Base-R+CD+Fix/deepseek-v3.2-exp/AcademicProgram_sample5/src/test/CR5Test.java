import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    
    @Before
    public void setUp() {
        // Initialize instructor and courses before each test
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        
        // Set initial enrollments to empty lists
        course1.setEnrollments(new ArrayList<>());
        course2.setEnrollments(new ArrayList<>());
        course3.setEnrollments(new ArrayList<>());
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Test Case 1: Single course with quota increase
        // Input: Instructor with single course, initial quota 20, increase by 5
        // Expected Output: Remaining quota should be 25
        
        // Setup
        course1.setQuota(20);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Action: Increase quota by 5
        course1.increaseQuotaBy(5);
        
        // Assert: Remaining quota should be 25
        assertEquals("Remaining quota for single course after increase should be 25", 
                     25, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Test Case 2: Single course with quota decrease
        // Input: Instructor with single course, initial quota 30, decrease by 8
        // Expected Output: Remaining quota should be 22
        
        // Setup
        course1.setQuota(30);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Action: Decrease quota by 8
        course1.decreaseQuotaBy(8);
        
        // Assert: Remaining quota should be 22
        assertEquals("Remaining quota for single course after decrease should be 22", 
                     22, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Test Case 3: Multiple courses with no quota changes
        // Input: Instructor with three courses, quotas 15, 25, 35
        // Expected Output: Remaining quota should be 15 + 25 + 35 = 75
        
        // Setup
        course1.setQuota(15);
        course2.setQuota(25);
        course3.setQuota(35);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // No quota changes applied
        
        // Assert: Remaining quota should be sum of all quotas (75)
        assertEquals("Remaining quota for multiple courses without changes should be 75", 
                     75, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Test Case 4: Multiple courses with quota increase on one course
        // Input: Instructor with two courses, quotas 20 and 25, increase first course by 10
        // Expected Output: Remaining quota should be 30 + 25 = 55
        
        // Setup
        course1.setQuota(20);
        course2.setQuota(25);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Action: Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Assert: Remaining quota should be 30 + 25 = 55
        assertEquals("Remaining quota after increase on one course should be 55", 
                     55, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Test Case 5: Multiple courses with quota increase and decrease
        // Input: Instructor with three courses, quotas 18, 22, 28
        // Increase second course by 6, decrease third course by 4
        // Expected Output: Remaining quota should be 18 + 28 + 24 = 70
        
        // Setup
        course1.setQuota(18);
        course2.setQuota(22);
        course3.setQuota(28);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Actions: Increase second course by 6, decrease third course by 4
        course2.increaseQuotaBy(6);  // 22 + 6 = 28
        course3.decreaseQuotaBy(4);  // 28 - 4 = 24
        
        // Assert: Remaining quota should be 18 + 28 + 24 = 70
        assertEquals("Remaining quota after mixed increases and decreases should be 70", 
                     70, instructor.calculateRemainingQuota());
    }
}