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
        // Setup: Academic Program with one course that occurs on the given day
        Course course = new Course();
        List<String> days = new ArrayList<>();
        days.add("Monday");
        course.setCourseDays(days);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        List<String> days1 = new ArrayList<>();
        days1.add("Tuesday");
        course1.setCourseDays(days1);
        
        Course course2 = new Course();
        List<String> days2 = new ArrayList<>();
        days2.add("Wednesday");
        course2.setCourseDays(days2);
        
        Course course3 = new Course();
        List<String> days3 = new ArrayList<>();
        days3.add("Thursday");
        course3.setCourseDays(days3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for Friday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add("Wednesday");
            course.setCourseDays(days);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        List<Course> courses = new ArrayList<>();
        
        // Add 3 courses on Thursday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add("Thursday");
            course.setCourseDays(days);
            courses.add(course);
        }
        
        // Add 5 courses on other days
        String[] otherDays = {"Monday", "Tuesday", "Wednesday", "Friday", "Saturday"};
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add(otherDays[i]);
            course.setCourseDays(days);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Execute: Call countCoursesOnSpecialDay with "Thursday"
        int result = academicProgram.countCoursesOnSpecialDay("Thursday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 courses for Thursday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (already empty from setUp)
        
        // Execute: Call countCoursesOnSpecialDay with any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}