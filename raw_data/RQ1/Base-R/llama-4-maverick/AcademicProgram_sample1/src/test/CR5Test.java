import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor increases quota by 5
        course.setQuota(course.getQuota() + 5);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 25;
        int actualRemainingQuota = instructor.remainingQuotaInCourses();
        
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a single course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor decreases quota by 8
        course.setQuota(course.getQuota() - 8);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 22;
        int actualRemainingQuota = instructor.remainingQuotaInCourses();
        
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 
                     expectedRemainingQuota, actualRemainingQuota);
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
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // No quota changes made
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 15 + 25 + 35;
        int actualRemainingQuota = instructor.remainingQuotaInCourses();
        
        assertEquals("Remaining quota should be sum of all course quotas (75)", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        course1.setQuota(course1.getQuota() + 10);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 30 + 25;
        int actualRemainingQuota = instructor.remainingQuotaInCourses();
        
        assertEquals("Remaining quota should be 55 after increasing first course quota by 10", 
                     expectedRemainingQuota, actualRemainingQuota);
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
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6
        course2.setQuota(course2.getQuota() + 6);
        
        // Decrease quota of third course by 4
        course3.setQuota(course3.getQuota() - 4);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 18 + 28 + 24;
        int actualRemainingQuota = instructor.remainingQuotaInCourses();
        
        assertEquals("Remaining quota should be 70 after quota adjustments", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
}