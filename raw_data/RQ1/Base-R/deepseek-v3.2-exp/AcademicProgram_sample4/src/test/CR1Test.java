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
        // Create a single course with a classroom capacity of 30
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
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
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
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
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result (50 + 50 = 100)
        assertEquals("Total classroom capacity should be 100 for two identical classrooms with capacity 50", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Academic program has no courses (already set by setUp method)
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result
        assertEquals("Total classroom capacity should be 0 for empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Create five courses with classroom capacities 15, 20, 25, 30, 40
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
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Calculate total classroom capacity
        int result = academicProgram.calculateTotalClassroomCapacity();
        
        // Verify the result (15 + 20 + 25 + 30 + 40 = 130)
        assertEquals("Total classroom capacity should be 130 for five classrooms with capacities 15, 20, 25, 30, 40", 130, result);
    }
}