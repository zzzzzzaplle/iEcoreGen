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
        course.setCourseDays(Arrays.asList("Monday"));
        academicProgram.setCourses(Arrays.asList(course));
        
        // Test: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Should return 1 for single course on given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Friday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Test: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 0 since no courses are taught on Monday
        assertEquals("Should return 0 when no courses on given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all occurring on the given day
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            courses.add(course);
        }
        academicProgram.setCourses(courses);
        
        // Test: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: Should return 5 as all five courses are taught on that day
        assertEquals("Should return 5 when all courses are on given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three of which occur on the given day
        List<Course> courses = new ArrayList<>();
        
        // Add 3 courses that occur on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            courses.add(course);
        }
        
        // Add 5 courses that occur on other days (not Friday)
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Test: Count courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: Should return 3 as only three courses are taught on Friday
        assertEquals("Should return 3 when only some courses are on given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses)
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Test: Count courses on any day (using "Tuesday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 for empty academic program", 0, result);
    }
}