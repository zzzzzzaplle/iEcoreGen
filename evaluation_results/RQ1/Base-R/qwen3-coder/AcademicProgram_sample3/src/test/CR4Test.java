import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        course.setDays("Monday");
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Get courses on "Monday"
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify: Should return 1 as there is only one course on Monday
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Create academic program with multiple courses that occur on "Tuesday" and "Wednesday", but not on "Monday"
        Course course1 = new Course();
        course1.setDays("Tuesday");
        Course course2 = new Course();
        course2.setDays("Wednesday");
        Course course3 = new Course();
        course3.setDays("Tuesday");
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute: Get courses on "Monday"
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify: Should return 0 since no courses are taught on Monday
        assertEquals("Should return 0 courses for Monday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Create academic program with five courses that all occur on "Friday"
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays("Friday");
            courses.add(course);
        }
        academicProgram.setCourses(courses);
        
        // Execute: Get courses on "Friday"
        int result = academicProgram.getCoursesOnDay("Friday");
        
        // Verify: Should return 5 as all five courses are taught on Friday
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Create academic program with eight courses, three of which occur on "Wednesday"
        List<Course> courses = new ArrayList<>();
        
        // Add 3 courses on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setDays("Wednesday");
            courses.add(course);
        }
        
        // Add 5 courses on other days
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setDays("Thursday");
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Execute: Get courses on "Wednesday"
        int result = academicProgram.getCoursesOnDay("Wednesday");
        
        // Verify: Should return 3 as only three courses are taught on Wednesday
        assertEquals("Should return 3 courses for Wednesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Academic program is already empty from @Before
        
        // Execute: Get courses on "Tuesday"
        int result = academicProgram.getCoursesOnDay("Tuesday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}