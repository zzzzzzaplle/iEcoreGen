import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private Course course1, course2, course3, course4, course5, course6, course7, course8;
    
    @Before
    public void setUp() {
        // Initialize academic program and courses for test setup
        academicProgram = new AcademicProgram();
        
        // Create sample courses with different day configurations
        course1 = new Course();
        course1.setDays(new ArrayList<>(Arrays.asList("Monday", "Wednesday")));
        
        course2 = new Course();
        course2.setDays(new ArrayList<>(Arrays.asList("Tuesday", "Thursday")));
        
        course3 = new Course();
        course3.setDays(new ArrayList<>(Arrays.asList("Monday", "Friday")));
        
        course4 = new Course();
        course4.setDays(new ArrayList<>(Arrays.asList("Wednesday", "Friday")));
        
        course5 = new Course();
        course5.setDays(new ArrayList<>(Arrays.asList("Monday", "Wednesday", "Friday")));
        
        course6 = new Course();
        course6.setDays(new ArrayList<>(Arrays.asList("Tuesday")));
        
        course7 = new Course();
        course7.setDays(new ArrayList<>(Arrays.asList("Thursday")));
        
        course8 = new Course();
        course8.setDays(new ArrayList<>(Arrays.asList("Monday")));
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Setup: Academic Program with one course that occurs on the given day
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course1)));
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify: Should return 1 as there is only one course on Monday
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Setup: Academic Program with multiple courses, none on the given day
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course2, course6, course7)));
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify: Should return 0 since no courses are taught on Monday
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Setup: Academic Program with five courses, all on the given day
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course1, course3, course5, course8)));
        // Note: course1, course3, course5, and course8 all have Monday, but we need 5 courses
        // Create one more course with Monday
        Course course9 = new Course();
        course9.setDays(new ArrayList<>(Arrays.asList("Monday")));
        academicProgram.getCourses().add(course9);
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify: Should return 5 as all five courses are taught on Monday
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Setup: Academic Program with eight courses, three on the given day
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(
            course1, course2, course3, course4, course5, course6, course7, course8
        )));
        
        // Execute: Call method with specific day "Wednesday"
        int result = academicProgram.coursesOnSpecificDay("Wednesday");
        
        // Verify: Should return 3 as only three courses are taught on Wednesday
        // (course1, course4, and course5 have Wednesday)
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Setup: Empty academic program
        academicProgram.setCourses(new ArrayList<>());
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.coursesOnSpecificDay("Monday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}