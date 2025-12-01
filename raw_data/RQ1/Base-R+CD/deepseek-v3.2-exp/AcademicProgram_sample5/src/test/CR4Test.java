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
        // Input: Academic Program with one course that occurs on the given day, and the
        // given specific day
        // Expected Output: The total number of courses returned should be 1 as there is
        // only one course on that day

        // Create a course that occurs on Monday
        Course course = new Course();
        course.setCourseDays(Arrays.asList("Monday", "Wednesday"));

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);

        // Count courses on Monday
        int result = academicProgram.countCoursesOnSpecialDay("Monday");

        // Verify result
        assertEquals("Should return 1 when one course occurs on the given day", 1, result);
    }

    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Test Case 2: "No Courses on Given Day"
        // Input: Academic Program with multiple courses, none of which occur on the
        // given day, and the given specific day
        // Expected Output: The total number of courses returned should be 0 since no
        // courses are taught on that day

        // Create multiple courses that don't occur on Friday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));

        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));

        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        // Remove Friday from course3 to ensure no courses on Friday
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday"));

        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);

        // Count courses on Friday
        int result = academicProgram.countCoursesOnSpecialDay("Friday");

        // Verify result
        assertEquals("Should return 0 when no courses occur on the given day", 0, result);
    }

    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Test Case 3: "Multiple Courses on Given Day"
        // Input: Academic Program with five courses, all of which occur on the given
        // day, and the given specific day
        // Expected Output: The total number of courses returned should be 5 as all five
        // courses are taught on that day

        // Create five courses that all occur on Wednesday
        Course course1 = new Course();
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));

        Course course2 = new Course();
        course2.setCourseDays(Arrays.asList("Wednesday", "Friday"));

        Course course3 = new Course();
        course3.setCourseDays(Arrays.asList("Tuesday", "Wednesday", "Thursday"));

        Course course4 = new Course();
        course4.setCourseDays(Arrays.asList("Wednesday"));

        Course course5 = new Course();
        course5.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));

        // Add all five courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);

        // Count courses on Wednesday
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");

        // Verify result
        assertEquals("Should return 5 when all five courses occur on the given day", 5, result);
    }

    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Test Case 4: "Mixed Courses on Given Day"
        // Input: Academic Program with eight courses, out of which three occur on the
        // given day, and the given specific day
        // Expected Output: The total number of courses returned should be 3 as only
        // three courses are taught on that day

        // Create eight courses, only three of which occur on Tuesday
        Course course1 = new Course(); // Tuesday course
        course1.setCourseDays(Arrays.asList("Monday", "Tuesday"));

        Course course2 = new Course(); // Tuesday course
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));

        Course course3 = new Course(); // Not Tuesday
        course3.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));

        Course course4 = new Course(); // Tuesday course
        course4.setCourseDays(Arrays.asList("Tuesday"));

        Course course5 = new Course(); // Not Tuesday
        course5.setCourseDays(Arrays.asList("Wednesday", "Friday"));

        Course course6 = new Course(); // Not Tuesday
        course6.setCourseDays(Arrays.asList("Monday", "Thursday"));

        Course course7 = new Course(); // Not Tuesday
        course7.setCourseDays(Arrays.asList("Wednesday"));

        Course course8 = new Course(); // Not Tuesday
        course8.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));

        // Add all eight courses to academic program
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

        // Count courses on Tuesday
        int result = academicProgram.countCoursesOnSpecialDay("Tuesday");

        // Verify result
        assertEquals("Should return 3 when only three out of eight courses occur on the given day", 3, result);
    }

    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Test Case 5: "Empty Academic Program"
        // Input: An empty Academic Program and a given specific day
        // Expected Output: The total number of courses returned should be 0 as there
        // are no courses in the academic program

        // Academic program is already empty from setUp()
        // Count courses on any day (using "Monday" as example)
        int result = academicProgram.countCoursesOnSpecialDay("Monday");

        // Verify result
        assertEquals("Should return 0 when academic program is empty", 0, result);
    }
}