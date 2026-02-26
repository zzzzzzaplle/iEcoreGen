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
        // Input: Academic Program with one course that occurs on the given day
        // Expected Output: The total number of courses returned should be 1
        
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setDays("Monday");
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Call the method with "Monday" as the specific day
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        // Expected Output: The total number of courses returned should be 0
        
        // Create courses that occur on different days
        Course course1 = new Course();
        course1.setDays("Tuesday");
        
        Course course2 = new Course();
        course2.setDays("Wednesday");
        
        Course course3 = new Course();
        course3.setDays("Thursday");
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Call the method with "Friday" as the specific day
        int result = academicProgram.getCoursesOnDay("Friday");
        
        // Verify the result
        assertEquals("Should return 0 courses for Friday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        // Expected Output: The total number of courses returned should be 5
        
        // Create five courses that all occur on "Wednesday"
        Course course1 = new Course();
        course1.setDays("Wednesday");
        
        Course course2 = new Course();
        course2.setDays("Wednesday");
        
        Course course3 = new Course();
        course3.setDays("Wednesday");
        
        Course course4 = new Course();
        course4.setDays("Wednesday");
        
        Course course5 = new Course();
        course5.setDays("Wednesday");
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Call the method with "Wednesday" as the specific day
        int result = academicProgram.getCoursesOnDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        // Expected Output: The total number of courses returned should be 3
        
        // Create eight courses - three occur on "Thursday", others on different days
        Course course1 = new Course();
        course1.setDays("Thursday");
        
        Course course2 = new Course();
        course2.setDays("Thursday");
        
        Course course3 = new Course();
        course3.setDays("Thursday");
        
        Course course4 = new Course();
        course4.setDays("Monday");
        
        Course course5 = new Course();
        course5.setDays("Tuesday");
        
        Course course6 = new Course();
        course6.setDays("Wednesday");
        
        Course course7 = new Course();
        course7.setDays("Friday");
        
        Course course8 = new Course();
        course8.setDays("Friday");
        
        // Add courses to the academic program
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
        
        // Call the method with "Thursday" as the specific day
        int result = academicProgram.getCoursesOnDay("Thursday");
        
        // Verify the result
        assertEquals("Should return 3 courses for Thursday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0
        
        // Academic program is already empty from setUp()
        // Call the method with any day (e.g., "Monday")
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}