import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
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
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        Course course = new Course();
        course.setClassroom(classroom);
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Expected Output: The total classroom capacity in the academic program is 30
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("Total classroom capacity should be 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Input: An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35
        Classroom classroom1 = new Classroom("C101", 25, "1", "A");
        Classroom classroom2 = new Classroom("C102", 40, "1", "A");
        Classroom classroom3 = new Classroom("C103", 35, "1", "A");
        
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        academicProgram.setCourses(courses);
        
        // Expected Output: The total classroom capacity in the academic program is 100
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("Total classroom capacity should be 100", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Input: An academic program with 2 courses both using a classroom with a capacity of 50
        Classroom classroom = new Classroom("C101", 50, "1", "A");
        
        Course course1 = new Course();
        course1.setClassroom(classroom);
        Course course2 = new Course();
        course2.setClassroom(classroom);
        
        List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2));
        academicProgram.setCourses(courses);
        
        // Expected Output: The total classroom capacity in the academic program is 100
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("Total classroom capacity should be 100", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Input: An academic program with no courses (and thus no classrooms used)
        academicProgram.setCourses(new ArrayList<>());
        
        // Expected Output: The total classroom capacity in the academic program is 0
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("Total classroom capacity should be 0", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Input: An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order
        Classroom classroom1 = new Classroom("C101", 15, "1", "A");
        Classroom classroom2 = new Classroom("C102", 20, "1", "A");
        Classroom classroom3 = new Classroom("C103", 25, "1", "A");
        Classroom classroom4 = new Classroom("C104", 30, "1", "A");
        Classroom classroom5 = new Classroom("C105", 40, "1", "A");
        
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
        
        List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2, course3, course4, course5));
        academicProgram.setCourses(courses);
        
        // Expected Output: The total classroom capacity in the academic program is 130
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("Total classroom capacity should be 130", 130, result);
    }
}