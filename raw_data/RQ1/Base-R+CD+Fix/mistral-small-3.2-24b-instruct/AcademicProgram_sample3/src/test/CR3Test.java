import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;
    private Classroom classroom1;
    private Classroom classroom2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    private Enrollment enrollment4;

    @Before
    public void setUp() {
        // Create academic program
        academicProgram = new AcademicProgram();
        
        // Create classrooms
        classroom1 = new Classroom();
        classroom1.setId("C101");
        classroom1.setCapacity(50);
        
        classroom2 = new Classroom();
        classroom2.setId("C102");
        classroom2.setCapacity(30);
        
        // Create courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        course1.setClassroom(classroom1);
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        course2.setClassroom(classroom2);
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        
        course3 = new Course();
        course3.setCode("PHY301");
        course3.setName("Physics");
        course3.setClassroom(classroom1);
        course3.setCourseDays(Arrays.asList("Monday", "Friday"));
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Create enrollments
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.5);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.0);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.5);
        
        enrollment4 = new Enrollment();
        enrollment4.setStudent(student1);
        enrollment4.setGrade(88.0);
    }

    @Test
    public void testCase1_AverageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Setup course with multiple enrollments and different grades
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1); // grade: 85.5
        enrollments.add(enrollment2); // grade: 92.0
        enrollments.add(enrollment3); // grade: 78.5
        
        course1.setEnrollments(enrollments);
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result with delta for floating point comparison
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Setup course with single enrollment
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1); // grade: 85.5
        
        course1.setEnrollments(enrollments);
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result - should be exactly the single student's grade
        assertEquals("Average grade for single student should equal that student's grade", 
                     85.5, actualAverage, 0.001);
    }

    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // Setup course with empty enrollments list
        course1.setEnrollments(new ArrayList<>());
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result - should be 0.0 as specified in the method implementation
        assertEquals("Average grade for course with no students should be 0.0", 
                     0.0, actualAverage, 0.001);
    }

    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Setup course with multiple enrollments all having the same grade
        Enrollment enrollmentSame1 = new Enrollment();
        enrollmentSame1.setStudent(student1);
        enrollmentSame1.setGrade(90.0);
        
        Enrollment enrollmentSame2 = new Enrollment();
        enrollmentSame2.setStudent(student2);
        enrollmentSame2.setGrade(90.0);
        
        Enrollment enrollmentSame3 = new Enrollment();
        enrollmentSame3.setStudent(student3);
        enrollmentSame3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollmentSame1);
        enrollments.add(enrollmentSame2);
        enrollments.add(enrollmentSame3);
        
        course1.setEnrollments(enrollments);
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result - should be exactly the common grade value
        assertEquals("Average grade for students with all same grades should be that common grade", 
                     90.0, actualAverage, 0.001);
    }

    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Setup course with mix of valid and zero grades
        Enrollment enrollmentZero1 = new Enrollment();
        enrollmentZero1.setStudent(student1);
        enrollmentZero1.setGrade(0.0);
        
        Enrollment enrollmentValid1 = new Enrollment();
        enrollmentValid1.setStudent(student2);
        enrollmentValid1.setGrade(80.0);
        
        Enrollment enrollmentZero2 = new Enrollment();
        enrollmentZero2.setStudent(student3);
        enrollmentZero2.setGrade(0.0);
        
        Enrollment enrollmentValid2 = new Enrollment();
        enrollmentValid2.setStudent(new Student());
        enrollmentValid2.setGrade(70.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollmentZero1);  // grade: 0.0
        enrollments.add(enrollmentValid1); // grade: 80.0
        enrollments.add(enrollmentZero2);  // grade: 0.0
        enrollments.add(enrollmentValid2); // grade: 70.0
        
        course1.setEnrollments(enrollments);
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
        
        // Calculate expected average: (0.0 + 80.0 + 0.0 + 70.0) / 4 = 37.5
        double expectedAverage = (0.0 + 80.0 + 0.0 + 70.0) / 4;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }


}