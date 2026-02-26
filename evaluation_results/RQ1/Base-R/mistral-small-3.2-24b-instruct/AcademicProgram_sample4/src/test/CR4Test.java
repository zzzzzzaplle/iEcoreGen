import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        
        // Add the course to the academic program
        academicProgram.addCourse(course);
        
        // Test for Monday - should return 1
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on different days (Tuesday and Wednesday)
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Tuesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday"));
        
        // Add courses to the academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Test for Monday - should return 0 since no courses on Monday
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Friday - should return 5
        int result = academicProgram.getNumberOfCoursesOnDay("Friday");
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses - three on Thursday, others on different days
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Thursday"));
            academicProgram.addCourse(course);
        }
        
        // Add five more courses on different days
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course4);
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Tuesday"));
        academicProgram.addCourse(course5);
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Wednesday"));
        academicProgram.addCourse(course6);
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Friday"));
        academicProgram.addCourse(course7);
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Saturday"));
        academicProgram.addCourse(course8);
        
        // Test for Thursday - should return 3
        int result = academicProgram.getNumberOfCoursesOnDay("Thursday");
        assertEquals("Should return 3 when exactly three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test with empty academic program - should return 0
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}