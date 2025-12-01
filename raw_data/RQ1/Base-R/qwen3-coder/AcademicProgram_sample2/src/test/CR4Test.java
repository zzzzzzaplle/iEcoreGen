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
        course.setDays("Monday");
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Call method with given specific day "Monday"
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Course course1 = new Course();
        course1.setDays("Tuesday");
        Course course2 = new Course();
        course2.setDays("Wednesday");
        Course course3 = new Course();
        course3.setDays("Thursday");
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute: Call method with given specific day "Friday"
        int result = academicProgram.getCoursesOnDay("Friday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for Friday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Course course1 = new Course();
        course1.setDays("Wednesday");
        Course course2 = new Course();
        course2.setDays("Wednesday");
        Course course3 = new Course();
        course3.setDays("Wednesday");
        Course course4 = new Course();
        course4.setDays("Wednesday");
        Course course5 = new Course();
        course5.setDays("Wednesday");
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Execute: Call method with given specific day "Wednesday"
        int result = academicProgram.getCoursesOnDay("Wednesday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        Course course1 = new Course(); // On Thursday
        course1.setDays("Thursday");
        Course course2 = new Course(); // On Thursday
        course2.setDays("Thursday");
        Course course3 = new Course(); // On Thursday
        course3.setDays("Thursday");
        Course course4 = new Course(); // On Monday
        course4.setDays("Monday");
        Course course5 = new Course(); // On Tuesday
        course5.setDays("Tuesday");
        Course course6 = new Course(); // On Wednesday
        course6.setDays("Wednesday");
        Course course7 = new Course(); // On Friday
        course7.setDays("Friday");
        Course course8 = new Course(); // On Monday
        course8.setDays("Monday");
        
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
        
        // Execute: Call method with given specific day "Thursday"
        int result = academicProgram.getCoursesOnDay("Thursday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 courses for Thursday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses)
        // No courses added to academic program
        
        // Execute: Call method with given specific day "Monday"
        int result = academicProgram.getCoursesOnDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}