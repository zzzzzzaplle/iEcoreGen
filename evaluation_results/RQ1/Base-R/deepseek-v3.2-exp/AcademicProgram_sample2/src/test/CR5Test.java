import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        instructor.addCourse(course1);
        
        // Action: Instructor increases quota by 5
        instructor.increaseCourseQuota(course1, 5);
        
        // Verify: Remaining quota should be 25
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: Create instructor with single course having initial quota of 30
        course1.setQuota(30);
        instructor.addCourse(course1);
        
        // Action: Instructor decreases quota by 8
        instructor.decreaseCourseQuota(course1, 8);
        
        // Verify: Remaining quota should be 22
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: Create instructor with three courses having quotas 15, 25, and 35
        course1.setQuota(15);
        course2.setQuota(25);
        course3.setQuota(35);
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: No quota changes made
        
        // Verify: Remaining quota should be 15 + 25 + 35 = 75
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        assertEquals("Remaining quota should be sum of all course quotas: 75", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: Create instructor with two courses having quotas 20 and 25
        course1.setQuota(20);
        course2.setQuota(25);
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        // Action: Instructor increases quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Verify: Remaining quota should be 30 + 25 = 55
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        assertEquals("Remaining quota should be 55 after increasing first course by 10", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: Create instructor with three courses having quotas 18, 22, and 28
        course1.setQuota(18);
        course2.setQuota(22);
        course3.setQuota(28);
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Action: Increase second course by 6 and decrease third course by 4
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Verify: Remaining quota should be 18 + 28 + 24 = 70
        int remainingQuota = instructor.getRemainingQuotaInCourses();
        assertEquals("Remaining quota should be 70 after increasing second course by 6 and decreasing third by 4", 70, remainingQuota);
    }
}