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
        course.setCourseCode("CS101");
        course.setQuotaLimit(20);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Increase quota by 5
        instructor.increaseCourseQuota("CS101", 5);
        
        // Calculate remaining quota should be 25
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 25 after increasing from 20 to 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setCourseCode("MATH201");
        course.setQuotaLimit(30);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Decrease quota by 8
        instructor.decreaseCourseQuota("MATH201", 8);
        
        // Calculate remaining quota should be 22
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 22 after decreasing from 30 to 22", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course();
        course1.setCourseCode("PHY101");
        course1.setQuotaLimit(15);
        
        Course course2 = new Course();
        course2.setCourseCode("CHEM201");
        course2.setQuotaLimit(25);
        
        Course course3 = new Course();
        course3.setCourseCode("BIO301");
        course3.setQuotaLimit(35);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Calculate remaining quota should be 15 + 25 + 35 = 75
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 75 (15+25+35) with no quota changes", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setCourseCode("ENG101");
        course1.setQuotaLimit(20);
        
        Course course2 = new Course();
        course2.setCourseCode("HIST201");
        course2.setQuotaLimit(25);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Increase quota of first course by 10
        instructor.increaseCourseQuota("ENG101", 10);
        
        // Calculate remaining quota should be 30 + 25 = 55
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 55 (30+25) after increasing first course quota", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create three courses with quotas 18, 22, and 28
        Course course1 = new Course();
        course1.setCourseCode("ART101");
        course1.setQuotaLimit(18);
        
        Course course2 = new Course();
        course2.setCourseCode("MUS201");
        course2.setQuotaLimit(22);
        
        Course course3 = new Course();
        course3.setCourseCode("DANCE301");
        course3.setQuotaLimit(28);
        
        // Add courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Increase quota of second course by 6 and decrease third course by 4
        instructor.increaseCourseQuota("MUS201", 6);
        instructor.decreaseCourseQuota("DANCE301", 4);
        
        // Calculate remaining quota should be 18 + 28 + 24 = 70
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 70 (18+28+24) after quota adjustments", 70, result);
    }
}