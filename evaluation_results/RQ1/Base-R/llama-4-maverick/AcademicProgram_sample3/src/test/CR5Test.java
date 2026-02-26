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
        course.setQuotaLimit(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Increase quota by 5
        course.setQuotaLimit(course.getQuotaLimit() + 5);
        
        // Verify remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing from 20 to 25", 
                     25, instructor.remainingQuotaInCourses());
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuotaLimit(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Decrease quota by 8
        course.setQuotaLimit(course.getQuotaLimit() - 8);
        
        // Verify remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing from 30 to 22", 
                     22, instructor.remainingQuotaInCourses());
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setQuotaLimit(15);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        Course course3 = new Course();
        course3.setQuotaLimit(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify total remaining quota is 75 (15 + 25 + 35)
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 
                     75, instructor.remainingQuotaInCourses());
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with initial quotas 20 and 25
        Course course1 = new Course();
        course1.setQuotaLimit(20);
        
        Course course2 = new Course();
        course2.setQuotaLimit(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        course1.setQuotaLimit(course1.getQuotaLimit() + 10);
        
        // Verify total remaining quota is 55 (30 + 25)
        assertEquals("Remaining quota should be 55 after increasing first course quota from 20 to 30", 
                     55, instructor.remainingQuotaInCourses());
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with initial quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setQuotaLimit(18);
        
        Course course2 = new Course();
        course2.setQuotaLimit(22);
        
        Course course3 = new Course();
        course3.setQuotaLimit(28);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6
        course2.setQuotaLimit(course2.getQuotaLimit() + 6);
        
        // Decrease quota of third course by 4
        course3.setQuotaLimit(course3.getQuotaLimit() - 4);
        
        // Verify total remaining quota is 70 (18 + 28 + 24)
        assertEquals("Remaining quota should be 70 after increasing second course by 6 and decreasing third by 4", 
                     70, instructor.remainingQuotaInCourses());
    }
}