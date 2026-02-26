import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private Classroom classroom;
    
    @Before
    public void setUp() {
        // Initialize fresh objects before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: An academic program with a single course using a classroom with a capacity of 30
        
        // Create classroom with capacity 30
        classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create course and assign classroom
        course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.addCourse(course);
        
        // Calculate total capacity and verify expected output
        int result = academicProgram.totalClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: An academic program with 3 courses with capacities 25, 40, and 35
        
        // Create first classroom and course (capacity 25)
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        academicProgram.addCourse(course1);
        
        // Create second classroom and course (capacity 40)
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        academicProgram.addCourse(course2);
        
        // Create third classroom and course (capacity 35)
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        academicProgram.addCourse(course3);
        
        // Calculate total capacity and verify expected output
        int result = academicProgram.totalClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 100", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: An academic program with 2 courses both using classrooms with capacity 50
        
        // Create first classroom and course (capacity 50)
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        academicProgram.addCourse(course1);
        
        // Create second classroom and course (capacity 50)
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        academicProgram.addCourse(course2);
        
        // Calculate total capacity and verify expected output
        int result = academicProgram.totalClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 100", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: An academic program with no courses (empty by default)
        
        // Calculate total capacity and verify expected output (should be 0 for empty program)
        int result = academicProgram.totalClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 0", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: An academic program with 5 courses with capacities 15, 20, 25, 30, 40
        
        // Create classrooms and courses with specified capacities
        int[] capacities = {15, 20, 25, 30, 40};
        
        for (int capacity : capacities) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            Course course = new Course();
            course.setClassroom(classroom);
            academicProgram.addCourse(course);
        }
        
        // Calculate total capacity and verify expected output
        int result = academicProgram.totalClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 130", 130, result);
    }
}