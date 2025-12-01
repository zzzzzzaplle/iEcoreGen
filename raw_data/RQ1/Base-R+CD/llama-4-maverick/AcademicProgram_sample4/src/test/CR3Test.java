import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course courseWithMultipleStudents;
    private Course courseWithSingleStudent;
    private Course courseWithNoStudents;
    private Course courseWithSameGrades;
    private Course courseWithMixedGrades;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        // Initialize date format for creating test dates
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create classroom for all courses
        Classroom classroom = new Classroom("A101", 50, "1", "A");
        
        // Create test courses with different enrollment scenarios
        courseWithMultipleStudents = new Course(
            "Mathematics", "MATH101", 
            dateFormat.parse("2024-01-15 09:00:00"), 
            dateFormat.parse("2024-01-15 10:30:00"), 
            10, 3, classroom
        );
        
        courseWithSingleStudent = new Course(
            "Physics", "PHYS101", 
            dateFormat.parse("2024-01-15 11:00:00"), 
            dateFormat.parse("2024-01-15 12:30:00"), 
            5, 3, classroom
        );
        
        courseWithNoStudents = new Course(
            "Chemistry", "CHEM101", 
            dateFormat.parse("2024-01-15 14:00:00"), 
            dateFormat.parse("2024-01-15 15:30:00"), 
            8, 3, classroom
        );
        
        courseWithSameGrades = new Course(
            "Biology", "BIO101", 
            dateFormat.parse("2024-01-16 09:00:00"), 
            dateFormat.parse("2024-01-16 10:30:00"), 
            4, 3, classroom
        );
        
        courseWithMixedGrades = new Course(
            "Computer Science", "CS101", 
            dateFormat.parse("2024-01-16 11:00:00"), 
            dateFormat.parse("2024-01-16 12:30:00"), 
            6, 3, classroom
        );
        
        // Create students and enroll them in courses with different grades
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Student student4 = new Student();
        Student student5 = new Student();
        
        // Course with multiple students: different grades
        student1.enrollInCourse(courseWithMultipleStudents);
        courseWithMultipleStudents.getEnrollments().get(0).setGrade(85.5);
        student2.enrollInCourse(courseWithMultipleStudents);
        courseWithMultipleStudents.getEnrollments().get(1).setGrade(92.0);
        student3.enrollInCourse(courseWithMultipleStudents);
        courseWithMultipleStudents.getEnrollments().get(2).setGrade(78.5);
        
        // Course with single student
        student4.enrollInCourse(courseWithSingleStudent);
        courseWithSingleStudent.getEnrollments().get(0).setGrade(88.0);
        
        // Course with same grades (all students get 90.0)
        student1.enrollInCourse(courseWithSameGrades);
        courseWithSameGrades.getEnrollments().get(0).setGrade(90.0);
        student2.enrollInCourse(courseWithSameGrades);
        courseWithSameGrades.getEnrollments().get(1).setGrade(90.0);
        student3.enrollInCourse(courseWithSameGrades);
        courseWithSameGrades.getEnrollments().get(2).setGrade(90.0);
        
        // Course with mixed grades (valid and zero grades)
        student1.enrollInCourse(courseWithMixedGrades);
        courseWithMixedGrades.getEnrollments().get(0).setGrade(75.0);
        student2.enrollInCourse(courseWithMixedGrades);
        courseWithMixedGrades.getEnrollments().get(1).setGrade(0.0);
        student3.enrollInCourse(courseWithMixedGrades);
        courseWithMixedGrades.getEnrollments().get(2).setGrade(85.5);
        student4.enrollInCourse(courseWithMixedGrades);
        courseWithMixedGrades.getEnrollments().get(3).setGrade(0.0);
        
        // Create academic program with all test courses
        List<Course> courses = new ArrayList<>();
        courses.add(courseWithMultipleStudents);
        courses.add(courseWithSingleStudent);
        courses.add(courseWithNoStudents);
        courses.add(courseWithSameGrades);
        courses.add(courseWithMixedGrades);
        
        academicProgram = new AcademicProgram(courses);
    }

    @Test
    public void testCase1_AverageGradeCalculationForMultipleStudents() {
        // Test average grade calculation for a course with multiple students having different grades
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Expected: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        assertEquals(85.333, result, 0.001); // Allow for floating-point precision
    }

    @Test
    public void testCase2_AverageGradeCalculationForSingleStudent() {
        // Test average grade calculation for a course with only one student
        double result = academicProgram.calculateAverageGrade("PHYS101");
        
        // Expected: The single student's grade (88.0)
        assertEquals(88.0, result, 0.001);
    }

    @Test
    public void testCase3_AverageGradeCalculationForNoStudents() {
        // Test average grade calculation for a course with no enrolled students
        double result = academicProgram.calculateAverageGrade("CHEM101");
        
        // Expected: 0 (as specified in the requirement)
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Test average grade calculation for a course where all students have the same grade
        double result = academicProgram.calculateAverageGrade("BIO101");
        
        // Expected: 90.0 (all students have the same grade)
        assertEquals(90.0, result, 0.001);
    }

    @Test
    public void testCase5_AverageGradeCalculationWithMixedAndZeroGrades() {
        // Test average grade calculation for a course with a mix of valid and zero grades
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected: (75.0 + 0.0 + 85.5 + 0.0) / 4 = 160.5 / 4 = 40.125
        assertEquals(40.125, result, 0.001);
    }
}