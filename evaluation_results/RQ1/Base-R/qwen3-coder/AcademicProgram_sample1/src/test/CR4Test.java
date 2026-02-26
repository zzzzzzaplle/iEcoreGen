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
        // Initialize academic program before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day
        
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Test with Monday as the specific day
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day
        
        // Create multiple courses that occur on different days (not Tuesday)
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Thursday"));
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test with Tuesday as the specific day (none of the courses occur on Tuesday)
        int result = academicProgram.getCoursesOnDay("Tuesday");
        
        // Verify the result
        assertEquals("Should return 0 courses for Tuesday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day
        
        // Create five courses that all occur on Wednesday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Test with Wednesday as the specific day
        int result = academicProgram.getCoursesOnDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day
        
        // Create three courses that occur on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Create five courses that occur on other days (not Friday)
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Monday", "Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Test with Friday as the specific day
        int result = academicProgram.getCoursesOnDay("Friday");
        
        // Verify the result
        assertEquals("Should return 3 courses for Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program
        
        // Academic program is already empty (no courses added)
        // Test with any day (e.g., "Monday")
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}