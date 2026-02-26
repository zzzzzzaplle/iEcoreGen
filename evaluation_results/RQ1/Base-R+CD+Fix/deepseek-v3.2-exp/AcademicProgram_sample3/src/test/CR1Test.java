import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        
        // Initialize classrooms with different capacities
        classroom1 = new Classroom();
        classroom1.setCapacity(30);
        
        classroom2 = new Classroom();
        classroom2.setCapacity(25);
        
        classroom3 = new Classroom();
        classroom3.setCapacity(40);
        
        classroom4 = new Classroom();
        classroom4.setCapacity(35);
        
        classroom5 = new Classroom();
        classroom5.setCapacity(50);
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: Single Classroom in Academic Program
        // Input: An academic program with a single course using a classroom with a capacity of 30
        // Expected Output: The total classroom capacity in the academic program is 30
        
        // Setup
        course1 = new Course();
        course1.setClassroom(classroom1);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify
        assertEquals("Total capacity should be 30 for a single classroom", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: Multiple Classrooms with Different Capacities
        // Input: An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35
        // Expected Output: The total classroom capacity in the academic program is 100
        
        // Setup
        course1 = new Course();
        course1.setClassroom(classroom2); // capacity 25
        
        course2 = new Course();
        course2.setClassroom(classroom3); // capacity 40
        
        course3 = new Course();
        course3.setClassroom(classroom4); // capacity 35
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify
        assertEquals("Total capacity should be 100 for three classrooms with capacities 25, 40, 35", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: Two Identical Classrooms
        // Input: An academic program with 2 courses both using a classroom with a capacity of 50
        // Expected Output: The total classroom capacity in the academic program is 100
        
        // Setup
        Classroom identicalClassroom1 = new Classroom();
        identicalClassroom1.setCapacity(50);
        
        Classroom identicalClassroom2 = new Classroom();
        identicalClassroom2.setCapacity(50);
        
        course1 = new Course();
        course1.setClassroom(identicalClassroom1);
        
        course2 = new Course();
        course2.setClassroom(identicalClassroom2);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Execute
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify
        assertEquals("Total capacity should be 100 for two identical classrooms with capacity 50 each", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: Empty Academic Program
        // Input: An academic program with no courses (and thus no classrooms used)
        // Expected Output: The total classroom capacity in the academic program is 0
        
        // Setup - academic program is already empty from setUp()
        
        // Execute
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify
        assertEquals("Total capacity should be 0 for an empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: Large Number of Classrooms
        // Input: An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order
        // Expected Output: The total classroom capacity in the academic program is 130
        
        // Setup classrooms with specified capacities
        Classroom classroomA = new Classroom();
        classroomA.setCapacity(15);
        
        Classroom classroomB = new Classroom();
        classroomB.setCapacity(20);
        
        Classroom classroomC = new Classroom();
        classroomC.setCapacity(25);
        
        Classroom classroomD = new Classroom();
        classroomD.setCapacity(30);
        
        Classroom classroomE = new Classroom();
        classroomE.setCapacity(40);
        
        // Setup courses with the classrooms
        course1 = new Course();
        course1.setClassroom(classroomA);
        
        course2 = new Course();
        course2.setClassroom(classroomB);
        
        course3 = new Course();
        course3.setClassroom(classroomC);
        
        course4 = new Course();
        course4.setClassroom(classroomD);
        
        course5 = new Course();
        course5.setClassroom(classroomE);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Execute
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify
        assertEquals("Total capacity should be 130 for five classrooms with capacities 15, 20, 25, 30, 40", 130, result);
    }
}