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
        // Setup: An academic program with a single course using a classroom with a capacity of 30
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity in the academic program is 30
        assertEquals(30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Setup: An academic program with 3 courses with different classroom capacities
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
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity in the academic program is 100
        assertEquals(100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Setup: An academic program with 2 courses both using a classroom with a capacity of 50
        Course course1 = new Course();
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(50);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(50);
        course2.setClassroom(classroom2);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity in the academic program is 100
        assertEquals(100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Setup: An academic program with no courses (and thus no classrooms used)
        // academicProgram is already empty from setUp()
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity in the academic program is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Setup: An academic program with 5 courses with classroom capacities 15, 20, 25, 30, 40
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
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: The total classroom capacity in the academic program is 130
        assertEquals(130, result);
    }
}