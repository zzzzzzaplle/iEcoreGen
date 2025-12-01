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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day.
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday"));
        academicProgram.setCourses(Arrays.asList(course));
        
        // Execute the method under test
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day.
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Friday"));
        
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute the method under test
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day.
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day.
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
        
        // Execute the method under test
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day.
        assertEquals(5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day.
        List<Course> courses = new ArrayList<>();
        
        // Three courses on Friday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCourseDays(Arrays.asList("Friday"));
            courses.add(course);
        }
        
        // Five courses not on Friday
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
        
        // Execute the method under test
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Input: An empty Academic Program and a given specific day.
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute the method under test
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program.
        assertEquals(0, result);
    }
}