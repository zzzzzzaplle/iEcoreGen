import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private List<Student> students;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add(new Student());
        }
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForMultipleStudents() {
        // Test Case 1: "Average grade calculation for students in a specific course"
        // Input: "An AcademicProgram with a specific Course having a list of enrolled Students with different grades."
        // Expected Output: "The correct average grade of all the students in that specific Course within the Academic Program."
        
        // Setup enrollments with different grades
        List<Enrollment> enrollments = new ArrayList<>();
        double[] grades = {85.5, 92.0, 78.5, 88.0, 95.5};
        double expectedSum = 0;
        
        for (int i = 0; i < 5; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students.get(i));
            enrollment.setCourse(course1);
            enrollment.setGrade(grades[i]);
            enrollments.add(enrollment);
            expectedSum += grades[i];
        }
        
        course1.setEnrollments(enrollments);
        
        double expectedAverage = expectedSum / 5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                        expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Test Case 2: "Average grade calculation for a single-student course"
        // Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
        // Expected Output: "The grade of the single enrolled student, as it represents the average grade."
        
        // Setup enrollment with a single student
        List<Enrollment> enrollments = new ArrayList<>();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(students.get(0));
        enrollment.setCourse(course1);
        enrollment.setGrade(87.5);
        enrollments.add(enrollment);
        
        course1.setEnrollments(enrollments);
        
        double expectedAverage = 87.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the single student's grade", 
                        expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
        // Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
        
        // Course has no enrollments (empty list)
        course1.setEnrollments(new ArrayList<>());
        
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // With no students, the average should be 0 (since sum=0 and division by 0 results in NaN, but our implementation returns 0/0 = NaN in double)
        // However, looking at the implementation: sum=0, enrollments.size()=0, so 0/0 = NaN
        // But the test specification expects 0 or special value
        assertTrue("Average should be 0 when there are no students", 
                    Double.isNaN(actualAverage) || actualAverage == 0.0);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
        // Expected Output: "The common grade value as the average grade."
        
        // Setup enrollments with all same grades
        List<Enrollment> enrollments = new ArrayList<>();
        double commonGrade = 90.0;
        
        for (int i = 0; i < 4; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students.get(i));
            enrollment.setCourse(course1);
            enrollment.setGrade(commonGrade);
            enrollments.add(enrollment);
        }
        
        course1.setEnrollments(enrollments);
        
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                        expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
        // Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
        
        // Setup enrollments with mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        double[] grades = {85.0, 0.0, 92.5, 0.0, 88.5};
        double expectedSum = 0;
        
        for (int i = 0; i < 5; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students.get(i));
            enrollment.setCourse(course1);
            enrollment.setGrade(grades[i]);
            enrollments.add(enrollment);
            expectedSum += grades[i];
        }
        
        course1.setEnrollments(enrollments);
        
        double expectedAverage = expectedSum / 5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                        expectedAverage, actualAverage, 0.001);
    }
    

}