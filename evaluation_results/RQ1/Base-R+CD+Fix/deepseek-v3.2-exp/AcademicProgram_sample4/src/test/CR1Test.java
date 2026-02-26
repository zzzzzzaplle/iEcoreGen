import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize a fresh AcademicProgram before each test
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_SingleClassroomInAcademicProgram() {
        // Test Case 1: "Single Classroom in Academic Program"
        // Input: "An academic program with a single course using a classroom with a capacity of 30"
        
        // Create a classroom with capacity 30
        Classroom classroom = new Classroom();
        classroom.setCapacity(30);
        
        // Create a course and assign the classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add the course to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify result
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program should be 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Test Case 2: "Multiple Classrooms with Different Capacities"
        // Input: "An academic program with 3 courses. The first course uses a classroom with a capacity of 25, 
        // the second uses a classroom with a capacity of 40, and the third uses a classroom with a capacity of 35"
        
        // Create classrooms with different capacities
        Classroom classroom1 = new Classroom();
        classroom1.setCapacity(25);
        
        Classroom classroom2 = new Classroom();
        classroom2.setCapacity(40);
        
        Classroom classroom3 = new Classroom();
        classroom3.setCapacity(35);
        
        // Create courses and assign classrooms
        Course course1 = new Course();
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setClassroom(classroom3);
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify result
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program should be 100", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Test Case 3: "Two Identical Classrooms"
        // Input: "An academic program with 2 courses both using a classroom with a capacity of 50"
        
        // Create a classroom with capacity 50
        Classroom classroom = new Classroom();
        classroom.setCapacity(50);
        
        // Create two courses and assign the same classroom
        Course course1 = new Course();
        course1.setClassroom(classroom);
        
        Course course2 = new Course();
        course2.setClassroom(classroom);
        
        // Add courses to the academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify result
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program should be 100", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Test Case 4: "Empty Academic Program"
        // Input: "An academic program with no courses (and thus no classrooms used)"
        
        // Academic program is already empty from setUp()
        // Calculate total capacity and verify result
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in an empty academic program should be 0", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Test Case 5: "Large Number of Classrooms"
        // Input: "An academic program with 5 courses. The classroom capacities for each course 
        // are 15, 20, 25, 30, 40 in order"
        
        // Create classrooms with specified capacities
        int[] capacities = {15, 20, 25, 30, 40};
        List<Course> courses = new ArrayList<>();
        
        for (int capacity : capacities) {
            Classroom classroom = new Classroom();
            classroom.setCapacity(capacity);
            
            Course course = new Course();
            course.setClassroom(classroom);
            
            courses.add(course);
        }
        
        // Add courses to the academic program
        academicProgram.setCourses(courses);
        
        // Calculate total capacity and verify result
        int result = academicProgram.sumClassroomCapacity();
        assertEquals("The total classroom capacity in the academic program should be 130", 130, result);
    }
}