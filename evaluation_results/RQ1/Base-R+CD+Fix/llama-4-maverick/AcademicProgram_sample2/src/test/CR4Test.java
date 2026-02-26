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
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day
        
        // Create classroom
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create course with specified day
        List<String> courseDays = Arrays.asList("Monday");
        Course course = new Course("Mathematics", "MATH101", null, null, courseDays, 30, 3, classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day
        
        // Create classroom
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create courses that don't occur on "Wednesday"
        List<String> course1Days = Arrays.asList("Monday", "Tuesday");
        List<String> course2Days = Arrays.asList("Thursday", "Friday");
        List<String> course3Days = Arrays.asList("Monday", "Friday");
        
        Course course1 = new Course("Mathematics", "MATH101", null, null, course1Days, 30, 3, classroom);
        Course course2 = new Course("Physics", "PHYS101", null, null, course2Days, 25, 4, classroom);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, course3Days, 20, 3, classroom);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result
        assertEquals("Should return 0 courses for Wednesday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day
        
        // Create classroom
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create five courses that all occur on "Friday"
        List<String> courseDays = Arrays.asList("Friday");
        
        Course course1 = new Course("Mathematics", "MATH101", null, null, courseDays, 30, 3, classroom);
        Course course2 = new Course("Physics", "PHYS101", null, null, courseDays, 25, 4, classroom);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, courseDays, 20, 3, classroom);
        Course course4 = new Course("Biology", "BIO101", null, null, courseDays, 15, 2, classroom);
        Course course5 = new Course("Computer Science", "CS101", null, null, courseDays, 35, 4, classroom);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test counting courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day
        
        // Create classroom
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create eight courses - three with "Tuesday", others with different days
        Course course1 = new Course("Mathematics", "MATH101", null, null, Arrays.asList("Monday", "Tuesday"), 30, 3, classroom);
        Course course2 = new Course("Physics", "PHYS101", null, null, Arrays.asList("Tuesday", "Thursday"), 25, 4, classroom);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, Arrays.asList("Tuesday"), 20, 3, classroom);
        Course course4 = new Course("Biology", "BIO101", null, null, Arrays.asList("Monday", "Wednesday"), 15, 2, classroom);
        Course course5 = new Course("Computer Science", "CS101", null, null, Arrays.asList("Wednesday", "Friday"), 35, 4, classroom);
        Course course6 = new Course("History", "HIST101", null, null, Arrays.asList("Monday"), 18, 2, classroom);
        Course course7 = new Course("Geography", "GEOG101", null, null, Arrays.asList("Thursday"), 22, 3, classroom);
        Course course8 = new Course("English", "ENG101", null, null, Arrays.asList("Friday"), 28, 3, classroom);
        
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
        
        // Test counting courses on "Tuesday"
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify result
        assertEquals("Should return 3 courses for Tuesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program
        
        // academicProgram is already empty from setUp()
        
        // Test counting courses on any day ("Monday" in this case)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}