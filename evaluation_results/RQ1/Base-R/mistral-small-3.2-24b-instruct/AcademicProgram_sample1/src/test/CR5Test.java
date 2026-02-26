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
        
        // Increase quota by 5
        course.setQuota(25);
        
        // Add course to instructor
        courses.add(course);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 25
        assertEquals("Single course with quota increase should have remaining quota of 25", 
                     25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Decrease quota by 8
        course.setQuota(22);
        
        // Add course to instructor
        courses.add(course);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 22
        assertEquals("Single course with quota decrease should have remaining quota of 22", 
                     22, remainingQuota);
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
        
        // Add courses to instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 75 (15 + 25 + 35)
        assertEquals("Multiple courses with no quota changes should have total remaining quota of 75", 
                     75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Increase quota of first course by 10
        course1.setQuota(30);
        
        // Add courses to instructor
        courses.add(course1);
        courses.add(course2);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 55 (30 + 25)
        assertEquals("Multiple courses with quota increase on one course should have total remaining quota of 55", 
                     55, remainingQuota);
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
        course2.setQuota(28);
        course3.setQuota(24);
        
        // Add courses to instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 70 (18 + 28 + 24)
        assertEquals("Multiple courses with quota increase and decrease should have total remaining quota of 70", 
                     70, remainingQuota);
    }
}