import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Create a course that occurs on "Monday"
        course = new Course();
        course.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course);
        
        // Test for Monday - should return 1
        int result = academicProgram.coursesOnSpecificDay("Monday");
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create multiple courses that occur on other days but not on "Wednesday"
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Friday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Thursday"));
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test for Wednesday - should return 0
        int result = academicProgram.coursesOnSpecificDay("Wednesday");
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on "Friday"
        for (int i = 0; i < 5; i++) {
            course = new Course();
            course.setDays(Arrays.asList("Friday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Friday - should return 5
        int result = academicProgram.coursesOnSpecificDay("Friday");
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses, three of which occur on "Tuesday"
        for (int i = 0; i < 3; i++) {
            course = new Course();
            course.setDays(Arrays.asList("Tuesday"));
            academicProgram.addCourse(course);
        }
        
        // Create five courses that occur on other days
        for (int i = 0; i < 5; i++) {
            course = new Course();
            course.setDays(Arrays.asList("Monday", "Wednesday", "Thursday"));
            academicProgram.addCourse(course);
        }
        
        // Test for Tuesday - should return 3
        int result = academicProgram.coursesOnSpecificDay("Tuesday");
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test with empty academic program for any day - should return 0
        int result = academicProgram.coursesOnSpecificDay("Monday");
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}