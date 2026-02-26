import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Reset academic program before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: Single Classroom in Academic Program
        // Input: An academic program with a single course using a classroom with a capacity of 30
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create course using the classroom
        Course course = new Course("Mathematics", "MATH101", null, null, 30, 3, classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify expected output: The total classroom capacity in the academic program is 30
        assertEquals("Total capacity should be 30 for single classroom", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: Multiple Classrooms with Different Capacities
        // Input: An academic program with 3 courses with capacities 25, 40, and 35
        
        // Create classrooms with different capacities
        Classroom classroom1 = new Classroom("C101", 25, "1", "A");
        Classroom classroom2 = new Classroom("C102", 40, "1", "A");
        Classroom classroom3 = new Classroom("C103", 35, "1", "A");
        
        // Create courses using the classrooms
        Course course1 = new Course("Mathematics", "MATH101", null, null, 25, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 40, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 35, 3, classroom3);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify expected output: The total classroom capacity in the academic program is 100
        assertEquals("Total capacity should be 100 for classrooms with capacities 25, 40, 35", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: Two Identical Classrooms
        // Input: An academic program with 2 courses both using a classroom with a capacity of 50
        
        // Create two identical classrooms with capacity 50
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        
        // Create courses using the classrooms
        Course course1 = new Course("Mathematics", "MATH101", null, null, 50, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 50, 3, classroom2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify expected output: The total classroom capacity in the academic program is 100
        assertEquals("Total capacity should be 100 for two identical classrooms with capacity 50", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: Empty Academic Program
        // Input: An academic program with no courses (and thus no classrooms used)
        
        // Academic program is already empty from setUp() method
        // No courses added to the program
        
        // Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify expected output: The total classroom capacity in the academic program is 0
        assertEquals("Total capacity should be 0 for empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: Large Number of Classrooms
        // Input: An academic program with 5 courses with capacities 15, 20, 25, 30, 40
        
        // Create classrooms with specified capacities
        Classroom classroom1 = new Classroom("C101", 15, "1", "A");
        Classroom classroom2 = new Classroom("C102", 20, "1", "A");
        Classroom classroom3 = new Classroom("C103", 25, "1", "A");
        Classroom classroom4 = new Classroom("C104", 30, "1", "A");
        Classroom classroom5 = new Classroom("C105", 40, "1", "A");
        
        // Create courses using the classrooms
        Course course1 = new Course("Math", "MATH101", null, null, 15, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 20, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 25, 3, classroom3);
        Course course4 = new Course("Biology", "BIO101", null, null, 30, 3, classroom4);
        Course course5 = new Course("History", "HIST101", null, null, 40, 3, classroom5);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify expected output: The total classroom capacity in the academic program is 130
        assertEquals("Total capacity should be 130 for classrooms with capacities 15, 20, 25, 30, 40", 130, result);
    }
}