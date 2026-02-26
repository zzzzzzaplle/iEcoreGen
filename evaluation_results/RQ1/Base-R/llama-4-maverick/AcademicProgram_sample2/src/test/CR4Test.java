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
        course.setDays(Arrays.asList("Monday"));
        
        // Add course to academic program
        academicProgram.addCourse(course);
        
        // Test for Monday - should return 1 course
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create multiple courses that occur on different days, but not on Wednesday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Friday"));
        
        // Add courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test for a day not in any course (e.g., "Saturday")
        int result = academicProgram.coursesOnSpecificDay("Saturday");
        
        // Verify the result
        assertEquals("Should return 0 courses for Saturday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Friday - should return 5 courses
        int result = academicProgram.coursesOnSpecificDay("Friday");
        
        // Verify the result
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses with mixed days
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        
        for (int i = 0; i < 8; i++) {
            Course course = new Course();
            // Three courses will have Wednesday, others will have different days
            if (i < 3) {
                course.setDays(Arrays.asList("Wednesday"));
            } else {
                course.setDays(Arrays.asList(days[i % days.length]));
            }
            academicProgram.addCourse(course);
        }
        
        // Test for Wednesday - should return 3 courses
        int result = academicProgram.coursesOnSpecificDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 3 courses for Wednesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Test for any day - should return 0 courses
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}