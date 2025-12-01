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
        // Initialize a fresh AcademicProgram before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
        
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        
        // Add the course to the academic program
        academicProgram.setCourses(Arrays.asList(course));
        
        // Call the method with "Monday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
        
        // Create courses that occur on Tuesday and Wednesday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Thursday"));
        
        // Add courses to the academic program
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Call the method with "Monday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day.
        
        // Create five courses that all occur on Friday
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            courses.add(course);
        }
        
        // Add courses to the academic program
        academicProgram.setCourses(courses);
        
        // Call the method with "Friday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify the result
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
        
        // Create eight courses - three on Wednesday, others on different days
        List<Course> courses = new ArrayList<>();
        
        // Three courses on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            courses.add(course);
        }
        
        // Five courses on other days
        String[] otherDays = {"Monday", "Tuesday", "Thursday", "Friday", "Saturday"};
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList(otherDays[i]));
            courses.add(course);
        }
        
        // Add courses to the academic program
        academicProgram.setCourses(courses);
        
        // Call the method with "Wednesday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day.
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
        
        // AcademicProgram is already empty from setUp()
        // Call the method with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}