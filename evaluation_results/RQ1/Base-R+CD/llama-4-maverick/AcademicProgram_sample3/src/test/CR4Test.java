import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR4Test {
    
    private AcademicProgram academicProgram;
    private Course course1, course2, course3, course4, course5, course6, course7, course8;
    private Classroom classroom;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        classroom = new Classroom();
        classroom.setId("A101");
        classroom.setCapacity(30);
        
        course1 = new Course();
        course1.setCode("CS101");
        course1.setClassroom(classroom);
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setClassroom(classroom);
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        course3 = new Course();
        course3.setCode("PHYS301");
        course3.setClassroom(classroom);
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        course4 = new Course();
        course4.setCode("CHEM401");
        course4.setClassroom(classroom);
        course4.setCourseDays(Arrays.asList("Wednesday", "Friday"));
        
        course5 = new Course();
        course5.setCode("BIO501");
        course5.setClassroom(classroom);
        course5.setCourseDays(Arrays.asList("Monday", "Wednesday", "Friday"));
        
        course6 = new Course();
        course6.setCode("ENG601");
        course6.setClassroom(classroom);
        course6.setCourseDays(Arrays.asList("Tuesday"));
        
        course7 = new Course();
        course7.setCode("HIST701");
        course7.setClassroom(classroom);
        course7.setCourseDays(Arrays.asList("Thursday"));
        
        course8 = new Course();
        course8.setCode("ART801");
        course8.setClassroom(classroom);
        course8.setCourseDays(Arrays.asList("Wednesday"));
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Academic Program with one course that occurs on the given day
        List<Course> courses = new ArrayList<>();
        courses.add(course1); // Course that occurs on Monday
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Single course on given day should return 1", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on the given day
        List<Course> courses = new ArrayList<>();
        courses.add(course1); // Monday, Wednesday
        courses.add(course2); // Tuesday, Thursday
        courses.add(course4); // Wednesday, Friday
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Saturday" (no courses scheduled)
        int result = academicProgram.countCoursesOnSpecialDay("Saturday");
        
        // Verify: Should return 0 since no courses are taught on that day
        assertEquals("No courses on given day should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses all occurring on the given day
        List<Course> courses = new ArrayList<>();
        
        // Create 5 courses that all occur on Monday
        Course mondayCourse1 = new Course();
        mondayCourse1.setCode("CS101");
        mondayCourse1.setClassroom(classroom);
        mondayCourse1.setCourseDays(Arrays.asList("Monday"));
        
        Course mondayCourse2 = new Course();
        mondayCourse2.setCode("MATH201");
        mondayCourse2.setClassroom(classroom);
        mondayCourse2.setCourseDays(Arrays.asList("Monday"));
        
        Course mondayCourse3 = new Course();
        mondayCourse3.setCode("PHYS301");
        mondayCourse3.setClassroom(classroom);
        mondayCourse3.setCourseDays(Arrays.asList("Monday"));
        
        Course mondayCourse4 = new Course();
        mondayCourse4.setCode("CHEM401");
        mondayCourse4.setClassroom(classroom);
        mondayCourse4.setCourseDays(Arrays.asList("Monday"));
        
        Course mondayCourse5 = new Course();
        mondayCourse5.setCode("BIO501");
        mondayCourse5.setClassroom(classroom);
        mondayCourse5.setCourseDays(Arrays.asList("Monday"));
        
        courses.add(mondayCourse1);
        courses.add(mondayCourse2);
        courses.add(mondayCourse3);
        courses.add(mondayCourse4);
        courses.add(mondayCourse5);
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Monday"
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 5 as all five courses are taught on that day
        assertEquals("Five courses on given day should return 5", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three occur on the given day
        List<Course> courses = new ArrayList<>();
        courses.add(course1); // Monday, Wednesday
        courses.add(course2); // Tuesday, Thursday
        courses.add(course3); // Monday, Friday
        courses.add(course4); // Wednesday, Friday
        courses.add(course5); // Monday, Wednesday, Friday
        courses.add(course6); // Tuesday
        courses.add(course7); // Thursday
        courses.add(course8); // Wednesday
        academicProgram.setCourses(courses);
        
        // Execute: Count courses on "Wednesday"
        int result = academicProgram.countCoursesOnSpecialDay("Wednesday");
        
        // Verify: Should return 3 as only three courses are taught on that day
        assertEquals("Three courses on given day should return 3", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Count courses on any day
        int result = academicProgram.countCoursesOnSpecialDay("Monday");
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Empty academic program should return 0", 0, result);
    }
}