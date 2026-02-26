import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        academicProgram.getCourses().add(course);
        
        // Call method with "Monday" as input
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result is 1
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on different days (not "Tuesday")
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Thursday", "Friday"));
        academicProgram.getCourses().addAll(Arrays.asList(course1, course2));
        
        // Call method with "Tuesday" as input
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify result is 0
        assertEquals("Should return 0 courses on Tuesday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create 5 courses that all occur on "Wednesday"
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            academicProgram.getCourses().add(course);
        }
        
        // Call method with "Wednesday" as input
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result is 5
        assertEquals("Should return 5 courses on Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create 8 courses - 3 on "Friday" and 5 on other days
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            academicProgram.getCourses().add(course);
        }
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
            academicProgram.getCourses().add(course);
        }
        
        // Call method with "Friday" as input
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result is 3
        assertEquals("Should return 3 courses on Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is empty (no courses added)
        
        // Call method with any day as input
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result is 0
        assertEquals("Should return 0 courses when academic program is empty", 0, result);
    }
}