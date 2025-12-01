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
    
    /**
     * Test Case 1: "Single Course on Given Day"
     * Input: Academic Program with one course that occurs on the given day, and the given specific day.
     * Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
     */
    @Test
    public void testCase1_singleCourseOnGivenDay() {
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Test for Monday
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    /**
     * Test Case 2: "No Courses on Given Day"
     * Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
     * Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
     */
    @Test
    public void testCase2_noCoursesOnGivenDay() {
        // Create courses that occur on different days (Tuesday and Wednesday)
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Tuesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test for Monday (no courses scheduled)
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify result
        assertEquals("Should return 0 courses for Monday", 0, result);
    }
    
    /**
     * Test Case 3: "Multiple Courses on Given Day"
     * Input: Academic Program with five courses, all of which occur on the given day, and the given specific day.
     * Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day.
     */
    @Test
    public void testCase3_multipleCoursesOnGivenDay() {
        // Create 5 courses that all occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Friday
        int result = academicProgram.getCoursesOnSpecificDay("Friday");
        
        // Verify result
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    /**
     * Test Case 4: "Mixed Courses on Given Day"
     * Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
     * Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
     */
    @Test
    public void testCase4_mixedCoursesOnGivenDay() {
        // Create 3 courses on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Create 5 courses on other days
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Tuesday"));
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Thursday"));
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Friday"));
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Monday", "Wednesday")); // This should also count for Wednesday
        
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        academicProgram.addCourse(course6);
        academicProgram.addCourse(course7);
        academicProgram.addCourse(course8);
        
        // Test for Wednesday (should get 3 dedicated Wednesday courses + 1 Monday/Wednesday course)
        int result = academicProgram.getCoursesOnSpecificDay("Wednesday");
        
        // Verify result - course8 also occurs on Wednesday, so total should be 4
        assertEquals("Should return 4 courses for Wednesday (3 dedicated + 1 mixed)", 4, result);
    }
    
    /**
     * Test Case 5: "Empty Academic Program"
     * Input: An empty Academic Program and a given specific day.
     * Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
     */
    @Test
    public void testCase5_emptyAcademicProgram() {
        // Test for any day with empty academic program
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify result
        assertEquals("Should return 0 courses for any day with empty program", 0, result);
    }
}