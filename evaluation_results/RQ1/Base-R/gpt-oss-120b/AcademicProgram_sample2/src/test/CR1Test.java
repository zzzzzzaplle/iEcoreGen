import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    private Classroom classroom1;
    private Classroom classroom2;
    private Classroom classroom3;
    private Classroom classroom4;
    private Classroom classroom5;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        classroom1 = new Classroom();
        classroom2 = new Classroom();
        classroom3 = new Classroom();
        classroom4 = new Classroom();
        classroom5 = new Classroom();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        course5 = new Course();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        // Expected Output: "The total classroom capacity in the academic program is 30"
        
        // Setup classroom with capacity 30
        classroom1.setCapacity(30);
        course1.setClassroom(classroom1);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Verify total classroom capacity is 30
        assertEquals(30, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Setup classrooms with different capacities
        classroom1.setCapacity(25);
        classroom2.setCapacity(40);
        classroom3.setCapacity(35);
        
        // Assign classrooms to courses
        course1.setClassroom(classroom1);
        course2.setClassroom(classroom2);
        course3.setClassroom(classroom3);
        
        // Add courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Verify total classroom capacity is 100 (25 + 40 + 35)
        assertEquals(100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        // Expected Output: "The total classroom capacity in the academic program is 100"
        
        // Setup two classrooms with same capacity
        classroom1.setCapacity(50);
        classroom2.setCapacity(50);
        
        // Assign classrooms to courses
        course1.setClassroom(classroom1);
        course2.setClassroom(classroom2);
        
        // Add courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Verify total classroom capacity is 100 (50 + 50)
        assertEquals(100, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // Expected Output: "The total classroom capacity in the academic program is 0"
        
        // Academic program has no courses by default
        // Verify total classroom capacity is 0
        assertEquals(0, academicProgram.getTotalClassroomCapacity());
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        // Expected Output: "The total classroom capacity in the academic program is 130"
        
        // Setup classrooms with specified capacities
        classroom1.setCapacity(15);
        classroom2.setCapacity(20);
        classroom3.setCapacity(25);
        classroom4.setCapacity(30);
        classroom5.setCapacity(40);
        
        // Assign classrooms to courses
        course1.setClassroom(classroom1);
        course2.setClassroom(classroom2);
        course3.setClassroom(classroom3);
        course4.setClassroom(classroom4);
        course5.setClassroom(classroom5);
        
        // Add all courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        
        // Verify total classroom capacity is 130 (15 + 20 + 25 + 30 + 40)
        assertEquals(130, academicProgram.getTotalClassroomCapacity());
    }
}