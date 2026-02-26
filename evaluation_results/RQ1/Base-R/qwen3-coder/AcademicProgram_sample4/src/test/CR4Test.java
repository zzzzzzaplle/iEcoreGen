import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day
        
        // Create a course that occurs on Monday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Test for Monday
        int result = program.getCoursesOnDay("Monday");
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day
        
        // Create courses that occur on Monday and Wednesday, but not on Friday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        // Add courses to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        program.setCourses(courses);
        
        // Test for Saturday (which none of the courses have)
        int result = program.getCoursesOnDay("Saturday");
        assertEquals("Should return 0 courses for Saturday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day
        
        // Create five courses that all occur on Wednesday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Wednesday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        // Add all five courses to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        program.setCourses(courses);
        
        // Test for Wednesday
        int result = program.getCoursesOnDay("Wednesday");
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day
        
        // Create eight courses
        List<Course> courses = new ArrayList<>();
        
        // Three courses that occur on Friday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Friday"));
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday", "Friday"));
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Friday"));
        courses.add(course3);
        
        // Five courses that don't occur on Friday
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday", "Wednesday"));
        courses.add(course4);
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Tuesday", "Thursday"));
        courses.add(course5);
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Monday"));
        courses.add(course6);
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Tuesday"));
        courses.add(course7);
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Wednesday", "Thursday"));
        courses.add(course8);
        
        program.setCourses(courses);
        
        // Test for Friday
        int result = program.getCoursesOnDay("Friday");
        assertEquals("Should return 3 courses for Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program
        
        // Program is already empty from setUp()
        // Test for any day (using "Monday" as example)
        int result = program.getCoursesOnDay("Monday");
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}