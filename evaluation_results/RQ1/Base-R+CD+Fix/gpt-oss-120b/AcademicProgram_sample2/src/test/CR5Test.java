import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Instructor increases the quota by 5
        course.increaseQuotaBy(5);
        
        // Verify remaining quota is 25
        int remainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Instructor decreases the quota by 8
        course.decreaseQuotaBy(8);
        
        // Verify remaining quota is 22
        int remainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, remainingQuota);
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
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // No quota changes made
        
        // Verify remaining quota is 15 + 25 + 35 = 75
        int remainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be sum of all course quotas: 75", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Instructor increases quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify remaining quota is 30 + 25 = 55
        int remainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 30 + 25 = 55", 55, remainingQuota);
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
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Instructor increases quota of second course by 6 and decreases quota of third course by 4
        course2.increaseQuotaBy(6);  // 22 + 6 = 28
        course3.decreaseQuotaBy(4); // 28 - 4 = 24
        
        // Verify remaining quota is 18 + 28 + 24 = 70
        int remainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 18 + 28 + 24 = 70", 70, remainingQuota);
    }
}