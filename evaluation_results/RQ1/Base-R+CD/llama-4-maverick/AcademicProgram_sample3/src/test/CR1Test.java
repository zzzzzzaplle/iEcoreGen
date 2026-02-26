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
        // Setup: Create academic program with a single course using a classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        Course course = new Course();
        course.setClassroom(classroom);
        
        academicProgram.setCourses(new ArrayList<Course>());
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 30
        assertEquals("Total classroom capacity should be 30 for single classroom", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Setup: Create academic program with 3 courses using classrooms with capacities 25, 40, 35
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        List<Course> courses = new ArrayList<Course>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 100 (25+40+35)
        assertEquals("Total classroom capacity should be 100 for multiple classrooms", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Setup: Create academic program with 2 courses both using classrooms with capacity 50
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        List<Course> courses = new ArrayList<Course>();
        courses.add(course1);
        courses.add(course2);
        
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 100 (50+50)
        assertEquals("Total classroom capacity should be 100 for two identical classrooms", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Setup: Academic program with no courses (empty courses list)
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 0 when no courses exist
        assertEquals("Total classroom capacity should be 0 for empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Setup: Create academic program with 5 courses using classrooms with capacities 15, 20, 25, 30, 40
        int[] capacities = {15, 20, 25, 30, 40};
        List<Course> courses = new ArrayList<Course>();
        
        for (int i = 0; i < capacities.length; i++) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacities[i]);
            
            Course course = new Course();
            course.setClassroom(classroom);
            
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 130 (15+20+25+30+40)
        assertEquals("Total classroom capacity should be 130 for 5 classrooms", 130, result);
    }
}