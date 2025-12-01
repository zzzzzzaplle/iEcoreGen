import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize academic program before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: Single Classroom in Academic Program
        // Input: An academic program with a single course using a classroom with a capacity of 30
        // Expected Output: The total classroom capacity in the academic program is 30
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create course using the classroom
        Course course = new Course("Mathematics", "MATH101", null, null, 
                                 new ArrayList<>(), 30, 3, classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 30", 30, totalCapacity);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: Multiple Classrooms with Different Capacities
        // Input: An academic program with 3 courses with capacities 25, 40, and 35
        // Expected Output: The total classroom capacity in the academic program is 100
        
        // Create classrooms with different capacities
        Classroom classroom1 = new Classroom("C101", 25, "1", "A");
        Classroom classroom2 = new Classroom("C102", 40, "1", "A");
        Classroom classroom3 = new Classroom("C103", 35, "2", "A");
        
        // Create courses using the classrooms
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 25, 3, classroom1);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<>(), 40, 4, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<>(), 35, 3, classroom3);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify the result (25 + 40 + 35 = 100)
        assertEquals("Total classroom capacity should be 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: Two Identical Classrooms
        // Input: An academic program with 2 courses both using a classroom with a capacity of 50
        // Expected Output: The total classroom capacity in the academic program is 100
        
        // Create classroom with capacity 50
        Classroom classroom = new Classroom("C101", 50, "1", "A");
        
        // Create two courses using the same classroom capacity
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 50, 3, classroom);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<>(), 50, 4, classroom);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify the result (50 + 50 = 100)
        assertEquals("Total classroom capacity should be 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: Empty Academic Program
        // Input: An academic program with no courses (and thus no classrooms used)
        // Expected Output: The total classroom capacity in the academic program is 0
        
        // Academic program is already empty from setUp()
        // Calculate total classroom capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 0 for empty program", 0, totalCapacity);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: Large Number of Classrooms
        // Input: An academic program with 5 courses with capacities 15, 20, 25, 30, 40
        // Expected Output: The total classroom capacity in the academic program is 130
        
        // Create classrooms with specified capacities
        Classroom classroom1 = new Classroom("C101", 15, "1", "A");
        Classroom classroom2 = new Classroom("C102", 20, "1", "A");
        Classroom classroom3 = new Classroom("C103", 25, "2", "A");
        Classroom classroom4 = new Classroom("C104", 30, "2", "A");
        Classroom classroom5 = new Classroom("C105", 40, "3", "A");
        
        // Create courses using the classrooms
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 15, 3, classroom1);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<>(), 20, 4, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<>(), 25, 3, classroom3);
        Course course4 = new Course("Biology", "BIO101", null, null, 
                                  new ArrayList<>(), 30, 3, classroom4);
        Course course5 = new Course("Computer Science", "CS101", null, null, 
                                  new ArrayList<>(), 40, 4, classroom5);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity (15 + 20 + 25 + 30 + 40 = 130)
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 130", 130, totalCapacity);
    }
}