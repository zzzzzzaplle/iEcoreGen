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
        // Create a single course with a classroom of capacity 30
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program is 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Create 3 courses with different classroom capacities
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
        
        // Add all courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify
        int result = academicProgram.sumClassroomCapacity();