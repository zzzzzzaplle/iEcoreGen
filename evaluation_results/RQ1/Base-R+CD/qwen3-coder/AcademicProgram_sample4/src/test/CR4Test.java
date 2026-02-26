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
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add the course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify only one course is found on Monday
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create multiple courses that don't occur on "Friday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        // Add courses courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify no courses are found on Friday
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on "Wednesday"
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Wednesday", "Thursday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Wednesday"));
        
        // Add all courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify all five courses are found on Wednesday
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses with mixed days
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Wednesday", "Thursday"));
        
        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Monday"));
        
        Course course6 = new Course();
        course6.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course7 = new Course();
        course7.setCourseDays(Arrays.asList("Thursday"));
        
        Course course8 = new Course();
        course8.setCourseDays(Arrays.asList("Friday"));
        
        // Add all courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify only three courses are found on Monday
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Test counting courses on any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify no courses are found in empty academic program
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}