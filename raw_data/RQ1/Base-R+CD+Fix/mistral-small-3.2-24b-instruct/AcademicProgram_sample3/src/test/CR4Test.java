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
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Single course on given day should return 1", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 0 since no courses are taught on Monday
        assertEquals("No courses on given day should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            courses.add(course);
        }
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: Should return 5 as all five courses are taught on Wednesday
        assertEquals("All five courses on given day should return 5", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
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
        
        // Execute: Count courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: Should return 3 as only three courses are taught on Friday
        assertEquals("Three out of eight courses on given day should return 3", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses added)
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Count courses on "Thursday"
        int result = academicProgram.countCoursesOnSpecialDay("Thursday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Empty academic program should return 0", 0, result);
    }
}