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
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Test with Monday as the given day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result is 1 as there is only one course on Monday
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on different days (not Friday)
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        // Add courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        
        // Test with Friday as the given day (no courses on Friday)
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result is 0 since no courses are taught on Friday
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Tuesday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
            academicProgram.getCourses().add(course);
        }
        
        // Test with Tuesday as the given day
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify result is 5 as all five courses are taught on Tuesday
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses - three on Wednesday, others on different days
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday", "Friday"));
            academicProgram.getCourses().add(course);
        }
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Thursday"));
            academicProgram.getCourses().add(course);
        }
        
        // Test with Wednesday as the given day
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result is 3 as only three courses are taught on Wednesday
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test with an empty academic program and Wednesday as the given day
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result is 0 as there are no courses in the academic program
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }

    

}