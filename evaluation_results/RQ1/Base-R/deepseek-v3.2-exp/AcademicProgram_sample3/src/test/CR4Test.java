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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
        
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setDays("Monday");
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test the method with "Monday" as the given day
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
        
        // Create multiple courses that occur on different days (Tuesday, Wednesday, Thursday)
        Course course1 = new Course();
        course1.setDays("Tuesday");
        
        Course course2 = new Course();
        course2.setDays("Wednesday");
        
        Course course3 = new Course();
        course3.setDays("Thursday");
        
        // Add the courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Test the method with "Monday" as the given day (none of the courses occur on Monday)
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for Monday", 0, result);
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
            course.setDays("Friday");
            courses.add(course);
        }
        
        // Add the courses to the academic program
        academicProgram.setCourses(courses);
        
        // Test the method with "Friday" as the given day
        int result = academicProgram.getCoursesOnDay("Friday");
        
        // Verify the result
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
        
        // Create eight courses - three on Saturday, five on other days
        List<Course> courses = new ArrayList<>();
        
        // Add three courses on Saturday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays("Saturday");
            courses.add(course);
        }
        
        // Add five courses on other days
        String[] otherDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(otherDays[i]);
            courses.add(course);
        }
        
        // Add the courses to the academic program
        academicProgram.setCourses(courses);
        
        // Test the method with "Saturday" as the given day
        int result = academicProgram.getCoursesOnDay("Saturday");
        
        // Verify the result
        assertEquals("Should return 3 courses for Saturday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day.
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
        
        // Academic program is already empty from setUp() method
        // Test the method with any day (e.g., "Sunday")
        int result = academicProgram.getCoursesOnDay("Sunday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}