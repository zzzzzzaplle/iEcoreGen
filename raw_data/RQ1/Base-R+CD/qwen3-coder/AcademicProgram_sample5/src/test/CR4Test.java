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
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        academicProgram.addCourse(course);
        
        // Test for Monday - should return 1
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create multiple courses that don't occur on Friday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        // Remove Friday from the third course to ensure no courses on Friday
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test for Friday - should return 0
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Wednesday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Wednesday - should return 5
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses - three on Thursday, others on different days
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
            academicProgram.addCourse(course);
        }
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Thursday - should return 3
        int result = academicProgram.countCoursesOnSpecialDay("Thursday");
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test with empty academic program - should return 0
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}