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
        // Create a single course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate remaining quota (should be 25)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate remaining quota (should be 22)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 22 after decreasing from 30 by 8", 22, remainingQuota);
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
        
        // Add courses to instructor (no quota changes made)
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 75 (15 + 25 + 35)", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Calculate remaining quota (should be 30 + 25 = 55)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 55 (30 + 25)", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuota(18);
        
        Course course2 = new Course();
        course2.setQuota(22);
        
        Course course3 = new Course();
        course3.setQuota(28);
        
        // Increase quota of second course by 6 and decrease third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify expected output
        assertEquals("Remaining quota should be 70 (18 + 28 + 24)", 70, remainingQuota);
    }
}