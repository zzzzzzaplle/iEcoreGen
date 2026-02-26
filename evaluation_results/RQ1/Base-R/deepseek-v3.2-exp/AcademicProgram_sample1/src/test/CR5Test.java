import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private AcademicProgram academicProgram;
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize academic program and instructor before each test
        academicProgram = new AcademicProgram();
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a course with initial quota of 20
        Course course = new Course();
        course.setQuota(20);
        
        // Add course to instructor and academic program
        instructor.addCourse(course);
        academicProgram.addCourse(course);
        
        // Instructor increases quota by 5
        instructor.increaseCourseQuota(course, 5);
        
        // Calculate remaining quota for instructor's courses
        int result = academicProgram.calculateRemainingQuotaInInstructorCourses(instructor);
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a course with initial quota of 30
        Course course = new Course();
        course.setQuota(30);
        
        // Add course to instructor and academic program
        instructor.addCourse(course);
        academicProgram.addCourse(course);
        
        // Instructor decreases quota by 8
        instructor.decreaseCourseQuota(course, 8);
        
        // Calculate remaining quota for instructor's courses
        int result = academicProgram.calculateRemainingQuotaInInstructorCourses(instructor);
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 22, result);
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
        
        // Add all courses to instructor and academic program
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Calculate remaining quota for instructor's courses (no quota changes made)
        int result = academicProgram.calculateRemainingQuotaInInstructorCourses(instructor);
        
        // Verify the total remaining quota is 15 + 25 + 35 = 75
        assertEquals("Total remaining quota should be 75 for courses with quotas 15, 25, and 35", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create two courses with quotas 20 and 25
        Course course1 = new Course();
        course1.setQuota(20);
        
        Course course2 = new Course();
        course2.setQuota(25);
        
        // Add both courses to instructor and academic program
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Instructor increases quota of first course by 10
        instructor.increaseCourseQuota(course1, 10);
        
        // Calculate remaining quota for instructor's courses
        int result = academicProgram.calculateRemainingQuotaInInstructorCourses(instructor);
        
        // Verify the total remaining quota is 30 + 25 = 55
        assertEquals("Total remaining quota should be 55 after increasing first course quota by 10", 55, result);
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
        
        // Add all courses to instructor and academic program
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Instructor increases quota of second course by 6 and decreases quota of third course by 4
        instructor.increaseCourseQuota(course2, 6);
        instructor.decreaseCourseQuota(course3, 4);
        
        // Calculate remaining quota for instructor's courses
        int result = academicProgram.calculateRemainingQuotaInInstructorCourses(instructor);
        
        // Verify the total remaining quota is 18 + 28 + 24 = 70
        assertEquals("Total remaining quota should be 70 after quota adjustments", 70, result);
    }
}