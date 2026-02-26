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
        course.setDays(days);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test: Find courses on "Monday"
        int result = academicProgram.coursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        List<String> days1 = new ArrayList<>();
        days1.add("Tuesday");
        course1.setDays(days1);
        
        Course course2 = new Course();
        List<String> days2 = new ArrayList<>();
        days2.add("Wednesday");
        course2.setDays(days2);
        
        Course course3 = new Course();
        List<String> days3 = new ArrayList<>();
        days3.add("Thursday");
        course3.setDays(days3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Test: Find courses on "Monday"
        int result = academicProgram.coursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for Monday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add("Friday");
            course.setDays(days);
            courses.add(course);
        }
        academicProgram.setCourses(courses);
        
        // Test: Find courses on "Friday"
        int result = academicProgram.coursesOnDay("Friday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        List<Course> courses = new ArrayList<>();
        
        // Three courses on Wednesday
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add("Wednesday");
            course.setDays(days);
            courses.add(course);
        }
        
        // Five courses on other days
        String[] otherDays = {"Monday", "Tuesday", "Thursday", "Friday", "Saturday"};
        for (int i = 0; i < 5; i++) {
            Course course = new Course();
            List<String> days = new ArrayList<>();
            days.add(otherDays[i]);
            course.setDays(days);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Test: Find courses on "Wednesday"
        int result = academicProgram.coursesOnDay("Wednesday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 courses for Wednesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Test: Find courses on any day (using "Monday" as example)
        int result = academicProgram.coursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}