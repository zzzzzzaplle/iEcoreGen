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
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        
        // Create a course that occurs on "Monday"
        Course course = new Course();
        course.setCode("CS101");
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test for Monday - should return 1
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        
        // Create courses that occur on different days but not on "Friday"
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCode("MATH201");
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Test for Friday - should return 0
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        
        // Create 5 courses that all occur on "Wednesday"
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCode("MATH201");
        course2.setCourseDays(Arrays.asList("Tuesday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setCode("PHY301");
        course3.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setCode("CHEM401");
        course4.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course5 = new Course();
        course5.setCode("BIO501");
        course5.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test for Wednesday - should return 5
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        
        // Create 8 courses with mixed days
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCode("MATH201");
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCode("PHY301");
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        Course course4 = new Course();
        course4.setCode("CHEM401");
        course4.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course5 = new Course();
        course5.setCode("BIO501");
        course5.setCourseDays(Arrays.asList("Monday"));
        
        Course course6 = new Course();
        course6.setCode("HIST601");
        course6.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course7 = new Course();
        course7.setCode("ART701");
        course7.setCourseDays(Arrays.asList("Thursday"));
        
        Course course8 = new Course();
        course8.setCode("MUS801");
        course8.setCourseDays(Arrays.asList("Friday"));
        
        // Add courses to academic program
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
        
        // Test for Monday - should return 3 (courses 1, 3, and 5)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        
        // Academic program is already empty from setUp()
        // No need to add any courses
        
        // Test for any day (e.g., "Monday") - should return 0
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}