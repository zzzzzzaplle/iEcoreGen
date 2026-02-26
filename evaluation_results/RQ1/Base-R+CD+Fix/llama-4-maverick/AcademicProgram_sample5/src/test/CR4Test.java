import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private Classroom classroom1;
    private Classroom classroom2;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private Course course6;
    private Course course7;
    private Course course8;
    
    @Before
    public void setUp() {
        // Create classrooms
        classroom1 = new Classroom("A101", 30, "1", "A");
        classroom2 = new Classroom("B202", 25, "2", "B");
        
        // Create courses with different course days
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 3600000); // 1 hour later
        
        course1 = new Course("Math", "MATH101", startTime, endTime, 30, 3, classroom1);
        course2 = new Course("Physics", "PHYS101", startTime, endTime, 25, 3, classroom2);
        course3 = new Course("Chemistry", "CHEM101", startTime, endTime, 20, 3, classroom1);
        course4 = new Course("Biology", "BIO101", startTime, endTime, 25, 3, classroom2);
        course5 = new Course("Computer Science", "CS101", startTime, endTime, 30, 3, classroom1);
        course6 = new Course("History", "HIST101", startTime, endTime, 20, 3, classroom2);
        course7 = new Course("Literature", "LIT101", startTime, endTime, 25, 3, classroom1);
        course8 = new Course("Art", "ART101", startTime, endTime, 15, 3, classroom2);
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Academic Program with one course that occurs on the given day
        course1.setCourseDays(Arrays.asList("Monday"));
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram = new AcademicProgram(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 1 as there is only one course on Monday
        assertEquals("Should return 1 for single course on given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        course1.setCourseDays(Arrays.asList("Monday"));
        course2.setCourseDays(Arrays.asList("Tuesday"));
        course3.setCourseDays(Arrays.asList("Wednesday"));
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram = new AcademicProgram(courses);
        
        // Execute: Count courses on "Friday" (no courses scheduled)
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: Should return 0 since no courses are taught on Friday
        assertEquals("Should return 0 when no courses on given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        course1.setCourseDays(Arrays.asList("Wednesday"));
        course2.setCourseDays(Arrays.asList("Wednesday"));
        course3.setCourseDays(Arrays.asList("Wednesday"));
        course4.setCourseDays(Arrays.asList("Wednesday"));
        course5.setCourseDays(Arrays.asList("Wednesday"));
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram = new AcademicProgram(courses);
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: Should return 5 as all five courses are taught on Wednesday
        assertEquals("Should return 5 when all courses on given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        course4.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        course5.setCourseDays(Arrays.asList("Monday"));
        course6.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        course7.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        course8.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        academicProgram = new AcademicProgram(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 3 as only three courses are taught on Monday
        assertEquals("Should return 3 when mixed courses on given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram = new AcademicProgram(new ArrayList<>());
        
        // Execute: Count courses on any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 for empty academic program", 0, result);
    }
}