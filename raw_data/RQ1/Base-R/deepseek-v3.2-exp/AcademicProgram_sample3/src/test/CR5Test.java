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
        
        // Verify remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing from 20 by 5", 
                     25, instructor.getRemainingQuotaInCourses());
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
        
        // Verify remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing from 30 by 8", 
                     22, instructor.getRemainingQuotaInCourses());
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
        
        // Verify total remaining quota is 75 (15 + 25 + 35)
        assertEquals("Remaining quota should be 75 for three courses with quotas 15, 25, 35", 
                     75, instructor.getRemainingQuotaInCourses());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add both courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Instructor increases quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Verify total remaining quota is 55 (30 + 25)
        assertEquals("Remaining quota should be 55 after increasing first course from 20 to 30", 
                     55, instructor.getRemainingQuotaInCourses());
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
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Instructor increases second course quota by 6 and decreases third course quota by 4
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Verify total remaining quota is 70 (18 + 28 + 24)
        assertEquals("Remaining quota should be 70 after increasing second course to 28 and decreasing third course to 24", 
                     70, instructor.getRemainingQuotaInCourses());
    }
}