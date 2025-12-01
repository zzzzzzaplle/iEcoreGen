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
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        // Expected Output: The total number of courses returned should be 1
        
        // Create a course taught on "Monday"
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Call the method with "Monday" as the specific day
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify the result is 1
        assertEquals("Should return 1 when there is one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        // Expected Output: The total number of courses returned should be 0
        
        // Create courses taught on "Tuesday", "Wednesday", "Thursday"
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Tuesday"));
        academicProgram.addCourse(course1);
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday"));
        academicProgram.addCourse(course2);
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Thursday"));
        academicProgram.addCourse(course3);
        
        // Call the method with "Monday" as the specific day
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify the result is 0
        assertEquals("Should return 0 when no courses are taught on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        // Expected Output: The total number of courses returned should be 5
        
        // Create five courses all taught on "Friday"
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Call the method with "Friday" as the specific day
        int result = academicProgram.getCoursesOnSpecificDay("Friday");
        
        // Verify the result is 5
        assertEquals("Should return 5 when all five courses are taught on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        // Expected Output: The total number of courses returned should be 3
        
        // Create three courses taught on "Wednesday"
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Create five courses taught on other days
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course4);
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Tuesday"));
        academicProgram.addCourse(course5);
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Thursday"));
        academicProgram.addCourse(course6);
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Friday"));
        academicProgram.addCourse(course7);
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Saturday"));
        academicProgram.addCourse(course8);
        
        // Call the method with "Wednesday" as the specific day
        int result = academicProgram.getCoursesOnSpecificDay("Wednesday");
        
        // Verify the result is 3
        assertEquals("Should return 3 when only three out of eight courses are taught on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0
        
        // Call the method with "Monday" as the specific day on empty academic program
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify the result is 0
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
    

}