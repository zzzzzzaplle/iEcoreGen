import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    /**
     * Test Case 1: "Single Classroom in Academic Program"
     * Input: "An academic program with a single course using a classroom with a capacity of 30"
     * Expected Output: "The total classroom capacity in the academic program is 30"
     */
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() throws Exception {
        // Setup: Create a classroom with capacity 30
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Setup: Create a course using the classroom
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-09-01 10:30:00");
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        
        Course course = new Course("Mathematics", "MATH101", startTime, endTime, 
                                  courseDays, 30, 3, classroom);
        
        // Setup: Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 30
        assertEquals(30, result);
    }
    
    /**
     * Test Case 2: "Multiple Classrooms with Different Capacities"
     * Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
     * the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
     * Expected Output: "The total classroom capacity in the academic program is 100"
     */
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() throws Exception {
        // Setup: Create three classrooms with different capacities
        Classroom classroom1 = new Classroom("C101", 25, "1", "A");
        Classroom classroom2 = new Classroom("C102", 40, "1", "A");
        Classroom classroom3 = new Classroom("C201", 35, "2", "A");
        
        // Setup: Create three courses using the classrooms
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-09-01 10:30:00");
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        
        Course course1 = new Course("Mathematics", "MATH101", startTime, endTime, 
                                   courseDays, 25, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", startTime, endTime, 
                                   courseDays, 40, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", startTime, endTime, 
                                   courseDays, 35, 3, classroom3);
        
        // Setup: Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 100 (25 + 40 + 35)
        assertEquals(100, result);
    }
    
    /**
     * Test Case 3: "Two Identical Classrooms"
     * Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
     * Expected Output: "The total classroom capacity in the academic program is 100"
     */
    @Test
    public void testCase3_TwoIdenticalClassrooms() throws Exception {
        // Setup: Create two identical classrooms with capacity 50
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        
        // Setup: Create two courses using identical classrooms
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-09-01 10:30:00");
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        
        Course course1 = new Course("Mathematics", "MATH101", startTime, endTime, 
                                   courseDays, 50, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", startTime, endTime, 
                                   courseDays, 50, 3, classroom2);
        
        // Setup: Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 100 (50 + 50)
        assertEquals(100, result);
    }
    
    /**
     * Test Case 4: "Empty Academic Program"
     * Input: "An academic program with no courses (and thus no classrooms used)"
     * Expected Output: "The total classroom capacity in the academic program is 0"
     */
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Setup: Academic program is already empty from @Before setup
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 0
        assertEquals(0, result);
    }
    
    /**
     * Test Case 5: "Large Number of Classrooms"
     * Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
     * Expected Output: "The total classroom capacity in the academic program is 130"
     */
    @Test
    public void testCase5_LargeNumberOfClassrooms() throws Exception {
        // Setup: Create five classrooms with different capacities
        Classroom classroom1 = new Classroom("C101", 15, "1", "A");
        Classroom classroom2 = new Classroom("C102", 20, "1", "A");
        Classroom classroom3 = new Classroom("C103", 25, "1", "A");
        Classroom classroom4 = new Classroom("C201", 30, "2", "A");
        Classroom classroom5 = new Classroom("C202", 40, "2", "A");
        
        // Setup: Create five courses using the classrooms
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-09-01 10:30:00");
        List<String> courseDays = new ArrayList<>();
        courseDays.add("Monday");
        
        Course course1 = new Course("Mathematics", "MATH101", startTime, endTime, 
                                   courseDays, 15, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", startTime, endTime, 
                                   courseDays, 20, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", startTime, endTime, 
                                   courseDays, 25, 3, classroom3);
        Course course4 = new Course("Biology", "BIO101", startTime, endTime, 
                                   courseDays, 30, 3, classroom4);
        Course course5 = new Course("Computer Science", "CS101", startTime, endTime, 
                                   courseDays, 40, 3, classroom5);
        
        // Setup: Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 130 (15 + 20 + 25 + 30 + 40)
        assertEquals(130, result);
    }
}