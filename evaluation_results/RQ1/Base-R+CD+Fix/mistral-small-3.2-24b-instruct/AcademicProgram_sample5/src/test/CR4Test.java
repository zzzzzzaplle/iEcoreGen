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
        // Reset academic program before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        // Expected Output: The total number of courses returned should be 1
        
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Call method with "Monday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result is 1
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none on the given day
        // Expected Output: The total number of courses returned should be 0
        
        // Create multiple courses that don't occur on "Friday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Thursday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Call method with "Friday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result is 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all on the given day
        // Expected Output: The total number of courses returned should be 5
        
        // Create five courses that all occur on "Wednesday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Wednesday", "Thursday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Call method with "Wednesday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result is 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, three on the given day
        // Expected Output: The total number of courses returned should be 3
        
        // Create eight courses with mixed days, only three on "Tuesday"
        Course course1 = new Course(); // On Tuesday
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course(); // Not on Tuesday
        course2.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course3 = new Course(); // On Tuesday
        course3.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course4 = new Course(); // Not on Tuesday
        course4.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        Course course5 = new Course(); // On Tuesday
        course5.setCourseDays(Arrays.asList("Tuesday", "Friday"));
        
        Course course6 = new Course(); // Not on Tuesday
        course6.setCourseDays(Arrays.asList("Wednesday", "Thursday"));
        
        Course course7 = new Course(); // Not on Tuesday
        course7.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course8 = new Course(); // Not on Tuesday
        course8.setCourseDays(Arrays.asList("Thursday"));
        
        // Add courses to academic program
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
        
        // Call method with "Tuesday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify result is 3
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program
        // Expected Output: The total number of courses returned should be 0
        
        // Academic program is already empty from setUp()
        // Call method with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result is 0
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}