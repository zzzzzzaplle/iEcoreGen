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
        // Setup: Create a course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing by 5 from initial 20", 
                     25, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create a course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing by 8 from initial 30", 
                     22, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuota(15);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        Course course3 = new Course();
        course3.setQuota(35);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // No quota changes made
        
        // Verify the remaining quota is 15 + 25 + 35 = 75
        assertEquals("Remaining quota should be 75 (15 + 25 + 35) with no quota changes", 
                     75, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify the remaining quota is 30 + 25 = 55
        assertEquals("Remaining quota should be 55 (30 + 25) after increasing first course by 10", 
                     55, instructor.calculateRemainingQuota());
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create three courses with quotas 18, 22, and 28
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
        
        // Increase quota of second course by 6 and decrease third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Verify the remaining quota is 18 + 28 + 24 = 70
        assertEquals("Remaining quota should be 70 (18 + 28 + 24) after operations", 
                     70, instructor.calculateRemainingQuota());
    }
}