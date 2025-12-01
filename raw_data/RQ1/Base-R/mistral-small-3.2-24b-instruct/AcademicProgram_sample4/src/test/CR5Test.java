import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR5Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    private Classroom classroom;
    
    @Before
    public void setUp() {
        // Create a common classroom for all tests
        classroom = new Classroom();
        classroom.setCapacity(50); // Arbitrary capacity for classroom
        
        // Initialize instructor
        instructor = new Instructor();
        instructor.setName("John");
        instructor.setSurname("Doe");
        
        // Initialize courses with basic setup
        course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setClassroom(classroom);
        
        course2 = new Course();
        course2.setCourseCode("MATH201");
        course2.setClassroom(classroom);
        
        course3 = new Course();
        course3.setCourseCode("PHY301");
        course3.setClassroom(classroom);
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Setup: An Instructor object with a single course having an initial quota of 20
        course1.setQuotaLimit(20);
        instructor.getCourses().add(course1);
        
        // Action: The instructor increases the quota of this course by 5
        course1.increaseQuotaLimit(5);
        
        // Verify: The remaining quota of the instructor's single course should be 25
        int remainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Setup: An Instructor object with a single course having an initial quota of 30
        course1.setQuotaLimit(30);
        instructor.getCourses().add(course1);
        
        // Action: The instructor decreases the quota of this course by 8
        course1.decreaseQuotaLimit(8);
        
        // Verify: The remaining quota of the instructor's single course should be 22
        int remainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Setup: An Instructor object with three courses having initial quotas of 15, 25, and 35
        course1.setQuotaLimit(15);
        course2.setQuotaLimit(25);
        course3.setQuotaLimit(35);
        
        instructor.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Action: No quota changes made
        
        // Verify: The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75
        int remainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota should be sum of all course quotas (15+25+35=75)", 75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Setup: An Instructor object with two courses having initial quotas of 20 and 25
        course1.setQuotaLimit(20);
        course2.setQuotaLimit(25);
        
        instructor.getCourses().addAll(Arrays.asList(course1, course2));
        
        // Action: The instructor increases the quota of the first course by 10
        course1.increaseQuotaLimit(10);
        
        // Verify: The remaining quotas of the instructor's courses should be 30 + 25 = 55
        int remainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota should be 30+25=55 after increasing first course quota", 55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Setup: An Instructor object with three courses having initial quotas of 18, 22, and 28
        course1.setQuotaLimit(18);
        course2.setQuotaLimit(22);
        course3.setQuotaLimit(28);
        
        instructor.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Action: The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4
        course2.increaseQuotaLimit(6);
        course3.decreaseQuotaLimit(4);
        
        // Verify: The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70
        int remainingQuota = instructor.getRemainingQuota();
        assertEquals("Remaining quota should be 18+28+24=70 after quota adjustments", 70, remainingQuota);
    }
}