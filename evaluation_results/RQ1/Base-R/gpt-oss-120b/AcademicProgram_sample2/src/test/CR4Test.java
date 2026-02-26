import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private Course course1, course2, course3, course4, course5, course6, course7, course8;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        academicProgram.setName("Computer Science");
        
        // Initialize courses with different day schedules
        course1 = new Course();
        course1.setName("Data Structures");
        course1.setCode("CS201");
        Set<String> days1 = new HashSet<>();
        days1.add("Monday");
        days1.add("Wednesday");
        course1.setDays(days1);
        
        course2 = new Course();
        course2.setName("Algorithms");
        course2.setCode("CS301");
        Set<String> days2 = new HashSet<>();
        days2.add("Tuesday");
        days2.add("Thursday");
        course2.setDays(days2);
        
        course3 = new Course();
        course3.setName("Database Systems");
        course3.setCode("CS401");
        Set<String> days3 = new HashSet<>();
        days3.add("Monday");
        days3.add("Friday");
        course3.setDays(days3);
        
        course4 = new Course();
        course4.setName("Operating Systems");
        course4.setCode("CS501");
        Set<String> days4 = new HashSet<>();
        days4.add("Wednesday");
        days4.add("Friday");
        course4.setDays(days4);
        
        course5 = new Course();
        course5.setName("Computer Networks");
        course5.setCode("CS601");
        Set<String> days5 = new HashSet<>();
        days5.add("Monday");
        course5.setDays(days5);
        
        course6 = new Course();
        course6.setName("Software Engineering");
        course6.setCode("CS701");
        Set<String> days6 = new HashSet<>();
        days6.add("Tuesday");
        course6.setDays(days6);
        
        course7 = new Course();
        course7.setName("Artificial Intelligence");
        course7.setCode("CS801");
        Set<String> days7 = new HashSet<>();
        days7.add("Thursday");
        course7.setDays(days7);
        
        course8 = new Course();
        course8.setName("Web Development");
        course8.setCode("CS901");
        Set<String> days8 = new HashSet<>();
        days8.add("Friday");
        course8.setDays(days8);
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Test Case 1: "Single Course on Given Day"
        // Input: Academic Program with one course that occurs on the given day, and the given specific day
        academicProgram.addCourse(course1); // Monday, Wednesday
        
        int result = academicProgram.getCoursesCountByDay("Monday");
        
        // Expected Output: The total number of courses returned should be 1 as there is only one course on that day
        assertEquals("Should return 1 when there is exactly one course on the given day", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the given day, and the given specific day
        academicProgram.addCourse(course2); // Tuesday, Thursday
        academicProgram.addCourse(course6); // Tuesday
        academicProgram.addCourse(course7); // Thursday
        
        int result = academicProgram.getCoursesCountByDay("Monday");
        
        // Expected Output: The total number of courses returned should be 0 since no courses are taught on that day
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given day, and the given specific day
        // Create five courses that all occur on Friday
        Course fridayCourse1 = new Course();
        fridayCourse1.setName("Course A");
        Set<String> fridayDays1 = new HashSet<>();
        fridayDays1.add("Friday");
        fridayCourse1.setDays(fridayDays1);
        
        Course fridayCourse2 = new Course();
        fridayCourse2.setName("Course B");
        Set<String> fridayDays2 = new HashSet<>();
        fridayDays2.add("Friday");
        fridayCourse2.setDays(fridayDays2);
        
        Course fridayCourse3 = new Course();
        fridayCourse3.setName("Course C");
        Set<String> fridayDays3 = new HashSet<>();
        fridayDays3.add("Friday");
        fridayCourse3.setDays(fridayDays3);
        
        Course fridayCourse4 = new Course();
        fridayCourse4.setName("Course D");
        Set<String> fridayDays4 = new HashSet<>();
        fridayDays4.add("Friday");
        fridayCourse4.setDays(fridayDays4);
        
        Course fridayCourse5 = new Course();
        fridayCourse5.setName("Course E");
        Set<String> fridayDays5 = new HashSet<>();
        fridayDays5.add("Friday");
        fridayCourse5.setDays(fridayDays5);
        
        academicProgram.addCourse(fridayCourse1);
        academicProgram.addCourse(fridayCourse2);
        academicProgram.addCourse(fridayCourse3);
        academicProgram.addCourse(fridayCourse4);
        academicProgram.addCourse(fridayCourse5);
        
        int result = academicProgram.getCoursesCountByDay("Friday");
        
        // Expected Output: The total number of courses returned should be 5 as all five courses are taught on that day
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the given day, and the given specific day
        academicProgram.addCourse(course1); // Monday, Wednesday
        academicProgram.addCourse(course2); // Tuesday, Thursday
        academicProgram.addCourse(course3); // Monday, Friday
        academicProgram.addCourse(course4); // Wednesday, Friday
        academicProgram.addCourse(course5); // Monday
        academicProgram.addCourse(course6); // Tuesday
        academicProgram.addCourse(course7); // Thursday
        academicProgram.addCourse(course8); // Friday
        
        int result = academicProgram.getCoursesCountByDay("Monday");
        
        // Expected Output: The total number of courses returned should be 3 as only three courses are taught on that day
        assertEquals("Should return 3 when exactly three out of eight courses occur on the given day", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // No courses added to academic program
        
        int result = academicProgram.getCoursesCountByDay("Wednesday");
        
        // Expected Output: The total number of courses returned should be 0 as there are no courses in the academic program
        assertEquals("Should return 0 when academic program has no courses", 0, result);
    }
}