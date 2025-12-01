import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        academicProgram.setCourses(new ArrayList<>());
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program.
        
        // Setup course with multiple enrollments and different grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.0);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.0);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Calculate average and verify
        double result = academicProgram.calculateAverageGrade("CS101");
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3; // 85.0
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        // Expected Output: The grade of the single enrolled student, as it represents the average grade.
        
        // Setup course with single enrollment
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setGrade(95.5);
        enrollments.add(enrollment);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Calculate average and verify
        double result = academicProgram.calculateAverageGrade("CS101");
        assertEquals("Average grade for single student should equal the student's grade", 
                     95.5, result, 0.001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from.
        
        // Setup course with empty enrollments
        course.setEnrollments(new ArrayList<>());
        academicProgram.getCourses().add(course);
        
        // This should throw IllegalArgumentException
        academicProgram.calculateAverageGrade("CS101");
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        // Expected Output: The common grade value as the average grade.
        
        // Setup course with multiple enrollments having same grade
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(88.0);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(88.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(88.0);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Calculate average and verify
        double result = academicProgram.calculateAverageGrade("CS101");
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     88.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students.
        
        // Setup course with mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(75.0);
        enrollments.add(enrollment3);
        
        Student student4 = new Student();
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setStudent(student4);
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Calculate average and verify
        double result = academicProgram.calculateAverageGrade("CS101");
        double expectedAverage = (90.0 + 0.0 + 75.0 + 0.0) / 4; // 41.25
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                     expectedAverage, result, 0.001);
    }
}