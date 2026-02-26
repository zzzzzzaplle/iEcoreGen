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
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 when there is one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none occurring on the given day
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
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
        
        // Execute: Call countCoursesOnSpecialDay with "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three occurring on the given day
        List<Course> courses = new ArrayList<>();
        
        // Add 3 courses that occur on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            courses.add(course);
        }
        
        // Add 5 courses that occur on other days
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses)
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Call countCoursesOnSpecialDay with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}