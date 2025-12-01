import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
        // Create a classroom
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        
        // Create course days list with only "Monday"
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        
        // Create a course that occurs on Monday
        Course course = new Course("Mathematics", "MATH101", new Date(), new Date(), 
                                  courseDays, 30, 3, classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 1 when only one course occurs on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B201", 25, "2", "B");
        
        // Create course days for courses that don't include "Wednesday"
        List<String> courseDays1 = new ArrayList<>(Arrays.asList("Monday", "Friday"));
        List<String> courseDays2 = new ArrayList<>(Arrays.asList("Tuesday", "Thursday"));
        
        // Create courses that don't occur on Wednesday
        Course course1 = new Course("Physics", "PHYS101", new Date(), new Date(), 
                                   courseDays1, 30, 3, classroom1);
        Course course2 = new Course("Chemistry", "CHEM101", new Date(), new Date(), 
                                   courseDays2, 25, 2, classroom2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Wednesday
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B201", 25, "2", "B");
        Classroom classroom3 = new Classroom("C301", 40, "3", "C");
        Classroom classroom4 = new Classroom("D401", 35, "4", "D");
        Classroom classroom5 = new Classroom("E501", 20, "5", "E");
        
        // Create course days lists - all courses occur on Friday
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Friday");
        
        // Create five courses that all occur on Friday
        Course course1 = new Course("Mathematics", "MATH101", new Date(), new Date(), 
                                   courseDays, 30, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", new Date(), new Date(), 
                                   courseDays, 25, 2, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", new Date(), new Date(), 
                                   courseDays, 40, 4, classroom3);
        Course course4 = new Course("Biology", "BIO101", new Date(), new Date(), 
                                   courseDays, 35, 3, classroom4);
        Course course5 = new Course("Computer Science", "CS101", new Date(), new Date(), 
                                   courseDays, 20, 5, classroom5);
        
        // Add all courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test counting courses on Friday
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B201", 25, "2", "B");
        Classroom classroom3 = new Classroom("C301", 40, "3", "C");
        Classroom classroom4 = new Classroom("D401", 35, "4", "D");
        Classroom classroom5 = new Classroom("E501", 20, "5", "E");
        Classroom classroom6 = new Classroom("F601", 45, "6", "F");
        Classroom classroom7 = new Classroom("G701", 50, "7", "G");
        Classroom classroom8 = new Classroom("H801", 30, "8", "H");
        
        // Create course days for different courses
        List<String> tuesdayCourses = new ArrayList<>();
        tuesdayCourses.add("Tuesday");
        
        List<String> mixedDays1 = new ArrayList<>(Arrays.asList("Monday", "Wednesday", "Friday"));
        List<String> mixedDays2 = new ArrayList<>(Arrays.asList("Monday", "Thursday"));
        List<String> mixedDays3 = new ArrayList<>(Arrays.asList("Wednesday", "Friday"));
        List<String> mixedDays4 = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Thursday"));
        List<String> mixedDays5 = new ArrayList<>(Arrays.asList("Tuesday", "Thursday"));
        
        // Create eight courses - three of which occur on Tuesday
        Course course1 = new Course("Mathematics", "MATH101", new Date(), new Date(), 
                                   tuesdayCourses, 30, 3, classroom1); // Tuesday
        Course course2 = new Course("Physics", "PHYS101", new Date(), new Date(), 
                                   mixedDays1, 25, 2, classroom2); // No Tuesday
        Course course3 = new Course("Chemistry", "CHEM101", new Date(), new Date(), 
                                   mixedDays2, 40, 4, classroom3); // No Tuesday
        Course course4 = new Course("Biology", "BIO101", new Date(), new Date(), 
                                   tuesdayCourses, 35, 3, classroom4); // Tuesday
        Course course5 = new Course("Computer Science", "CS101", new Date(), new Date(), 
                                   mixedDays3, 20, 5, classroom5); // No Tuesday
        Course course6 = new Course("History", "HIST101", new Date(), new Date(), 
                                   mixedDays4, 45, 3, classroom6); // Tuesday
        Course course7 = new Course("Geography", "GEOG101", new Date(), new Date(), 
                                   mixedDays5, 50, 2, classroom7); // No Tuesday
        Course course8 = new Course("Economics", "ECON101", new Date(), new Date(), 
                                   mixedDays5, 30, 4, classroom8); // No Tuesday
        
        // Add all courses to academic program
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
        
        // Test counting courses on Tuesday
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");
        
        // Verify result
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Test counting courses on any day (using "Monday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}