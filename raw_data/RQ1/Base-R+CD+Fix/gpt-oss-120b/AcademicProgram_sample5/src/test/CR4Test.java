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
        // Input: "Academic Program with one course that occurs on the given day, and the given specific day."
        // Expected Output: "The total number of courses returned should be 1 as there is only one course on that day."
        
        // Create a course with Monday in its course days
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add the course to the academic program
        academicProgram.getCourses().add(course);
        
        // Call method with "Monday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: "Academic Program with multiple courses, none of which occur on the given day, and the given specific day."
        // Expected Output: "The total number of courses returned should be 0 since no courses are taught on that day."
        
        // Create multiple courses that don't have Friday in their course days
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        // Note: course3 has Friday, but we'll remove it for this test
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add courses to the academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        
        // Call method with "Friday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify the result
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: "Academic Program with five courses, all of which occur on the given day, and the given specific day."
        // Expected Output: "The total number of courses returned should be 5 as all five courses are taught on that day."
        
        // Create five courses that all have Wednesday in their course days
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Wednesday"));
        
        // Add all courses to the academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        academicProgram.getCourses().add(course4);
        academicProgram.getCourses().add(course5);
        
        // Call method with "Wednesday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: "Academic Program with eight courses, out of which three occur on the given day, and the given specific day."
        // Expected Output: "The total number of courses returned should be 3 as only three courses are taught on that day."
        
        // Create eight courses
        List<Course> courses = new ArrayList<>();
        
        // Three courses with Thursday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Thursday"));
        courses.add(course1);
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        courses.add(course2);
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Thursday", "Friday"));
        courses.add(course3);
        
        // Five courses without Thursday
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        courses.add(course4);
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Tuesday", "Friday"));
        courses.add(course5);
        
        Course course6 = new Course();
        course6.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        courses.add(course6);
        
        Course course7 = new Course();
        course7.setCourseDays(Arrays.asList("Wednesday"));
        courses.add(course7);
        
        Course course8 = new Course();
        course8.setCourseDays(Arrays.asList("Monday", "Friday"));
        courses.add(course8);
        
        // Add all courses to the academic program
        academicProgram.setCourses(courses);
        
        // Call method with "Thursday" as the specific day
        int result = academicProgram.countCoursesOnSpecialDay("Thursday");
        
        // Verify the result
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: "An empty Academic Program and a given specific day."
        // Expected Output: "The total number of courses returned should be 0 as there are no courses in the academic program."
        
        // Academic program is already empty from setUp()
        
        // Call method with any day (using "Monday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}