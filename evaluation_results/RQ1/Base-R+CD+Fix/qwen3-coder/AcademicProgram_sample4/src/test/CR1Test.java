import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram program;
    
    @Before
    public void setUp() {
        program = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Setup: Create academic program with a single course using a classroom with capacity 30
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        program.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = program.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 30
        assertEquals("Single classroom with capacity 30 should return 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Setup: Create academic program with 3 courses using different classrooms
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
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        program.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = program.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 100 (25+40+35)
        assertEquals("Multiple classrooms with capacities 25, 40, 35 should return 100", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Setup: Create academic program with 2 courses using the same classroom
        Classroom sharedClassroom = new Classroom();
        sharedClassroom.setCapacity(50);
        
        Course course1 = new Course();
        course1.setClassroom(sharedClassroom);
        
        Course course2 = new Course();
        course2.setClassroom(sharedClassroom);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        program.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = program.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 50 (same classroom counted only once)
        assertEquals("Two courses using same classroom with capacity 50 should return 50", 50, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Setup: Academic program with no courses (already set by setUp method)
        
        // Execute: Calculate total classroom capacity
        int result = program.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 0
        assertEquals("Empty academic program should return 0", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Setup: Create academic program with 5 courses using different classrooms
        Course course1 = new Course();
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(15);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(20);
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(25);
        course3.setClassroom(classroom3);
        
        Course course4 = new Course();
        Classroom classroom4 = new Classroom();
        classroom4.setCapacity(30);
        course4.setClassroom(classroom4);
        
        Course course5 = new Course();
        Classroom classroom5 = new Classroom();
        classroom5.setCapacity(40);
        course5.setClassroom(classroom5);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        program.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = program.sumClassroomCapacity();
        
        // Verify: The total classroom capacity should be 130 (15+20+25+30+40)
        assertEquals("Five classrooms with capacities 15, 20, 25, 30, 40 should return 130", 130, result);
    }
}