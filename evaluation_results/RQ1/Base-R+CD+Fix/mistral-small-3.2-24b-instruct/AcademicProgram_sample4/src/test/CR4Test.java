import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add the course to the academic program
        academicProgram.getCourses().add(course);
        
        // Test counting courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that exactly 1 course is found
        assertEquals("Should return 1 when there is one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on different days (not "Friday")
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Thursday"));
        
        // Add courses to the academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        
        // Test counting courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify that no courses are found on Friday
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create 5 courses that all occur on "Wednesday"
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday", "Friday"));
            academicProgram.getCourses().add(course);
        }
        
        // Test counting courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify that all 5 courses are found
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create 8 courses - 3 on "Tuesday", 5 on other days
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
            academicProgram.getCourses().add(course);
        }
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
            academicProgram.getCourses().add(course);
        }
        
        // Test counting courses on "Tuesday"
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify that exactly 3 courses are found
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test counting courses on any day with empty academic program
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that 0 courses are found
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}