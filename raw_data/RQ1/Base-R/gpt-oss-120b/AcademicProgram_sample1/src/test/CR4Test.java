import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CR4Test {
    
    private AcademicProgram program;
    private Course course1, course2, course3, course4, course5, course6, course7, course8;
    
    @Before
    public void setUp() {
        program = new AcademicProgram();
        
        // Create sample courses with different day schedules
        course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("Introduction to Programming");
        Set<DayOfWeek> mondayWednesday = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        course1.setDays(mondayWednesday);
        
        course2 = new Course();
        course2.setCourseCode("CS102");
        course2.setCourseName("Data Structures");
        Set<DayOfWeek> tuesdayThursday = new HashSet<>(Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        course2.setDays(tuesdayThursday);
        
        course3 = new Course();
        course3.setCourseCode("CS201");
        course3.setCourseName("Algorithms");
        Set<DayOfWeek> mondayFriday = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.FRIDAY));
        course3.setDays(mondayFriday);
        
        course4 = new Course();
        course4.setCourseCode("CS301");
        course4.setCourseName("Software Engineering");
        Set<DayOfWeek> wednesdayFriday = new HashSet<>(Arrays.asList(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
        course4.setDays(wednesdayFriday);
        
        course5 = new Course();
        course5.setCourseCode("CS401");
        course5.setCourseName("Database Systems");
        Set<DayOfWeek> thursdayOnly = new HashSet<>(Arrays.asList(DayOfWeek.THURSDAY));
        course5.setDays(thursdayOnly);
        
        course6 = new Course();
        course6.setCourseCode("MATH101");
        course6.setCourseName("Calculus I");
        Set<DayOfWeek> mondayWednesdayFriday = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
        course6.setDays(mondayWednesdayFriday);
        
        course7 = new Course();
        course7.setCourseCode("PHYS101");
        course7.setCourseName("Physics I");
        Set<DayOfWeek> tuesdayOnly = new HashSet<>(Arrays.asList(DayOfWeek.TUESDAY));
        course7.setDays(tuesdayOnly);
        
        course8 = new Course();
        course8.setCourseCode("CHEM101");
        course8.setCourseName("Chemistry I");
        Set<DayOfWeek> mondayTuesday = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
        course8.setDays(mondayTuesday);
    }
    
    @Test
    public void testCase1_SingleCourseOnGivenDay() {
        // Setup: Academic Program with one course that occurs on the given day
        program.addCourse(course1); // Course on Monday and Wednesday
        
        // Test: Find courses on Monday
        int result = program.getCourseCountByDay(DayOfWeek.MONDAY);
        
        // Verify: Should return 1 as there is only one course on that day
        assertEquals("Should return 1 course on Monday", 1, result);
    }
    
    @Test
    public void testCase2_NoCoursesOnGivenDay() {
        // Setup: Academic Program with multiple courses, none on Saturday
        program.addCourse(course1); // Monday, Wednesday
        program.addCourse(course2); // Tuesday, Thursday
        program.addCourse(course3); // Monday, Friday
        
        // Test: Find courses on Saturday (no courses scheduled)
        int result = program.getCourseCountByDay(DayOfWeek.SATURDAY);
        
        // Verify: Should return 0 since no courses are taught on Saturday
        assertEquals("Should return 0 courses on Saturday", 0, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesOnGivenDay() {
        // Setup: Academic Program with five courses, all on Monday
        Course courseA = new Course();
        courseA.setCourseCode("A");
        Set<DayOfWeek> mondaySet = new HashSet<>(Arrays.asList(DayOfWeek.MONDAY));
        courseA.setDays(mondaySet);
        
        Course courseB = new Course();
        courseB.setCourseCode("B");
        courseB.setDays(mondaySet);
        
        Course courseC = new Course();
        courseC.setCourseCode("C");
        courseC.setDays(mondaySet);
        
        Course courseD = new Course();
        courseD.setCourseCode("D");
        courseD.setDays(mondaySet);
        
        Course courseE = new Course();
        courseE.setCourseCode("E");
        courseE.setDays(mondaySet);
        
        program.addCourse(courseA);
        program.addCourse(courseB);
        program.addCourse(courseC);
        program.addCourse(courseD);
        program.addCourse(courseE);
        
        // Test: Find courses on Monday
        int result = program.getCourseCountByDay(DayOfWeek.MONDAY);
        
        // Verify: Should return 5 as all five courses are taught on Monday
        assertEquals("Should return 5 courses on Monday", 5, result);
    }
    
    @Test
    public void testCase4_MixedCoursesOnGivenDay() {
        // Setup: Academic Program with eight courses, three on Monday
        program.addCourse(course1); // Monday, Wednesday
        program.addCourse(course2); // Tuesday, Thursday
        program.addCourse(course3); // Monday, Friday
        program.addCourse(course4); // Wednesday, Friday
        program.addCourse(course5); // Thursday only
        program.addCourse(course6); // Monday, Wednesday, Friday
        program.addCourse(course7); // Tuesday only
        program.addCourse(course8); // Monday, Tuesday
        
        // Test: Find courses on Monday
        int result = program.getCourseCountByDay(DayOfWeek.MONDAY);
        
        // Verify: Should return 3 as only three courses are taught on Monday
        assertEquals("Should return 3 courses on Monday", 3, result);
    }
    
    @Test
    public void testCase5_EmptyAcademicProgram() {
        // Setup: Empty Academic Program (no courses added)
        
        // Test: Find courses on any day (using Tuesday as example)
        int result = program.getCourseCountByDay(DayOfWeek.TUESDAY);
        
        // Verify: Should return 0 as there are no courses in the academic program
        assertEquals("Should return 0 courses in empty program", 0, result);
    }
}