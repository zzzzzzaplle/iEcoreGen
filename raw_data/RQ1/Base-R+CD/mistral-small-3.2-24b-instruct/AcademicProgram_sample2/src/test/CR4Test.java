import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Academic Program with one course that occurs on the given day
        Course course = createCourseWithDays("Monday");
        academicProgram.setCourses(Arrays.asList(course));
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = createCourseWithDays("Tuesday");
        Course course2 = createCourseWithDays("Wednesday");
        Course course3 = createCourseWithDays("Thursday");
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for Monday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Course course1 = createCourseWithDays("Monday");
        Course course2 = createCourseWithDays("Monday");
        Course course3 = createCourseWithDays("Monday");
        Course course4 = createCourseWithDays("Monday");
        Course course5 = createCourseWithDays("Monday");
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, course5));
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 courses for Monday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        Course course1 = createCourseWithDays("Monday");
        Course course2 = createCourseWithDays("Monday");
        Course course3 = createCourseWithDays("Monday");
        Course course4 = createCourseWithDays("Tuesday");
        Course course5 = createCourseWithDays("Wednesday");
        Course course6 = createCourseWithDays("Thursday");
        Course course7 = createCourseWithDays("Friday");
        Course course8 = createCourseWithDays("Saturday");
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, 
                                               course5, course6, course7, course8));
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 courses for Monday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Call countCoursesOnSpecialDay with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
    
    // Helper method to create a course with specified days
    private Course createCourseWithDays(String... days) {
        Course course = new Course();
        course.setCourseDays(Arrays.asList(days));
        return course;
    }
}