import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Create a classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create a course and assign the classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        academicProgram.addCourse(course);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result matches expected output
        assertEquals(30, result);
    }
    
    /**
     * Test Case 2: "Multiple Classrooms with Different Capacities"
     * Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
     * the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
     * Expected Output: "The total classroom capacity in the academic program is 100"
     */
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Create first classroom with capacity 25
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        // Create second classroom with capacity 40
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        // Create third classroom with capacity 35
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create three courses and assign classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add all courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result matches expected output (25 + 40 + 35 = 100)
        assertEquals(100, result);
    }
    
    /**
     * Test Case 3: "Two Identical Classrooms"
     * Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
     * Expected Output: "The total classroom capacity in the academic program is 100"
     */
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Create a classroom with capacity 50
        Classroom classroom = new Classroom();
        classroom.setCapacity(50);
        
        // Create two courses and assign the same classroom (or identical classrooms)
        Course course1 = new Course();
        course1.setClassroom(classroom);
        
        Course course2 = new Course();
        course2.setClassroom(classroom);
        
        // Add both courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result matches expected output (50 + 50 = 100)
        assertEquals(100, result);
    }
    
    /**
     * Test Case 4: "Empty Academic Program"
     * Input: "An academic program with no courses (and thus no classrooms used)"
     * Expected Output: "The total classroom capacity in the academic program is 0"
     */
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // No courses added to academic program (empty program)
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result is 0 for empty program
        assertEquals(0, result);
    }
    
    /**
     * Test Case 5: "Large Number of Classrooms"
     * Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
     * Expected Output: "The total classroom capacity in the academic program is 130"
     */
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
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
        
        // Create five courses and assign classrooms
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
        
        // Add all courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result matches expected output (15 + 20 + 25 + 30 + 40 = 130)
        assertEquals(130, result);
    }
}