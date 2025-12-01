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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
        
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        academicProgram.getCourses().add(course);
        
        // Test with day "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
        
        // Create multiple courses that occur on different days (not including Wednesday)
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Tuesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Thursday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        academicProgram.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Test with day "Wednesday" which none of the courses have
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day.
        
        // Create five courses that all occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            academicProgram.getCourses().add(course);
        }
        
        // Test with day "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
        
        // Create eight courses, three of which occur on Tuesday
        List<Course> courses = new ArrayList<>();
        
        // Three courses that occur on Tuesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday"));
            courses.add(course);
        }
        
        // Five courses that occur on other days (not Tuesday)
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Monday"));
        courses.add(course4);
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Wednesday"));
        courses.add(course5);
        
        Course course6 = new Course();
        course6.setCourseDays(Arrays.asList("Thursday"));
        courses.add(course6);
        
        Course course7 = new Course();
        course7.setCourseDays(Arrays.asList("Friday"));
        courses.add(course7);
        
        Course course8 = new Course();
        course8.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        courses.add(course8);
        
        academicProgram.setCourses(courses);
        
        // Test with day "Tuesday"
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        assertEquals("Should return 3 when exactly three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day.
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
        
        // academicProgram is already empty due to @Before setup
        
        // Test with any day - should return 0 since there are no courses
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}