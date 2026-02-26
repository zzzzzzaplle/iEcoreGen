import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        // Expected Output: "The total classroom capacity in the academic program is 30"
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create course with the classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Verify total capacity is 30
        assertEquals("Total classroom capacity should be 30", 30, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create first classroom with capacity 25
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        // Create second classroom with capacity 40
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        // Create third classroom with capacity 35
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create courses with classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add all courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        
        // Verify total capacity is 100 (25 + 40 + 35)
        assertEquals("Total classroom capacity should be 100", 100, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create classroom with capacity 50
        Classroom classroom = new Classroom();
        classroom.setCapacity(50);
        
        // Create two courses with the same classroom
        Course course1 = new Course();
        course1.setClassroom(classroom);
        
        Course course2 = new Course();
        course2.setClassroom(classroom);
        
        // Add both courses to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        
        // Verify total capacity is 100 (50 + 50)
        assertEquals("Total classroom capacity should be 100", 100, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // Expected Output: "The total classroom capacity in the academic program is 0"
        
        // Academic program is already empty from setUp()
        // Verify total capacity is 0
        assertEquals("Total classroom capacity should be 0 for empty program", 0, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        // Expected Output: "The total classroom capacity in the academic program is 130"
        
        int[] capacities = {15, 20, 25, 30, 40};
        
        // Create classrooms with specified capacities
        for (int capacity : capacities) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            
            Course course = new Course();
            course.setClassroom(classroom);
            
            academicProgram.getCourses().add(course);
        }
        
        // Verify total capacity is 130 (15 + 20 + 25 + 30 + 40)
        assertEquals("Total classroom capacity should be 130", 130, academicProgram.sumClassroomCapacity());
    }
}