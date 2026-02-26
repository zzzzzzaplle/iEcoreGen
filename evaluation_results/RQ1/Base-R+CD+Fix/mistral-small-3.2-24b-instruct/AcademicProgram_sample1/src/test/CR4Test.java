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
        assertEquals("There should be exactly 1 course on Monday", 1, result);
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
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("There should be 0 courses on Monday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Wednesday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, course5));
        
        // Execute: Call countCoursesOnSpecialDay with "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("There should be 5 courses on Wednesday", 5, result);
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
        
        // Add 5 courses that occur on other days (not Friday)
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Monday"));
        courses.add(course4);
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Tuesday"));
        courses.add(course5);
        
        Course course6 = new Course();
        course6.setCourseDays(Arrays.asList("Wednesday"));
        courses.add(course6);
        
        Course course7 = new Course();
        course7.setCourseDays(Arrays.asList("Thursday"));
        courses.add(course7);
        
        Course course8 = new Course();
        course8.setCourseDays(Arrays.asList("Saturday"));
        courses.add(course8);
        
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("There should be 3 courses on Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Call countCoursesOnSpecialDay with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("There should be 0 courses in an empty academic program", 0, result);
    }
}