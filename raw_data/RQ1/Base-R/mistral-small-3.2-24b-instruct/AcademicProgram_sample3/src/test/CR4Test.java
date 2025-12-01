import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private Course course6;
    private Course course7;
    private Course course8;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        
        // Initialize courses
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        course5 = new Course();
        course6 = new Course();
        course7 = new Course();
        course8 = new Course();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: Single Course on Given Day
        // Setup: Academic Program with one course that occurs on the given day
        List<String> days = Arrays.asList("Monday");
        course1.setDays(days);
        academicProgram.addCourse(course1);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: No Courses on Given Day
        // Setup: Academic Program with multiple courses, none on the given day
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        course3.setDays(Arrays.asList("Wednesday", "Friday"));
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Execute: Count courses on "Saturday"
        int result = academicProgram.countCoursesOnDay("Saturday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: Multiple Courses on Given Day
        // Setup: Academic Program with five courses all on the given day
        List<String> wednesdayDays = Arrays.asList("Wednesday");
        
        course1.setDays(wednesdayDays);
        course2.setDays(wednesdayDays);
        course3.setDays(wednesdayDays);
        course4.setDays(wednesdayDays);
        course5.setDays(wednesdayDays);
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: Mixed Courses on Given Day
        // Setup: Academic Program with eight courses, three on the given day
        List<String> fridayDays = Arrays.asList("Friday");
        List<String> otherDays = Arrays.asList("Monday", "Wednesday");
        
        // Three courses on Friday
        course1.setDays(fridayDays);
        course2.setDays(fridayDays);
        course3.setDays(fridayDays);
        
        // Five courses not on Friday
        course4.setDays(otherDays);
        course5.setDays(otherDays);
        course6.setDays(otherDays);
        course7.setDays(otherDays);
        course8.setDays(otherDays);
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        academicProgram.addCourse(course6);
        academicProgram.addCourse(course7);
        academicProgram.addCourse(course8);
        
        // Execute: Count courses on "Friday"
        int result = academicProgram.countCoursesOnDay("Friday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 when exactly three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: Empty Academic Program
        // Setup: Empty Academic Program (no courses added)
        // No setup needed - academicProgram is already empty
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}