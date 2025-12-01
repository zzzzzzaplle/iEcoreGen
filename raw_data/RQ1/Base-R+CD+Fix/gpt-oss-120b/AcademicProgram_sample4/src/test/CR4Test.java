import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram program;
    
    @Before
    public void setUp() {
        program = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to the program
        program.addCourse(course);
        
        // Call the method with "Monday" as the specific day
        int result = program.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 1
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        
        // Create first course that occurs on Tuesday and Thursday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        // Create second course that occurs on Wednesday and Friday
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        // Add courses to the program
        program.addCourse(course1);
        program.addCourse(course2);
        
        // Call the method with "Monday" as the specific day (none of the courses occur on Monday)
        int result = program.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        
        // Create five courses that all occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday", "Monday"));
            program.addCourse(course);
        }
        
        // Call the method with "Friday" as the specific day
        int result = program.countCoursesOnSpecialDay("Friday");
        
        // Expected Output: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        
        // Create three courses that occur on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday", "Friday"));
            program.addCourse(course);
        }
        
        // Create five courses that occur on other days but not Wednesday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Tuesday", "Thursday"));
            program.addCourse(course);
        }
        
        // Call the method with "Wednesday" as the specific day
        int result = program.countCoursesOnSpecialDay("Wednesday");
        
        // Expected Output: The total number of courses returned should be 3
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        
        // Program is already empty from setUp()
        
        // Call the method with any specific day
        int result = program.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}