import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Input: An Instructor object with a single course having an initial quota of 20. 
        // The instructor increases the quota of this course by 5.
        Course course = new Course();
        course.setQuota(20);
        course.increaseQuotaBy(5);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Expected Output: The remaining quota of the instructor's single course should be 25.
        int result = instructor.calculateRemainingQuota();
        assertEquals("Single course with quota increase from 20 to 25", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Input: An Instructor object with a single course having an initial quota of 30. 
        // The instructor decreases the quota of this course by 8.
        Course course = new Course();
        course.setQuota(30);
        course.decreaseQuotaBy(8);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Expected Output: The remaining quota of the instructor's single course should be 22.
        int result = instructor.calculateRemainingQuota();
        assertEquals("Single course with quota decrease from 30 to 22", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Input: An Instructor object with three courses having initial quotas of 15, 25, and 35 respectively. 
        // There are no quota changes made.
        Course course1 = new Course();
        course1.setQuota(15);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        Course course3 = new Course();
        course3.setQuota(35);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Expected Output: The remaining quotas of the instructor's courses should be 15 + 25 + 35 = 75.
        int result = instructor.calculateRemainingQuota();
        assertEquals("Three courses with quotas 15, 25, 35 - total 75", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Input: An Instructor object with two courses having initial quotas of 20 and 25. 
        // The instructor increases the quota of the first course by 10.
        Course course1 = new Course();
        course1.setQuota(20);
        course1.increaseQuotaBy(10);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Expected Output: The remaining quotas of the instructor's courses should be 30 + 25 = 55.
        int result = instructor.calculateRemainingQuota();
        assertEquals("Two courses with quotas increased to 30 and 25 - total 55", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Input: An Instructor object with three courses having initial quotas of 18, 22, and 28. 
        // The instructor increases the quota of the second course by 6 and decreases the quota of the third course by 4.
        Course course1 = new Course();
        course1.setQuota(18);
        
        Course course2 = new Course();
        course2.setQuota(22);
        course2.increaseQuotaBy(6);
        
        Course course3 = new Course();
        course3.setQuota(28);
        course3.decreaseQuotaBy(4);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Expected Output: The remaining quotas of the instructor's courses should be 18 + 28 + 24 = 70.
        int result = instructor.calculateRemainingQuota();
        assertEquals("Three courses with quotas 18, 28, 24 - total 70", 70, result);
    }
}