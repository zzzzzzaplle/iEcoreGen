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
        // Initialize a fresh AcademicProgram before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Create Academic Program with one course that occurs on the given day
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Single course on Monday should return 1", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Create Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Tuesday", "Thursday"));
        academicProgram.addCourse(course1);
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday", "Friday"));
        academicProgram.addCourse(course2);
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Tuesday", "Thursday"));
        academicProgram.addCourse(course3);
        
        // Execute: Call method with specific day "Monday"
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify: Should return 0 since no courses are taught on Monday
        assertEquals("No courses on Monday should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Create Academic Program with five courses, all on the given day
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Wednesday"));
            academicProgram.addCourse(course);
        }
        
        // Execute: Call method with specific day "Wednesday"
        int result = academicProgram.getCoursesOnSpecificDay("Wednesday");
        
        // Verify: Should return 5 as all five courses are taught on Wednesday
        assertEquals("Five courses on Wednesday should return 5", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Create Academic Program with eight courses, three on the given day
        // Three courses on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Five courses on other days (not Friday)
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Monday", "Wednesday"));
        academicProgram.addCourse(course4);
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Tuesday", "Thursday"));
        academicProgram.addCourse(course5);
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course6);
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Tuesday"));
        academicProgram.addCourse(course7);
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Thursday"));
        academicProgram.addCourse(course8);
        
        // Execute: Call method with specific day "Friday"
        int result = academicProgram.getCoursesOnSpecificDay("Friday");
        
        // Verify: Should return 3 as only three courses are taught on Friday
        assertEquals("Three courses on Friday should return 3", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Academic Program is already empty from @Before setup
        
        // Execute: Call method with any specific day (using "Monday" as example)
        int result = academicProgram.getCoursesOnSpecificDay("Monday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Empty academic program should return 0", 0, result);
    }
}