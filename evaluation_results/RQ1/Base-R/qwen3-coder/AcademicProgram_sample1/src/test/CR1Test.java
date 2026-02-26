import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize fresh AcademicProgram before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create course and assign classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.addCourse(course);
        
        // Verify total classroom capacity is 30
        assertEquals("The total classroom capacity in the academic program is 30", 
                     30, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        
        // Create first classroom with capacity 25
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        // Create second classroom with capacity 40
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        // Create third classroom with capacity 35
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add all courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Verify total classroom capacity is 100 (25 + 40 + 35)
        assertEquals("The total classroom capacity in the academic program is 100", 
                     100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        
        // Create first classroom with capacity 50
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        // Create second classroom with capacity 50
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        // Add both courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Verify total classroom capacity is 100 (50 + 50)
        assertEquals("The total classroom capacity in the academic program is 100", 
                     100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        
        // No courses added to academic program
        
        // Verify total classroom capacity is 0
        assertEquals("The total classroom capacity in the academic program is 0", 
                     0, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        
        // Create classrooms with specified capacities
        int[] capacities = {15, 20, 25, 30, 40};
        
        for (int capacity : capacities) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            Course course = new Course();
            course.setClassroom(classroom);
            academicProgram.addCourse(course);
        }
        
        // Verify total classroom capacity is 130 (15+20+25+30+40)
        assertEquals("The total classroom capacity in the academic program is 130", 
                     130, academicProgram.getTotalClassroomCapacity());
    }
}