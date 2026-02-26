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
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        
        // Add the course to the academic program
        academicProgram.setCourses(Arrays.asList(course));
        
        // Test counting courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that exactly 1 course is returned
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on Tuesday and Wednesday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Thursday"));
        
        // Add courses to the academic program
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Test counting courses on Friday (no courses on this day)
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify that 0 courses are returned since no courses are on Friday
        assertEquals("Should return 0 courses for Friday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create 5 courses that all occur on Wednesday
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            courses.add(course);
        }
        
        // Add all courses to the academic program
        academicProgram.setCourses(courses);
        
        // Test counting courses on Wednesday
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify that all 5 courses are returned
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create 8 courses: 3 on Monday, 2 on Tuesday, 3 on Wednesday
        List<Course> courses = new ArrayList<>();
        
        // 3 courses on Monday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Monday"));
            courses.add(course);
        }
        
        // 2 courses on Tuesday
        for (int i = 0; i < 2; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Tuesday"));
            courses.add(course);
        }
        
        // 3 courses on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Wednesday"));
            courses.add(course);
        }
        
        // Add all courses to the academic program
        academicProgram.setCourses(courses);
        
        // Test counting courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that exactly 3 courses are returned for Monday
        assertEquals("Should return 3 courses for Monday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test counting courses on any day with an empty academic program
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that 0 courses are returned since the program is empty
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}