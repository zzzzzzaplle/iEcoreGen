import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_singleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Given specific day is "Monday"
        String day = "Monday";
        
        // Expected Output: The total number of courses returned should be 1
        int result = academicProgram.coursesOnDay(day);
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_noCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        academicProgram.addCourse(course1);
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        academicProgram.addCourse(course2);
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Friday"));
        academicProgram.addCourse(course3);
        
        // Given specific day is "Sunday" (none of the courses occur on Sunday)
        String day = "Sunday";
        
        // Expected Output: The total number of courses returned should be 0
        int result = academicProgram.coursesOnDay(day);
        assertEquals("Should return 0 courses for Sunday", 0, result);
    }
    
    @Test
    public void testCase3_multipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all on the given day
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Given specific day is "Wednesday"
        String day = "Wednesday";
        
        // Expected Output: The total number of courses returned should be 5
        int result = academicProgram.coursesOnDay(day);
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_mixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, three on the given day
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Monday", "Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Given specific day is "Friday"
        String day = "Friday";
        
        // Expected Output: The total number of courses returned should be 3
        int result = academicProgram.coursesOnDay(day);
        assertEquals("Should return 3 courses for Friday", 3, result);
    }
    
    @Test
    public void testCase5_emptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: Empty Academic Program
        // Academic program is already empty from setUp()
        
        // Given specific day is "Tuesday"
        String day = "Tuesday";
        
        // Expected Output: The total number of courses returned should be 0
        int result = academicProgram.coursesOnDay(day);
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}