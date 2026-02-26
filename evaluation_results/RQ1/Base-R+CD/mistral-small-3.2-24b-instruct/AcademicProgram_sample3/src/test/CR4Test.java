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
        List<String> days = new ArrayList<>(Arrays.asList("Monday", "Wednesday"));
        course.setCourseDays(days);
        academicProgram.getCourses().add(course);
        
        // Input: given specific day "Monday"
        String day = "Monday";
        
        // Execute the method
        int result = academicProgram.countCoursesOnSpecialDay(day);
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setCourseDays(new ArrayList<>(Arrays.asList("Tuesday", "Thursday")));
        
        Course course2 = new Course();
        course2.setCourseDays(new ArrayList<>(Arrays.asList("Wednesday", "Friday")));
        
        Course course3 = new Course();
        course3.setCourseDays(new ArrayList<>(Arrays.asList("Tuesday", "Friday")));
        
        academicProgram.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Input: given specific day "Monday"
        String day = "Monday";
        
        // Execute the method
        int result = academicProgram.countCoursesOnSpecialDay(day);
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Course course1 = new Course();
        course1.setCourseDays(new ArrayList<>(Arrays.asList("Monday")));
        
        Course course2 = new Course();
        course2.setCourseDays(new ArrayList<>(Arrays.asList("Monday", "Wednesday")));
        
        Course course3 = new Course();
        course3.setCourseDays(new ArrayList<>(Arrays.asList("Monday", "Friday")));
        
        Course course4 = new Course();
        course4.setCourseDays(new ArrayList<>(Arrays.asList("Monday")));
        
        Course course5 = new Course();
        course5.setCourseDays(new ArrayList<>(Arrays.asList("Monday", "Tuesday")));
        
        academicProgram.getCourses().addAll(Arrays.asList(course1, course2, course3, course4, course5));
        
        // Input: given specific day "Monday"
        String day = "Monday";
        
        // Execute the method
        int result = academicProgram.countCoursesOnSpecialDay(day);
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        Course course1 = new Course(); // On Monday
        course1.setCourseDays(new ArrayList<>(Arrays.asList("Monday")));
        
        Course course2 = new Course(); // Not on Monday
        course2.setCourseDays(new ArrayList<>(Arrays.asList("Tuesday", "Thursday")));
        
        Course course3 = new Course(); // On Monday
        course3.setCourseDays(new ArrayList<>(Arrays.asList("Monday", "Wednesday")));
        
        Course course4 = new Course(); // Not on Monday
        course4.setCourseDays(new ArrayList<>(Arrays.asList("Wednesday")));
        
        Course course5 = new Course(); // Not on Monday
        course5.setCourseDays(new ArrayList<>(Arrays.asList("Tuesday", "Friday")));
        
        Course course6 = new Course(); // On Monday
        course6.setCourseDays(new ArrayList<>(Arrays.asList("Monday")));
        
        Course course7 = new Course(); // Not on Monday
        course7.setCourseDays(new ArrayList<>(Arrays.asList("Thursday")));
        
        Course course8 = new Course(); // Not on Monday
        course8.setCourseDays(new ArrayList<>(Arrays.asList("Friday")));
        
        academicProgram.getCourses().addAll(Arrays.asList(course1, course2, course3, course4, 
                                                         course5, course6, course7, course8));
        
        // Input: given specific day "Monday"
        String day = "Monday";
        
        // Execute the method
        int result = academicProgram.countCoursesOnSpecialDay(day);
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 when three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses added)
        
        // Input: given specific day "Monday"
        String day = "Monday";
        
        // Execute the method
        int result = academicProgram.countCoursesOnSpecialDay(day);
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}