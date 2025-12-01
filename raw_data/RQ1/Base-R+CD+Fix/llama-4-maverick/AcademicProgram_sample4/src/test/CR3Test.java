import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;
    private List<Student> students;
    
    @Before
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        
        // Create courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        course3 = new Course();
        course3.setCode("PHY301");
        course3.setName("Physics Fundamentals");
        
        // Create students
        students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student());
        }
        
        // Set up enrollments with grades
        List<Enrollment> enrollments1 = new ArrayList<>();
        List<Enrollment> enrollments2 = new ArrayList<>();
        List<Enrollment> enrollments3 = new ArrayList<>();
        
        // Course1: Multiple students with different grades
        for (int i = 0; i < 5; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students.get(i));
            enrollment.setCourse(course1);
            enrollment.setGrade(70 + i * 5); // Grades: 70, 75, 80, 85, 90
            enrollments1.add(enrollment);
        }
        course1.setEnrollments(enrollments1);
        
        // Course2: Single student
        Enrollment singleEnrollment = new Enrollment();
        singleEnrollment.setStudent(students.get(5));
        singleEnrollment.setCourse(course2);
        singleEnrollment.setGrade(88.5);
        enrollments2.add(singleEnrollment);
        course2.setEnrollments(enrollments2);
        
        // Course3: No students (empty enrollments)
        course3.setEnrollments(enrollments3);
        
        // Add courses to academic program
        List<Course> programCourses = new ArrayList<>();
        programCourses.add(course1);
        programCourses.add(course2);
        programCourses.add(course3);
        academicProgram.setCourses(programCourses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Calculate expected average: (70 + 75 + 80 + 85 + 90) / 5 = 80.0
        double expectedAverage = 80.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");
        
        assertEquals("Average grade for single student course should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("PHY301");
        
        assertEquals("Average grade for course with no students should be 0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Create a new course with all same grades
        Course sameGradeCourse = new Course();
        sameGradeCourse.setCode("CHEM401");
        sameGradeCourse.setName("Chemistry Basics");
        
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students.get(i));
            enrollment.setCourse(sameGradeCourse);
            enrollment.setGrade(85.0); // All students have grade 85.0
            enrollments.add(enrollment);
        }
        sameGradeCourse.setEnrollments(enrollments);
        
        // Add to academic program temporarily
        academicProgram.getCourses().add(sameGradeCourse);
        
        double expectedAverage = 85.0;
        double actualAverage = academicProgram.calculateAverageGrade("CHEM401");
        
        assertEquals("Average grade for course with all same grades should equal the common grade value", 
                     expectedAverage, actualAverage, 0.001);
        
        // Remove the temporary course
        academicProgram.getCourses().remove(sameGradeCourse);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Create a new course with mix of valid and zero grades
        Course mixedGradeCourse = new Course();
        mixedGradeCourse.setCode("BIO501");
        mixedGradeCourse.setName("Biology Fundamentals");
        
        List<Enrollment> enrollments = new ArrayList<>();
        // Students with valid grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(students.get(0));
        enrollment1.setCourse(mixedGradeCourse);
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(students.get(1));
        enrollment2.setCourse(mixedGradeCourse);
        enrollment2.setGrade(80.0);
        enrollments.add(enrollment2);
        
        // Students with zero grades
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(students.get(2));
        enrollment3.setCourse(mixedGradeCourse);
        enrollment3.setGrade(0.0);
        enrollments.add(enrollment3);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setStudent(students.get(3));
        enrollment4.setCourse(mixedGradeCourse);
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);
        
        mixedGradeCourse.setEnrollments(enrollments);
        
        // Add to academic program temporarily
        academicProgram.getCourses().add(mixedGradeCourse);
        
        // Calculate expected average: (90 + 80 + 0 + 0) / 4 = 42.5
        double expectedAverage = 42.5;
        double actualAverage = academicProgram.calculateAverageGrade("BIO501");
        
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
        
        // Remove the temporary course
        academicProgram.getCourses().remove(mixedGradeCourse);
    }
}