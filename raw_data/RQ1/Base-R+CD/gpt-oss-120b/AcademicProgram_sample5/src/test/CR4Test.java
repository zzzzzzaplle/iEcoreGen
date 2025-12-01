import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day
        
        // Create a course that occurs on "Monday"
        Course course = new Course();
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        course.setCourseDays(courseDays);
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day
        
        // Create multiple courses that occur on different days (not "Tuesday")
        Course course1 = new Course();
        List<String> courseDays1 = new ArrayList<>();
        courseDays1.add("Monday");
        courseDays1.add("Wednesday");
        course1.setCourseDays(courseDays1);
        
        Course course2 = new Course();
        List<String> courseDays2 = new ArrayList<>();
        courseDays2.add("Wednesday");
        courseDays2.add("Friday");
        course2.setCourseDays(courseDays2);
        
        Course course3 = new Course();
        List<String> courseDays3 = new ArrayList<>();
        courseDays3.add("Monday");
        courseDays3.add("Friday");
        course3.setCourseDays(courseDays3);
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Count courses on "Tuesday" (none of the courses occur on Tuesday)
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify the result
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day
        
        // Create five courses that all occur on "Wednesday"
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> courseDays = new ArrayList<>();
            courseDays.add("Wednesday");
            course.setCourseDays(courseDays);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day
        
        // Create eight courses, three of which occur on "Friday"
        List<Course> courses = new ArrayList<>();
        
        // Three courses that occur on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<String> courseDays = new ArrayList<>();
            courseDays.add("Friday");
            course.setCourseDays(courseDays);
            courses.add(course);
        }
        
        // Five courses that occur on other days (not Friday)
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> courseDays = new ArrayList<>();
            courseDays.add("Monday");
            courseDays.add("Wednesday");
            course.setCourseDays(courseDays);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Count courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify the result
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program
        
        // Academic program is already empty from setUp()
        // Count courses on any day (e.g., "Monday")
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}