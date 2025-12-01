import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    private AcademicProgram academicProgram;
    private Course course1, course2, course3, course4, course5, course6, course7, course8;
    
    @Before
    public void setUp() {
        // Initialize academic program before each test
        academicProgram = new AcademicProgram();
        
        // Create test courses with different day schedules
        course1 = new Course();
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        course2 = new Course();
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        course3 = new Course();
        course3.setDays(Arrays.asList("Monday", "Friday"));
        
        course4 = new Course();
        course4.setDays(Arrays.asList("Wednesday", "Friday"));
        
        course5 = new Course();
        course5.setDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        course6 = new Course();
        course6.setDays(Arrays.asList("Tuesday"));
        
        course7 = new Course();
        course7.setDays(Arrays.asList("Thursday"));
        
        course8 = new Course();
        course8.setDays(Arrays.asList("Monday"));
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: Academic Program with one course that occurs on the given day
        academicProgram.addCourse(course1); // Course with Monday and Wednesday
        academicProgram.addCourse(course2); // Course with Tuesday and Thursday
        
        String specificDay = "Monday";
        int result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 1 course on Monday (course1)
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: Academic Program with multiple courses, none on the given day
        academicProgram.addCourse(course2); // Tuesday, Thursday
        academicProgram.addCourse(course6); // Tuesday
        academicProgram.addCourse(course7); // Thursday
        
        String specificDay = "Monday";
        int result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 0 courses on Monday
        assertEquals("Should return 0 when no courses are taught on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: Academic Program with five courses, all on the given day
        academicProgram.addCourse(course1); // Monday, Wednesday
        academicProgram.addCourse(course3); // Monday, Friday
        academicProgram.addCourse(course5); // Monday, Wednesday, Friday
        academicProgram.addCourse(course8); // Monday
        // Create additional courses for Monday
        Course course9 = new Course();
        course9.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(course9);
        
        String specificDay = "Monday";
        int result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 5 courses on Monday
        assertEquals("Should return 5 when all five courses are taught on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: Academic Program with eight courses, three on the given day
        academicProgram.addCourse(course1); // Monday, Wednesday
        academicProgram.addCourse(course2); // Tuesday, Thursday
        academicProgram.addCourse(course3); // Monday, Friday
        academicProgram.addCourse(course4); // Wednesday, Friday
        academicProgram.addCourse(course5); // Monday, Wednesday, Friday
        academicProgram.addCourse(course6); // Tuesday
        academicProgram.addCourse(course7); // Thursday
        academicProgram.addCourse(course8); // Monday
        
        String specificDay = "Monday";
        int result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 3 courses on Monday (course1, course3, course5, course8) - actually 4
        // Correction: The specification says 3 out of 8, so we need to adjust our test data
        academicProgram = new AcademicProgram();
        
        // Add courses: only 3 should have Monday
        Course mondayCourse1 = new Course();
        mondayCourse1.setDays(Arrays.asList("Monday"));
        academicProgram.addCourse(mondayCourse1);
        
        Course mondayCourse2 = new Course();
        mondayCourse2.setDays(Arrays.asList("Monday", "Wednesday"));
        academicProgram.addCourse(mondayCourse2);
        
        Course mondayCourse3 = new Course();
        mondayCourse3.setDays(Arrays.asList("Monday", "Friday"));
        academicProgram.addCourse(mondayCourse3);
        
        // Add 5 courses without Monday
        Course tuesdayCourse = new Course();
        tuesdayCourse.setDays(Arrays.asList("Tuesday"));
        academicProgram.addCourse(tuesdayCourse);
        
        Course wednesdayCourse = new Course();
        wednesdayCourse.setDays(Arrays.asList("Wednesday"));
        academicProgram.addCourse(wednesdayCourse);
        
        Course thursdayCourse = new Course();
        thursdayCourse.setDays(Arrays.asList("Thursday"));
        academicProgram.addCourse(thursdayCourse);
        
        Course fridayCourse = new Course();
        fridayCourse.setDays(Arrays.asList("Friday"));
        academicProgram.addCourse(fridayCourse);
        
        Course weekendCourse = new Course();
        weekendCourse.setDays(Arrays.asList("Saturday"));
        academicProgram.addCourse(weekendCourse);
        
        result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 3 courses on Monday
        assertEquals("Should return 3 when three out of eight courses are taught on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: Empty Academic Program
        String specificDay = "Monday";
        int result = academicProgram.getNumberOfCoursesOnDay(specificDay);
        
        // Expected: 0 courses (empty program)
        assertEquals("Should return 0 when the academic program is empty", 0, result);
    }
}