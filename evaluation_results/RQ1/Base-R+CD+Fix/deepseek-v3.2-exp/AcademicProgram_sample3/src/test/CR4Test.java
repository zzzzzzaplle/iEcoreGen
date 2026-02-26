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
        // Create a course that occurs on Monday
        Course course = new Course();
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        course.setCourseDays(courseDays);
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Count courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that exactly one course is found on Monday
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on Tuesday and Wednesday, but not Monday
        Course course1 = new Course();
        List<String> courseDays1 = new ArrayList<>();
        courseDays1.add("Tuesday");
        course1.setCourseDays(courseDays1);
        
        Course course2 = new Course();
        List<String> courseDays2 = new ArrayList<>();
        courseDays2.add("Wednesday");
        course2.setCourseDays(courseDays2);
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Count courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that no courses are found on Monday
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Friday
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> courseDays = new ArrayList<>();
            courseDays.add("Friday");
            course.setCourseDays(courseDays);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Count courses on Friday
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify that all five courses are found on Friday
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses, three of which occur on Wednesday
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Course course = new Course();
            List<String> courseDays = new ArrayList<>();
            
            // Three courses on Wednesday, others on different days
            if (i < 3) {
                courseDays.add("Wednesday");
            } else {
                courseDays.add("Thursday");
            }
            
            course.setCourseDays(courseDays);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Count courses on Wednesday
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify that exactly three courses are found on Wednesday
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Count courses on any day (using "Monday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify that no courses are found in an empty academic program
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}