import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Create a course that occurs on Monday
        Course course = new Course();
        course.setDays(Arrays.asList("Monday"));
        
        // Add the course to the academic program
        academicProgram.addCourse(course);
        
        // Test getting courses count on Monday
        int result = academicProgram.getCoursesCountOnDay("Monday");
        
        // Verify the result is 1
        assertEquals("Should return 1 when there is one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Create courses that occur on different days (not Tuesday)
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Thursday", "Friday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        // Add courses to the academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Test getting courses count on Tuesday
        int result = academicProgram.getCoursesCountOnDay("Tuesday");
        
        // Verify the result is 0
        assertEquals("Should return 0 when no courses are taught on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Create five courses that all occur on Wednesday
        Course course1 = new Course();
        course1.setDays(Arrays.asList("Wednesday"));
        
        Course course2 = new Course();
        course2.setDays(Arrays.asList("Monday", "Wednesday"));
        
        Course course3 = new Course();
        course3.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course course4 = new Course();
        course4.setDays(Arrays.asList("Tuesday", "Wednesday", "Thursday"));
        
        Course course5 = new Course();
        course5.setDays(Arrays.asList("Wednesday"));
        
        // Add all courses to the academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        
        // Test getting courses count on Wednesday
        int result = academicProgram.getCoursesCountOnDay("Wednesday");
        
        // Verify the result is 5
        assertEquals("Should return 5 when all five courses are taught on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Create eight courses - three occur on Friday and five occur on other days
        Course courseOnFriday1 = new Course();
        courseOnFriday1.setDays(Arrays.asList("Friday"));
        
        Course courseOnFriday2 = new Course();
        courseOnFriday2.setDays(Arrays.asList("Monday", "Friday"));
        
        Course courseOnFriday3 = new Course();
        courseOnFriday3.setDays(Arrays.asList("Wednesday", "Friday"));
        
        Course courseNotOnFriday1 = new Course();
        courseNotOnFriday1.setDays(Arrays.asList("Monday"));
        
        Course courseNotOnFriday2 = new Course();
        courseNotOnFriday2.setDays(Arrays.asList("Tuesday"));
        
        Course courseNotOnFriday3 = new Course();
        courseNotOnFriday3.setDays(Arrays.asList("Wednesday"));
        
        Course courseNotOnFriday4 = new Course();
        courseNotOnFriday4.setDays(Arrays.asList("Thursday"));
        
        Course courseNotOnFriday5 = new Course();
        courseNotOnFriday5.setDays(Arrays.asList("Monday", "Wednesday"));
        
        // Add all courses to the academic program
        academicProgram.addCourse(courseOnFriday1);
        academicProgram.addCourse(courseOnFriday2);
        academicProgram.addCourse(courseOnFriday3);
        academicProgram.addCourse(courseNotOnFriday1);
        academicProgram.addCourse(courseNotOnFriday2);
        academicProgram.addCourse(courseNotOnFriday3);
        academicProgram.addCourse(courseNotOnFriday4);
        academicProgram.addCourse(courseNotOnFriday5);
        
        // Test getting courses count on Friday
        int result = academicProgram.getCoursesCountOnDay("Friday");
        
        // Verify the result is 3
        assertEquals("Should return 3 when three out of eight courses are taught on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test getting courses count on any day with empty academic program
        int result = academicProgram.getCoursesCountOnDay("Monday");
        
        // Verify the result is 0
        assertEquals("Should return 0 when there are no courses in the academic program", 0, result);
    }
}