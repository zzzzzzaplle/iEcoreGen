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
        
        // Create course and assign classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        academicProgram.getClassrooms().add(classroom);
        
        // Verify total capacity
        assertEquals(30, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create classrooms with different capacities
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create courses and assign classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add courses and classrooms to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        
        academicProgram.getClassrooms().add(classroom1);
        academicProgram.getClassrooms().add(classroom2);
        academicProgram.getClassrooms().add(classroom3);
        
        // Verify total capacity (25 + 40 + 35 = 100)
        assertEquals(100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Create two identical classrooms with capacity 50
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        
        // Create courses and assign classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        // Add courses and classrooms to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        
        academicProgram.getClassrooms().add(classroom1);
        academicProgram.getClassrooms().add(classroom2);
        
        // Verify total capacity (50 + 50 = 100)
        assertEquals(100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // Expected Output: "The total classroom capacity in the academic program is 0"
        
        // Academic program is already empty from setUp()
        // Verify total capacity is 0
        assertEquals(0, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        // Expected Output: "The total classroom capacity in the academic program is 130"
        
        // Create classrooms with specified capacities
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(15);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(20);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(25);
        
        Classroom classroom4 = new Classroom();
        classroom4.setCapacity(30);
        
        Classroom classroom5 = new Classroom();
        classroom5.setCapacity(40);
        
        // Create courses and assign classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        Course course4 = new Course();
        course4.setClassroom(classroom4);
        
        Course course5 = new Course();
        course5.setClassroom(classroom5);
        
        // Add courses and classrooms to academic program
        academicProgram.getCourses().add(course1);
        academicProgram.getCourses().add(course2);
        academicProgram.getCourses().add(course3);
        academicProgram.getCourses().add(course4);
        academicProgram.getCourses().add(course5);
        
        academicProgram.getClassrooms().add(classroom1);
        academicProgram.getClassrooms().add(classroom2);
        academicProgram.getClassrooms().add(classroom3);
        academicProgram.getClassrooms().add(classroom4);
        academicProgram.getClassrooms().add(classroom5);
        
        // Verify total capacity (15 + 20 + 25 + 30 + 40 = 130)
        assertEquals(130, academicProgram.getTotalClassroomCapacity());
    }
}