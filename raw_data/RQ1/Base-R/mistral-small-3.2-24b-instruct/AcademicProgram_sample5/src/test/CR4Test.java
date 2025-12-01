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
        // Setup: Academic Program with one course that occurs on the given day
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.setCourses(Arrays.asList(course));
        
        // Test: Get number of courses on "Monday"
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 when there is one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Test: Get number of courses on "Monday"
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses are on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Wednesday"));
        
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Wednesday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Wednesday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, course5));
        
        // Test: Get number of courses on "Wednesday"
        int result = academicProgram.getNumberOfCoursesOnDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses are on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Friday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Friday"));
        
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Friday"));
        
        Course course6 = new Course();
        course6.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course7 = new Course();
        course7.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course8 = new Course();
        course8.setDays(Arrays.asList("Monday", "Wednesday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8));
        
        // Test: Get number of courses on "Friday"
        int result = academicProgram.getNumberOfCoursesOnDay("Friday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 when three out of eight courses are on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Test: Get number of courses on any day (using "Monday" as example)
        int result = academicProgram.getNumberOfCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}