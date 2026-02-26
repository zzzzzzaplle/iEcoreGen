import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Classroom classroom;

    @Before
    public void setUp() {
        // Setup common test objects
        academicProgram = new AcademicProgram();
        
        classroom = new Classroom();
        classroom.setCapacity(50);
        
        course1 = new Course();
        course1.setName("Mathematics");
        course1.setCode("MATH101");
        course1.setClassroom(classroom);
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        
        course2 = new Course();
        course2.setName("Physics");
        course2.setCode("PHYS101");
        course2.setClassroom(classroom);
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        
        student1 = new Student();
        student1.setName("John");
        student1.setSurname("Doe");
        
        student2 = new Student();
        student2.setName("Jane");
        student2.setSurname("Smith");
        
        student3 = new Student();
        student3.setName("Bob");
        student3.setSurname("Johnson");
        
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Add students with different grades to course1
        course1.addStudent(student1, 85);
        course1.addStudent(student2, 92);
        course1.addStudent(student3, 78);
        
        // Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGrade(course1);
        
        // Expected output: The correct average grade of all the students in that specific Course within the Academic Program
        // (85 + 92 + 78) / 3 = 85.0
        assertEquals(85.0, averageGrade, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Add only one student with a specific grade to course1
        course1.addStudent(student1, 90);
        
        // Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGrade(course1);
        
        // Expected output: The grade of the single enrolled student, as it represents the average grade
        assertEquals(90.0, averageGrade, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Course1 has no students enrolled
        
        // Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGrade(course1);
        
        // Expected output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        assertEquals(0.0, averageGrade, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Add students with all same grades to course1
        course1.addStudent(student1, 75);
        course1.addStudent(student2, 75);
        course1.addStudent(student3, 75);
        
        // Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGrade(course1);
        
        // Expected output: The common grade value as the average grade
        assertEquals(75.0, averageGrade, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Add students with mix of valid and zero grades to course1
        course1.addStudent(student1, 88);
        course1.addStudent(student2, 0);
        course1.addStudent(student3, 95);
        
        // Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGrade(course1);
        
        // Expected output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        // (88 + 0 + 95) / 3 = 61.0
        assertEquals(61.0, averageGrade, 0.001);
    }

}