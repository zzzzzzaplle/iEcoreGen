import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() throws Exception {
        // Create classroom
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        
        // Create course that occurs on Monday
        Course course = new Course(
            "Mathematics", 
            "MATH101", 
            dateFormat.parse("2023-09-01 09:00:00"), 
            dateFormat.parse("2023-09-01 10:30:00"), 
            30, 
            3, 
            classroom
        );
        course.setCourseDays(Arrays.asList("Monday"));
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Test countCoursesOnSpecialDay for Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() throws Exception {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        
        // Create courses that occur on Monday and Wednesday
        Course course1 = new Course(
            "Mathematics", 
            "MATH101", 
            dateFormat.parse("2023-09-01 09:00:00"), 
            dateFormat.parse("2023-09-01 10:30:00"), 
            30, 
            3, 
            classroom1
        );
        course1.setCourseDays(Arrays.asList("Monday"));
        
        Course course2 = new Course(
            "Physics", 
            "PHYS101", 
            dateFormat.parse("2023-09-01 11:00:00"), 
            dateFormat.parse("2023-09-01 12:30:00"), 
            25, 
            3, 
            classroom2
        );
        course2.setCourseDays(Arrays.asList("Wednesday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Test countCoursesOnSpecialDay for Friday (no courses)
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result
        assertEquals("Should return 0 courses on Friday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() throws Exception {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        Classroom classroom3 = new Classroom("C303", 40, "3", "C");
        Classroom classroom4 = new Classroom("D404", 35, "4", "D");
        Classroom classroom5 = new Classroom("E505", 20, "5", "E");
        
        // Create 5 courses that all occur on Wednesday
        Course course1 = new Course(
            "Mathematics", 
            "MATH101", 
            dateFormat.parse("2023-09-01 09:00:00"), 
            dateFormat.parse("2023-09-01 10:30:00"), 
            30, 
            3, 
            classroom1
        );
        course1.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course2 = new Course(
            "Physics", 
            "PHYS101", 
            dateFormat.parse("2023-09-01 11:00:00"), 
            dateFormat.parse("2023-09-01 12:30:00"), 
            25, 
            3, 
            classroom2
        );
        course2.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course3 = new Course(
            "Chemistry", 
            "CHEM101", 
            dateFormat.parse("2023-09-01 14:00:00"), 
            dateFormat.parse("2023-09-01 15:30:00"), 
            40, 
            3, 
            classroom3
        );
        course3.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course4 = new Course(
            "Biology", 
            "BIO101", 
            dateFormat.parse("2023-09-01 16:00:00"), 
            dateFormat.parse("2023-09-01 17:30:00"), 
            35, 
            3, 
            classroom4
        );
        course4.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course5 = new Course(
            "Computer Science", 
            "CS101", 
            dateFormat.parse("2023-09-01 18:00:00"), 
            dateFormat.parse("2023-09-01 19:30:00"), 
            20, 
            3, 
            classroom5
        );
        course5.setCourseDays(Arrays.asList("Wednesday"));
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Test countCoursesOnSpecialDay for Wednesday
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify result
        assertEquals("Should return 5 courses on Wednesday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() throws Exception {
        // Create classrooms
        Classroom classroom1 = new Classroom("A101", 30, "1", "A");
        Classroom classroom2 = new Classroom("B202", 25, "2", "B");
        Classroom classroom3 = new Classroom("C303", 40, "3", "C");
        Classroom classroom4 = new Classroom("D404", 35, "4", "D");
        Classroom classroom5 = new Classroom("E505", 20, "5", "E");
        Classroom classroom6 = new Classroom("F606", 45, "6", "F");
        Classroom classroom7 = new Classroom("G707", 30, "7", "G");
        Classroom classroom8 = new Classroom("H808", 25, "8", "H");
        
        // Create 8 courses with 3 occurring on Friday
        Course course1 = new Course(
            "Mathematics", 
            "MATH101", 
            dateFormat.parse("2023-09-01 09:00:00"), 
            dateFormat.parse("2023-09-01 10:30:00"), 
            30, 
            3, 
            classroom1
        );
        course1.setCourseDays(Arrays.asList("Monday"));
        
        Course course2 = new Course(
            "Physics", 
            "PHYS101", 
            dateFormat.parse("2023-09-01 11:00:00"), 
            dateFormat.parse("2023-09-01 12:30:00"), 
            25, 
            3, 
            classroom2
        );
        course2.setCourseDays(Arrays.asList("Tuesday"));
        
        Course course3 = new Course(
            "Chemistry", 
            "CHEM101", 
            dateFormat.parse("2023-09-01 14:00:00"), 
            dateFormat.parse("2023-09-01 15:30:00"), 
            40, 
            3, 
            classroom3
        );
        course3.setCourseDays(Arrays.asList("Wednesday"));
        
        Course course4 = new Course(
            "Biology", 
            "BIO101", 
            dateFormat.parse("2023-09-01 16:00:00"), 
            dateFormat.parse("2023-09-01 17:30:00"), 
            35, 
            3, 
            classroom4
        );
        course4.setCourseDays(Arrays.asList("Thursday"));
        
        Course course5 = new Course(
            "Computer Science", 
            "CS101", 
            dateFormat.parse("2023-09-01 09:00:00"), 
            dateFormat.parse("2023-09-01 10:30:00"), 
            20, 
            3, 
            classroom5
        );
        course5.setCourseDays(Arrays.asList("Friday"));
        
        Course course6 = new Course(
            "History", 
            "HIST101", 
            dateFormat.parse("2023-09-01 11:00:00"), 
            dateFormat.parse("2023-09-01 12:30:00"), 
            45, 
            3, 
            classroom6
        );
        course6.setCourseDays(Arrays.asList("Friday"));
        
        Course course7 = new Course(
            "English", 
            "ENG101", 
            dateFormat.parse("2023-09-01 14:00:00"), 
            dateFormat.parse("2023-09-01 15:30:00"), 
            30, 
            3, 
            classroom7
        );
        course7.setCourseDays(Arrays.asList("Friday"));
        
        Course course8 = new Course(
            "Art", 
            "ART101", 
            dateFormat.parse("2023-09-01 16:00:00"), 
            dateFormat.parse("极3-09-01 17:30:00"), 
            25, 
            3, 
            classroom8
        );
        course8.setCourseDays(Arrays.asList("Saturday"));
        
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
        
        // Test countCoursesOnSpecialDay for Friday
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify result
        assertEquals("Should return 3 courses on Friday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Test countCoursesOnSpecialDay for any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify result
        assertEquals("Should return 0 courses for empty academic program", 0, result);
    }
}