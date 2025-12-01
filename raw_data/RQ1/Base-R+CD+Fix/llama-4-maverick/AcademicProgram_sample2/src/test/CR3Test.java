import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course testCourse;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        academicProgram = new AcademicProgram();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() throws Exception {
        // Create a course with multiple students having different grades
        Classroom classroom = new Classroom("C101", 50, "1", "A");
        Date startTime = dateFormat.parse("2024-01-15 09:00:00");
        Date endTime = dateFormat.parse("2024-01-15 11:00:00");
        List<String> courseDays = Arrays.asList("Monday", "Wednesday");
        
        testCourse = new Course("Mathematics", "MATH101", startTime, endTime, 
                               courseDays, 50, 4, classroom);
        
        // Create students and enroll them with different grades
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        
        // Enroll students
        student1.enrollInCourse(testCourse);
        student2.enrollInCourse(testCourse);
        student3.enrollInCourse(testCourse);
        
        // Set grades for each enrollment
        testCourse.getEnrollments().get(0).setGrade(85.5);
        testCourse.getEnrollments().get(1).setGrade(92.0);
        testCourse.getEnrollments().get(2).setGrade(78.5);
        
        // Add course to academic program
        academicProgram.setCourses(Arrays.asList(testCourse));
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() throws Exception {
        // Create a course with only one student
        Classroom classroom = new Classroom("C102", 30, "2", "B");
        Date startTime = dateFormat.parse("2024-01-15 13:00:00");
        Date endTime = dateFormat.parse("2024-01-15 15:00:00");
        List<String> courseDays = Arrays.asList("Tuesday", "Thursday");
        
        testCourse = new Course("Physics", "PHYS101", startTime, endTime, 
                               courseDays, 30, 3, classroom);
        
        // Create and enroll one student
        Student student = new Student();
        student.enrollInCourse(testCourse);
        
        // Set grade for the single student
        testCourse.getEnrollments().get(0).setGrade(88.0);
        
        // Add course to academic program
        academicProgram.setCourses(Arrays.asList(testCourse));
        
        // For single student, average should equal the student's grade
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.calculateAverageGrade("PHYS101");
        
        assertEquals("Average grade for single student should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() throws Exception {
        // Create a course with no enrolled students
        Classroom classroom = new Classroom("C103", 40, "3", "C");
        Date startTime = dateFormat.parse("2024-01-15 10:00:00");
        Date endTime = dateFormat.parse("2024-01-15 12:00:00");
        List<String> courseDays = Arrays.asList("Monday", "Friday");
        
        testCourse = new Course("Chemistry", "CHEM101", startTime, endTime, 
                               courseDays, 40, 3, classroom);
        
        // No students enrolled - enrollments list is empty
        
        // Add course to academic program
        academicProgram.setCourses(Arrays.asList(testCourse));
        
        // For course with no students, should return 0 as specified in the method
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CHEM101");
        
        assertEquals("Average grade for course with no students should return 0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() throws Exception {
        // Create a course with multiple students having identical grades
        Classroom classroom = new Classroom("C104", 35, "1", "D");
        Date startTime = dateFormat.parse("2024-01-15 14:00:00");
        Date endTime = dateFormat.parse("2024-01-15 16:00:00");
        List<String> courseDays = Arrays.asList("Wednesday", "Friday");
        
        testCourse = new Course("Biology", "BIO101", startTime, endTime, 
                               courseDays, 35, 4, classroom);
        
        // Create students and enroll them with same grades
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        
        // Enroll students
        student1.enrollInCourse(testCourse);
        student2.enrollInCourse(testCourse);
        student3.enrollInCourse(testCourse);
        student4.enrollInCourse(testCourse);
        
        // Set identical grades for all enrollments
        for (Enrollment enrollment : testCourse.getEnrollments()) {
            enrollment.setGrade(75.0);
        }
        
        // Add course to academic program
        academicProgram.setCourses(Arrays.asList(testCourse));
        
        // All students have same grade, so average should be that grade
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("BIO101");
        
        assertEquals("Average grade with all same grades should equal the common grade value", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() throws Exception {
        // Create a course with mix of valid and zero grades
        Classroom classroom = new Classroom("C105", 45, "2", "E");
        Date startTime = dateFormat.parse("2024-01-15 11:00:00");
        Date endTime = dateFormat.parse("2024-01-15 13:00:00");
        List<String> courseDays = Arrays.asList("Tuesday", "Thursday");
        
        testCourse = new Course("Computer Science", "CS101", startTime, endTime, 
                               courseDays, 45, 4, classroom);
        
        // Create students and enroll them
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        
        // Enroll students
        student1.enrollInCourse(testCourse);
        student2.enrollInCourse(testCourse);
        student3.enrollInCourse(testCourse);
        student4.enrollInCourse(testCourse);
        
        // Set mix of valid and zero grades
        testCourse.getEnrollments().get(0).setGrade(90.0);  // Valid grade
        testCourse.getEnrollments().get(1).setGrade(0.0);   // Zero grade
        testCourse.getEnrollments().get(2).setGrade(85.0);  // Valid grade
        testCourse.getEnrollments().get(3).setGrade(0.0);   // Zero grade
        
        // Add course to academic program
        academicProgram.setCourses(Arrays.asList(testCourse));
        
        // Calculate expected average: (90.0 + 0.0 + 85.0 + 0.0) / 4 = 43.75
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}