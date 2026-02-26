import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Create a single course with a classroom capacity of 30
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.addCourse(course);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 30 for single classroom", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Create three courses with different classroom capacities
        Course course1 = new Course();
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        course3.setClassroom(classroom3);
        
        // Add all courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result (25 + 40 + 35 = 100)
        assertEquals("Total classroom capacity should be 100 for three classrooms with capacities 25, 40, 35", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Create two courses with identical classroom capacities of 50
        Course course1 = new Course();
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        course2.setClassroom(classroom2);
        
        // Add both courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result (50 + 50 = 100)
        assertEquals("Total classroom capacity should be 100 for two identical classrooms with capacity 50 each", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Academic program has no courses (already empty from setup)
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 0 for empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Create five courses with specified classroom capacities
        int[] capacities = {15, 20, 25, 30, 40};
        
        for (int capacity : capacities) {
            Course course = new Course();
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            course.setClassroom(classroom);
            academicProgram.addCourse(course);
        }
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result (15 + 20 + 25 + 30 + 40 = 130)
        assertEquals("Total classroom capacity should be 130 for five classrooms with capacities 15, 20, 25, 30, 40", 130, result);
    }
}