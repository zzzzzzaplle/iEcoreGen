import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private List<Course> courses;
    
    @Before
    public void setUp() {
        courses = new ArrayList<>();
        academicProgram = new AcademicProgram(courses);
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Academic Program with one course that occurs on the given day
        Classroom classroom = new Classroom("101", 30, "1", "A");
        Course course = new Course("Mathematics", "MATH101", new Date(), new Date(), 30, 3, classroom);
        course.getCourseDays().add("Monday");
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 1
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        Classroom classroom1 = new Classroom("101", 30, "1", "A");
        Classroom classroom2 = new Classroom("102", 25, "1", "A");
        
        Course course1 = new Course("Mathematics", "MATH101", new Date(), new Date(), 30, 3, classroom1);
        course1.getCourseDays().add("Tuesday");
        
        Course course2 = new Course("Physics", "PHYS101", new Date(), new Date(), 25, 3, classroom2);
        course2.getCourseDays().add("Wednesday");
        
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses on Monday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on the given day
        Classroom classroom1 = new Classroom("101", 30, "1", "A");
        Classroom classroom2 = new Classroom("102", 25, "1", "A");
        Classroom classroom3 = new Classroom("201", 40, "2", "A");
        Classroom classroom4 = new Classroom("202", 35, "2", "A");
        Classroom classroom5 = new Classroom("301", 50, "3", "A");
        
        Course course1 = new Course("Mathematics", "MATH101", new Date(), new Date(), 30, 3, classroom1);
        course1.getCourseDays().add("Friday");
        
        Course course2 = new Course("Physics", "PHYS101", new Date(), new Date(), 25, 3, classroom2);
        course2.getCourseDays().add("Friday");
        
        Course course3 = new Course("Chemistry", "CHEM101", new Date(), new Date(), 40, 3, classroom3);
        course3.getCourseDays().add("Friday");
        
        Course course4 = new Course("Biology", "BIO101", new Date(), new Date(), 35, 3, classroom4);
        course4.getCourseDays().add("Friday");
        
        Course course5 = new Course("Computer Science", "CS101", new Date(), new Date(), 50, 3, classroom5);
        course5.getCourseDays().add("Friday");
        
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Friday"
        int result = academicProgram.countCoursesOnSpecialDay("Friday");
        
        // Verify: The total number of courses returned should be 5
        assertEquals("Should return 5 courses on Friday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on the given day
        Classroom classroom1 = new Classroom("101", 30, "1", "A");
        Classroom classroom2 = new Classroom("102", 25, "1", "A");
        Classroom classroom3 = new Classroom("201", 40, "2", "A");
        Classroom classroom4 = new Classroom("202", 35, "2", "A");
        Classroom classroom5 = new Classroom("301", 50, "3", "A");
        Classroom classroom6 = new Classroom("302", 45, "3", "A");
        Classroom classroom7 = new Classroom("401", 60, "4", "A");
        Classroom classroom8 = new Classroom("402", 55, "4", "A");
        
        Course course1 = new Course("Mathematics", "MATH101", new Date(), new Date(), 30, 3, classroom1);
        course1.getCourseDays().add("Wednesday");
        
        Course course2 = new Course("Physics", "PHYS101", new Date(), new Date(), 25, 3, classroom2);
        course2.getCourseDays().add("Wednesday");
        
        Course course3 = new Course("Chemistry", "CHEM101", new Date(), new Date(), 40, 3, classroom3);
        course3.getCourseDays().add("Wednesday");
        
        Course course4 = new Course("Biology", "BIO101", new Date(), new Date(), 35, 3, classroom4);
        course4.getCourseDays().add("Monday");
        
        Course course5 = new Course("Computer Science", "CS101", new Date(), new Date(), 50, 3, classroom5);
        course5.getCourseDays().add("Tuesday");
        
        Course course6 = new Course("History", "HIST101", new Date(), new Date(), 45, 3, classroom6);
        course6.getCourseDays().add("Thursday");
        
        Course course7 = new Course("English", "ENG101", new Date(), new Date(), 60, 3, classroom7);
        course7.getCourseDays().add("Friday");
        
        Course course8 = new Course("Art", "ART101", new Date(), new Date(), 55, 3, classroom8);
        course8.getCourseDays().add("Monday");
        
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        courses.add(course6);
        courses.add(course7);
        courses.add(course8);
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: The total number of courses returned should be 3
        assertEquals("Should return 3 courses on Wednesday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Count courses on any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: The total number of courses returned should be 0
        assertEquals("Should return 0 courses for empty program", 0, result);
    }
}