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
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() {
        // Initialize academic program and basic objects for tests
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        student1 = new Student();
        student1.setName("Alice");
        student1.setSurname("Smith");
        
        student2 = new Student();
        student2.setName("Bob");
        student2.setSurname("Johnson");
        
        student3 = new Student();
        student3.setName("Charlie");
        student3.setSurname("Brown");
        
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Setup: AcademicProgram with a specific Course having a list of enrolled Students with different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all the students in that specific Course
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        enrollment1.setGrade(88.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student, as it represents the average grade
        assertEquals("Average grade should equal the single student's grade", 
                     88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: AcademicProgram with a specific Course and no students are enrolled in it
        List<Enrollment> enrollments = new ArrayList<>();
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: An appropriate indication (0.0) that there are no grades to calculate an average from
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: AcademicProgram with a specific Course where all enrolled students have the same grade
        enrollment1.setGrade(75.0);
        enrollment2.setGrade(75.0);
        enrollment3.setGrade(75.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value as the average grade
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(85.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        double expectedAverage = (90.0 + 0.0 + 85.0) / 3;
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, result, 0.001);
    }
    

}