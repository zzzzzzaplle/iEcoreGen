import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram program;
    
    @Before
    public void setUp() {
        program = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        // Expected Output: "The total classroom capacity in the academic program is 30"
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create course that uses this classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        program.getCourses().add(course);
        program.getClassrooms().add(classroom);
        
        // Calculate total capacity
        int totalCapacity = program.getTotalClassroomCapacity();
        
        // Verify result
        assertEquals("The total classroom capacity in the academic program is 30", 30, totalCapacity);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create three classrooms with different capacities
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create three courses using these classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add courses and classrooms to academic program
        program.getCourses().add(course1);
        program.getCourses().add(course2);
        program.getCourses().add(course3);
        program.getClassrooms().add(classroom1);
        program.getClassrooms().add(classroom2);
        program.getClassrooms().add(classroom3);
        
        // Calculate total capacity
        int totalCapacity = program.getTotalClassroomCapacity();
        
        // Verify result
        assertEquals("The total classroom capacity in the academic program is 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create two classrooms with same capacity
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        
        // Create two courses using these classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        // Add courses and classrooms to academic program
        program.getCourses().add(course1);
        program.getCourses().add(course2);
        program.getClassrooms().add(classroom1);
        program.getClassrooms().add(classroom2);
        
        // Calculate total capacity
        int totalCapacity = program.getTotalClassroomCapacity();
        
        // Verify result
        assertEquals("The total classroom capacity in the academic program is 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // Expected Output: "The total classroom capacity in the academic program is 0"
        
        // Calculate total capacity on empty program
        int totalCapacity = program.getTotalClassroomCapacity();
        
        // Verify result
        assertEquals("The total classroom capacity in the academic program is 0", 0, totalCapacity);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        // Expected Output: "The total classroom capacity in the academic program is 130"
        
        // Create five classrooms with specified capacities
        int[] capacities = {15, 20, 25, 30, 40};
        List<Classroom> classrooms = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < capacities.length; i++) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacities[i]);
            classrooms.add(classroom);
            
            Course course = new Course();
            course.setClassroom(classroom);
            courses.add(course);
        }
        
        // Add all courses and classrooms to academic program
        program.setCourses(courses);
        program.setClassrooms(classrooms);
        
        // Calculate total capacity
        int totalCapacity = program.getTotalClassroomCapacity();
        
        // Verify result
        assertEquals("The total classroom capacity in the academic program is 130", 130, totalCapacity);
    }
}