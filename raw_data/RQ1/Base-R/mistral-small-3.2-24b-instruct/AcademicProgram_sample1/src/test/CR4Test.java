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
        // Initialize AcademicProgram before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
        
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test with the specific day "Monday"
        int result = academicProgram.findCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
        
        // Create multiple courses that don't occur on "Saturday"
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Test with the specific day "Saturday"
        int result = academicProgram.findCoursesOnDay("Saturday");
        
        // Verify the result
        assertEquals("Should return 0 courses on Saturday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day.
        
        // Create five courses that all occur on "Wednesday"
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Wednesday"));
        
        // Add all five courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test with the specific day "Wednesday"
        int result = academicProgram.findCoursesOnDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 courses on Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
        
        // Create eight courses, with only three occurring on "Friday"
        Course course1 = new Course(); // Occurs on Friday
        course1.setDays(Arrays.asList("Monday", "Friday"));
        
        Course course2 = new Course(); // Does not occur on Friday
        course2.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course3 = new Course(); // Occurs on Friday
        course3.setDays(Arrays.asList("Tuesday", "Friday"));
        
        Course course4 = new Course(); // Does not occur on Friday
        course4.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course5 = new Course(); // Occurs on Friday
        course5.setDays(Arrays.asList("Friday"));
        
        Course course6 = new Course(); // Does not occur on Friday
        course6.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course7 = new Course(); // Does not occur on Friday
        course7.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course8 = new Course(); // Does not occur on Friday
        course8.setDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add all eight courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        academicProgram.setCourses(courses);
        
        // Test with the specific day "Friday"
        int result = academicProgram.findCoursesOnDay("Friday");
        
        // Verify the result
        assertEquals("Should return 3 courses on Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day.
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
        
        // Academic program is already empty from setUp()
        // Test with any specific day (using "Monday" as an example)
        int result = academicProgram.findCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}