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
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: Create instructor with single course having initial quota of 20
        course1.setQuota(20);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Action: Increase quota by 5
        course1.increaseQuotaBy(5);
        
        // Verify: Remaining quota should be 25
        int expectedRemainingQuota = 25;
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Single course with quota increase should return correct remaining quota", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create instructor with single course having initial quota of 30
        course1.setQuota(30);
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Action: Decrease quota by 8
        course1.decreaseQuotaBy(8);
        
        // Verify: Remaining quota should be 22
        int expectedRemainingQuota = 22;
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Single course with quota decrease should return correct remaining quota", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create instructor with three courses having quotas 15, 25, and 35
        course1.setQuota(15);
        course2.setQuota(25);
        course3.setQuota(35);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Action: No quota changes made
        
        // Verify: Remaining quota should be 15 + 25 + 35 = 75
        int expectedRemainingQuota = 75;
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Multiple courses with no quota changes should return correct sum", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create instructor with two courses having quotas 20 and 25
        course1.setQuota(20);
        course2.setQuota(25);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Action: Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify: Remaining quota should be 30 + 25 = 55
        int expectedRemainingQuota = 55;
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Multiple courses with quota increase on one course should return correct sum", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create instructor with three courses having quotas 18, 22, and 28
        course1.setQuota(18);
        course2.setQuota(22);
        course3.setQuota(28);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Action: Increase second course quota by 6 and decrease third course quota by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Verify: Remaining quota should be 18 + 28 + 24 = 70
        int expectedRemainingQuota = 70;
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        assertEquals("Multiple courses with quota increase and decrease should return correct sum", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
}