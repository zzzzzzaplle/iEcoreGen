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
        // Input: Academic Program with one course that occurs on the given day
        Course course = new Course();
        course.setDaysOfCourse(Arrays.asList("Monday"));
        academicProgram.setCourses(Arrays.asList(course));
        
        // Given specific day
        String day = "Monday";
        
        // Expected Output: The total number of courses returned should be 1
        int result = academicProgram.countCoursesOnDay(day);
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        Course course1 = new Course();
        course1.setDaysOfCourse(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setDaysOfCourse(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setDaysOfCourse(Arrays.asList("Monday", "Wednesday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Given specific day
        String day = "Sunday";
        
        // Expected Output: The total number of courses returned should be 0
        int result = academicProgram.countCoursesOnDay(day);
        assertEquals("Should return 0 courses on Sunday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        
        // Create 5 courses all taught on Tuesday
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDaysOfCourse(Arrays.asList("Tuesday", "Thursday"));
            courses.add(course);
        }
        academicProgram.setCourses(courses);
        
        // Given specific day
        String day = "Tuesday";
        
        // Expected Output: The total number of courses returned should be 5
        int result = academicProgram.countCoursesOnDay(day);
        assertEquals("Should return 5 courses on Tuesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        
        List<Course> courses = new ArrayList<>();
        
        // Create 3 courses that occur on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDaysOfCourse(Arrays.asList("Monday", "Wednesday", "Friday"));
            courses.add(course);
        }
        
        // Create 5 courses that don't occur on Friday
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDaysOfCourse(Arrays.asList("Monday", "Wednesday"));
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Given specific day
        String day = "Friday";
        
        // Expected Output: The total number of courses returned should be 3
        int result = academicProgram.countCoursesOnDay(day);
        assertEquals("Should return 3 courses on Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Given specific day
        String day = "Monday";
        
        // Expected Output: The total number of courses returned should be 0
        int result = academicProgram.countCoursesOnDay(day);
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}