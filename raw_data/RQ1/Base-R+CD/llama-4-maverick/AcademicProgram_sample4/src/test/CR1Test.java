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
        // Setup: Create academic program with a single course using a classroom with capacity 30
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        Course course = new Course();
        course.setClassroom(classroom);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 30
        assertEquals("Total classroom capacity should be 30 for single classroom", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Setup: Create academic program with 3 courses using classrooms with different capacities
        Classroom classroom1 = new Classroom("C101", 25, "1", "A");
        Classroom classroom2 = new Classroom("C102", 40, "1", "A");
        Classroom classroom3 = new Classroom("C103", 35, "2", "A");
        
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 100 (25 + 40 + 35)
        assertEquals("Total classroom capacity should be 100 for multiple classrooms", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Setup: Create academic program with 2 courses both using classrooms with capacity 50
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 100 (50 + 50)
        assertEquals("Total classroom capacity should be 100 for two identical classrooms", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Setup: Academic program with no courses (empty list)
        academicProgram.setCourses(new ArrayList<Course>());
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 0
        assertEquals("Total classroom capacity should be 0 for empty academic program", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Setup: Create academic program with 5 courses using classrooms with capacities 15, 20, 25, 30, 40
        Classroom classroom1 = new Classroom("C101", 15, "1", "A");
        Classroom classroom2 = new Classroom("C102", 20, "1", "A");
        Classroom classroom3 = new Classroom("C103", 25, "2", "A");
        Classroom classroom4 = new Classroom("C104", 30, "2", "A");
        Classroom classroom5 = new Classroom("C105", 40, "3", "A");
        
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
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate total classroom capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify: Total capacity should be 130 (15 + 20 + 25 + 30 + 40)
        assertEquals("Total classroom capacity should be 130 for large number of classrooms", 130, result);
    }
}