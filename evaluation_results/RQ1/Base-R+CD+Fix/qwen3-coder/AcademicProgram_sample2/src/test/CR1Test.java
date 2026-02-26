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
        // Input: An academic program with a single course using a classroom with a capacity of 30
        
        // Create classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create course and set classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Expected Output: The total classroom capacity in the academic program is 30
        assertEquals("The total classroom capacity in the academic program is 30", 30, totalCapacity);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Input: An academic program with 3 courses with classrooms of capacities 25, 40, and 35
        
        // Create classrooms
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create courses and set classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Expected Output: The total classroom capacity in the academic program is 100
        assertEquals("The total classroom capacity in the academic program is 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Input: An academic program with 2 courses both using classrooms with capacity of 50
        
        // Create classrooms with capacity 50
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        
        // Create courses and set classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Expected Output: The total classroom capacity in the academic program is 100
        assertEquals("The total classroom capacity in the academic program is 100", 100, totalCapacity);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Input: An academic program with no courses (and thus no classrooms used)
        
        // Academic program is already empty from setUp()
        
        // Calculate total capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Expected Output: The total classroom capacity in the academic program is 0
        assertEquals("The total classroom capacity in the academic program is 0", 0, totalCapacity);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Input: An academic program with 5 courses with classroom capacities 15, 20, 25, 30, 40
        
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
        
        // Create courses and set classrooms
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
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity
        int totalCapacity = academicProgram.sumClassroomCapacity();
        
        // Expected Output: The total classroom capacity in the academic program is 130
        assertEquals("The total classroom capacity in the academic program is 130", 130, totalCapacity);
    }
}