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
        // Create a course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor increases quota by 5
        instructor.increaseCourseQuota(course, 5);
        
        // Calculate remaining quota
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify expected output: 25 (20 + 5)
        assertEquals(25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Instructor decreases quota by 8
        instructor.decreaseCourseQuota(course, 8);
        
        // Calculate remaining quota
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify expected output: 22 (30 - 8)
        assertEquals(22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with initial quotas of 15, 25, and 35
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
        
        // Calculate remaining quota (no quota changes made)
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify expected output: 75 (15 + 25 + 35)
        assertEquals(75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas of 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Instructor increases quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Calculate remaining quota
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify expected output: 55 (30 + 25)
        assertEquals(55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas of 18, 22, and 28
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
        
        // Instructor increases quota of second course by 6
        instructor.increaseCourseQuota(course2, 6);
        
        // Instructor decreases quota of third course by 4
        instructor.decreaseCourseQuota(course3, 4);
        
        // Calculate remaining quota
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        
        // Verify expected output: 70 (18 + 28 + 24)
        assertEquals(70, remainingQuota);
    }
}