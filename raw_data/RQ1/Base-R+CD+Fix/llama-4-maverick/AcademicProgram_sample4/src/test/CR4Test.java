import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() throws Exception {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 1
        
        // Create a course with Monday as one of its course days
        Course course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() throws Exception {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0
        
        // Create multiple courses that don't occur on Saturday
        Course course1 = new Course();
        course1.setCode("MATH101");
        course1.setName("Calculus I");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course2 = new Course();
        course2.setCode("PHYS101");
        course2.setName("Physics I");
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course3 = new Course();
        course3.setCode("CHEM101");
        course3.setName("Chemistry I");
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Saturday (none should be found)
        int result = academicProgram.countCoursesOnSpecialDay("Saturday");
        
        // Verify the result
        assertEquals("Should return 0 courses for Saturday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() throws Exception {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 5
        
        // Create five courses that all occur on Wednesday
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Computer Science I");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Linear Algebra");
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course3 = new Course();
        course3.setCode("PHYS202");
        course3.setName("Modern Physics");
        course3.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course4 = new Course();
        course4.setCode("BIO101");
        course4.setName("Biology I");
        course4.setCourseDays(Arrays.asList("Tuesday", "Wednesday", "Thursday"));
        
        Course course5 = new Course();
        course5.setCode("ENG101");
        course5.setName("English Composition");
        course5.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Wednesday (all five should be found)
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify the result
        assertEquals("Should return 5 courses for Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() throws Exception {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3
        
        // Create eight courses, only three of which occur on Friday
        Course course1 = new Course(); // Friday course
        course1.setCode("CS301");
        course1.setName("Algorithms");
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        Course course2 = new Course(); // Friday course
        course2.setCode("MATH301");
        course2.setName("Advanced Calculus");
        course2.setCourseDays(Arrays.asList("Tuesday", "Friday"));
        
        Course course3 = new Course(); // Not Friday
        course3.setCode("PHYS301");
        course3.setName("Thermodynamics");
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course4 = new Course(); // Friday course
        course4.setCode("CHEM301");
        course4.setName("Organic Chemistry");
        course4.setCourseDays(Arrays.asList("Thursday", "Friday"));
        
        Course course5 = new Course(); // Not Friday
        course5.setCode("BIO301");
        course5.setName("Genetics");
        course5.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course6 = new Course(); // Not Friday
        course6.setCode("HIST101");
        course6.setName("World History");
        course6.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course7 = new Course(); // Not Friday
        course7.setCode("PSY101");
        course7.setName("Introduction to Psychology");
        course7.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        Course course8 = new Course(); // Not Friday
        course8.setCode("ART101");
        course8.setName("Art Appreciation");
        course8.setCourseDays(Arrays.asList("Monday"));
        
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
        
        // Test counting courses on Friday (only three should be found)
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify the result
        assertEquals("Should return 3 courses for Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() throws Exception {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0
        
        // Academic program is already empty from setup
        // Test counting courses on any day (should return 0)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify the result
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}