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
        // Reset academic program before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day
        
        // Create a classroom
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        
        // Create course days list with only "Monday"
        List<String> courseDays = new ArrayList<>(Arrays.asList("Monday"));
        
        // Create a course that occurs on Monday
        Course course = new Course("Mathematics", "MATH101", null, null, courseDays, 30, 3, classroom);
        
        // Add course to academic program
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course)));
        
        // Test with specific day "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 1
        assertEquals("Should return 1 course for Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day
        
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        
        // Create course days lists without "Wednesday"
        List<String> courseDays1 = new ArrayList<>(Arrays.asList("Monday", "Friday"));
        List<String> courseDays2 = new ArrayList<>(Arrays.asList("Tuesday", "Thursday"));
        
        // Create courses that don't occur on Wednesday
        Course course1 = new Course("Mathematics", "MATH101", null, null, courseDays1, 30, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS201", null, null, courseDays2, 25, 2, classroom2);
        
        // Add courses to academic program
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course1, course2)));
        
        // Test with specific day "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for Wednesday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day
        
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        Classroom classroom3 = new Classroom("C303", 40, "3", "C");
        Classroom classroom4 = new Classroom("D404", 35, "4", "D");
        Classroom classroom5 = new Classroom("E505", 20, "5", "E");
        
        // Create course days lists all including "Friday"
        List<String> courseDays = new ArrayList<>(Arrays.asList("Friday"));
        
        // Create five courses that all occur on Friday
        Course course1 = new Course("Mathematics", "MATH101", null, null, courseDays, 30, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS201", null, null, courseDays, 25, 2, classroom2);
        Course course3 = new Course("Chemistry", "CHEM301", null, null, courseDays, 40, 4, classroom3);
        Course course4 = new Course("Biology", "BIO401", null, null, courseDays, 35, 3, classroom4);
        Course course5 = new Course("Computer Science", "CS501", null, null, courseDays, 20, 2, classroom5);
        
        // Add all five courses to academic program
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course1, course2, course3, course4, course5)));
        
        // Test with specific day "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Expected Output: The total number of courses returned should be 5
        assertEquals("Should return 5 courses for Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day
        
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        Classroom classroom3 = new Classroom("C303", 40, "3", "C");
        Classroom classroom4 = new Classroom("D404", 35, "4", "D");
        Classroom classroom5 = new Classroom("E505", 20, "5", "E");
        Classroom classroom6 = new Classroom("F606", 45, "6", "F");
        Classroom classroom7 = new Classroom("G707", 15, "7", "G");
        Classroom classroom8 = new Classroom("H808", 50, "8", "H");
        
        // Create course days lists - only 3 courses will have "Tuesday"
        List<String> tuesdayDays = new ArrayList<>(Arrays.asList("Tuesday"));
        List<String> otherDays1 = new ArrayList<>(Arrays.asList("Monday", "Wednesday"));
        List<String> otherDays2 = new ArrayList<>(Arrays.asList("Thursday", "Friday"));
        List<String> otherDays3 = new ArrayList<>(Arrays.asList("Monday", "Friday"));
        
        // Create eight courses - only 3 will occur on Tuesday
        Course course1 = new Course("Mathematics", "MATH101", null, null, tuesdayDays, 30, 3, classroom1); // Tuesday
        Course course2 = new Course("Physics", "PHYS201", null, null, otherDays1, 25, 2, classroom2); // Not Tuesday
        Course course3 = new Course("Chemistry", "CHEM301", null, null, tuesdayDays, 40, 4, classroom3); // Tuesday
        Course course4 = new Course("Biology", "BIO401", null, null, otherDays2, 35, 3, classroom4); // Not Tuesday
        Course course5 = new Course("Computer Science", "CS501", null, null, otherDays3, 20, 2, classroom5); // Not Tuesday
        Course course6 = new Course("History", "HIST601", null, null, tuesdayDays, 45, 3, classroom6); // Tuesday
        Course course7 = new Course("Geography", "GEOG701", null, null, otherDays1, 15, 2, classroom7); // Not Tuesday
        Course course8 = new Course("Economics", "ECON801", null, null, otherDays2, 50, 4, classroom8); // Not Tuesday
        
        // Add all eight courses to academic program
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course1, course2, course3, course4, course5, course6, course7, course8)));
        
        // Test with specific day "Tuesday"
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Expected Output: The total number of courses returned should be 3
        assertEquals("Should return 3 courses for Tuesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        
        // Academic program is already empty due to setUp() method
        // No courses added to academic program
        
        // Test with any specific day (e.g., "Monday")
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}