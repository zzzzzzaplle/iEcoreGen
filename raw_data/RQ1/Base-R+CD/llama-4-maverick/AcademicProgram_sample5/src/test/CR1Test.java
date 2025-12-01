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
        // Create classroom with capacity 30
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        
        // Create course using the classroom
        Course course = new Course();
        course.setClassroom(classroom);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate total capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total capacity for single classroom should be 30", 30, result);
    }
    
    @Test
    public void testCase2_MultipleClassroomsWithDifferentCapacities() {
        // Create classrooms with different capacities
        Classroom classroom1 = new Classroom("B201", 25, "2", "B");
        Classroom classroom2 = new Classroom("C301", 40, "3", "C");
        Classroom classroom3 = new Classroom("D401", 35, "4", "D");
        
        // Create courses using the classrooms
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
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify the result (25 + 40 + 35 = 100)
        assertEquals("Total capacity for multiple classrooms should be 100", 100, result);
    }
    
    @Test
    public void testCase3_TwoIdenticalClassrooms() {
        // Create two identical classrooms with capacity 50
        Classroom classroom1 = new Classroom("E101", 50, "1", "E");
        Classroom classroom2 = new Classroom("E102", 50, "1", "E");
        
        // Create courses using the classrooms
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
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify the result (50 + 50 = 100)
        assertEquals("Total capacity for two identical classrooms should be 100", 100, result);
    }
    
    @Test
    public void testCase4_EmptyAcademicProgram() {
        // Academic program is already empty from setUp()
        
        // Calculate total capacity
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total capacity for empty academic program should be 0", 0, result);
    }
    
    @Test
    public void testCase5_LargeNumberOfClassrooms() {
        // Create classrooms with specified capacities
        Classroom classroom1 = new Classroom("F101", 15, "1", "F");
        Classroom classroom2 = new Classroom("F102", 20, "1", "F");
        Classroom classroom3 = new Classroom("F201", 25, "2", "F");
        Classroom classroom4 = new Classroom("F202", 30, "2", "F");
        Classroom classroom5 = new Classroom("F301", 40, "3", "F");
        
        // Create courses using the classrooms
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
        
        // Calculate total capacity (15 + 20 + 25 + 30 + 40 = 130)
        int result = academicProgram.sumClassroomCapacity();
        
        // Verify the result
        assertEquals("Total capacity for large number of classrooms should be 130", 130, result);
    }
}