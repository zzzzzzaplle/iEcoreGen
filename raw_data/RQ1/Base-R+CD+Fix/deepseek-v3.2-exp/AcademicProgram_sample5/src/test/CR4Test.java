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
        // Setup: Create academic program with one course that occurs on "Monday"
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        academicProgram.setCourses(Arrays.asList(course));
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Should return 1 for single course on given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Create academic program with multiple courses, none on "Wednesday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Tuesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Thursday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: Should return 0 since no courses are taught on that day
        assertEquals("Should return 0 when no courses on given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Create academic program with five courses, all on "Friday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Friday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Friday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Friday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Friday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3, course4, course5));
        
        // Execute: Count courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: Should return 5 as all five courses are taught on that day
        assertEquals("Should return 5 when all courses on given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Create academic program with eight courses, three on "Tuesday"
        List<Course> courses = new ArrayList<>();
        
        // Three courses on Tuesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday"));
            courses.add(course);
        }
        
        // Five courses not on Tuesday
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Monday"));
        courses.add(course4);
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Wednesday"));
        courses.add(course5);
        
        Course course6 = new Course();
        course6.setCourseDays(Arrays.asList("Thursday"));
        courses.add(course6);
        
        Course course7 = new Course();
        course7.setCourseDays(Arrays.asList("Friday"));
        courses.add(course7);
        
        Course course8 = new Course();
        course8.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        courses.add(course8);
        
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Tuesday"
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify: Should return 3 as only three courses are taught on that day
        assertEquals("Should return 3 when mixed courses on given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Academic program is already empty from setUp()
        
        // Execute: Count courses on any day (using "Monday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 for empty academic program", 0, result);
    }
}