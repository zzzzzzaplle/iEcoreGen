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
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        Course course = new Course();
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        course.setClassroom(classroom);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Expected Output: "The total classroom capacity in the academic program is 30"
        assertEquals(30, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
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
        
        // Expected Output: "The total classroom capacity in the academic program is 100"
        assertEquals(100, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
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
        
        // Expected Output: "The total classroom capacity in the academic program is 100"
        assertEquals(100, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Input: "An academic program with no courses (and thus no classrooms used)"
        // academicProgram is already empty from setUp()
        
        // Expected Output: "The total classroom capacity in the academic program is 0"
        assertEquals(0, academicProgram.sumClassroomCapacity());
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Input: "An academic program with 5 courses. The classroom capacities for each course are 15, 20, 25, 30, 40 in order"
        int[] capacities = {15, 20, 25, 30, 40};
        List<Course> courses = new ArrayList<>();
        
        for (int capacity : capacities) {
            Course course = new Course();
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            course.setClassroom(classroom);
            courses.add(course);
        }
        
        academicProgram.setCourses(courses);
        
        // Expected Output: "The total classroom capacity in the academic program is 130"
        assertEquals(130, academicProgram.sumClassroomCapacity());
    }
}