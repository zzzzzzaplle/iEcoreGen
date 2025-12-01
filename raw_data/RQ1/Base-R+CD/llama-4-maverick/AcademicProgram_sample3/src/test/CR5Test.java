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
        // Initialize common test objects
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Test Case 1: SingleCourseWithSingleQuotaIncrease
        // Input: An Instructor object with a single course having an initial quota of 20. 
        // The instructor increases the quota of this course by 5.
        
        // Setup course with initial quota
        course1.setQuota(20);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Increase quota by 5
        course1.increaseQuotaBy(5);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 25; // 20 + 5
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        
        assertEquals("Remaining quota should be 25 after increasing single course quota by 5", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Test Case 2: SingleCourseWithSingleQuotaDecrease
        // Input: An Instructor object with a single course having an initial quota of 30. 
        // The instructor decreases the quota of this course by 8.
        
        // Setup course with initial quota
        course1.setQuota(30);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        instructor.setCourses(courses);
        
        // Decrease quota by 8
        course1.decreaseQuotaBy(8);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 22; // 30 - 8
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        
        assertEquals("Remaining quota should be 22 after decreasing single course quota by 8", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Test Case 3: MultipleCoursesWithNoQuotaChanges
        // Input: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively. 
        // There are no quota changes made.
        
        // Setup courses with initial quotas
        course1.setQuota(15);
        course2.setQuota(25);
        course3.setQuota(35);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Verify remaining quota calculation (no quota changes)
        int expectedRemainingQuota = 75; // 15 + 25 + 35
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        
        assertEquals("Remaining quota should be 75 for three courses with quotas 15, 25, and 35", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Test Case 4: MultipleCoursesWithQuotaIncreaseOnOneCourse
        // Input: An Instructor object with two courses having initial quotas of 20 and 25. 
        // The instructor increases the quota of the first course by 10.
        
        // Setup courses with initial quotas
        course1.setQuota(20);
        course2.setQuota(25);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 55; // (20 + 10) + 25 = 55
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        
        assertEquals("Remaining quota should be 55 after increasing first course quota by 10", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Test Case 5: MultipleCoursesWithQuotaIncreaseAndDecrease
        // Input: An Instructor object with three courses having initial quotas of 18, 22, and 28. 
        // The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4.
        
        // Setup courses with initial quotas
        course1.setQuota(18);
        course2.setQuota(22);
        course3.setQuota(28);
        
        // Add courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Apply quota changes: increase second course by 6, decrease third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Verify remaining quota calculation
        int expectedRemainingQuota = 70; // 18 + (22 + 6) + (28 - 4) = 70
        int actualRemainingQuota = instructor.calculateRemainingQuota();
        
        assertEquals("Remaining quota should be 70 after increasing second course by 6 and decreasing third course by 4", 
                     expectedRemainingQuota, actualRemainingQuota);
    }
}