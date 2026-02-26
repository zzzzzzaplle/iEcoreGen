import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        academicProgram.setProgramName("Software Engineering");
        
        course1 = new Course();
        course1.setCourseCode("SE101");
        course1.setCourseName("Introduction to Software Engineering");
        
        course2 = new Course();
        course2.setCourseCode("SE102");
        course2.setCourseName("Advanced Software Engineering");
        
        student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("Alice");
        student1.setSurname("Smith");
        
        student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Bob");
        student2.setSurname("Johnson");
        
        student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Charlie");
        student3.setSurname("Brown");
        
        student4 = new Student();
        student4.setStudentId("S004");
        student4.setName("Diana");
        student4.setSurname("Wilson");
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForMultipleStudentsWithDifferentGrades() {
        // Setup: Enroll students with different grades in course1
        student1.setGradeForCourse("SE101", 85.0);
        student2.setGradeForCourse("SE101", 92.0);
        student3.setGradeForCourse("SE101", 78.0);
        
        course1.getEnrolledStudents().add(student1);
        course1.getEnrolledStudents().add(student2);
        course1.getEnrolledStudents().add(student3);
        
        // Execute: Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGradeInCourse("SE101");
        
        // Verify: Average should be (85 + 92 + 78) / 3 = 85.0
        assertEquals(85.0, averageGrade, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Enroll one student with a specific grade in course1
        student1.setGradeForCourse("SE101", 88.5);
        course1.getEnrolledStudents().add(student1);
        
        // Execute: Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGradeInCourse("SE101");
        
        // Verify: Average should be the single student's grade (88.5)
        assertEquals(88.5, averageGrade, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course1 has no enrolled students
        // No students added to course1
        
        // Execute: Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGradeInCourse("SE101");
        
        // Verify: Should return 0.0 as specified in the method documentation
        assertEquals(0.0, averageGrade, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Enroll multiple students with the same grade in course1
        student1.setGradeForCourse("SE101", 75.0);
        student2.setGradeForCourse("SE101", 75.0);
        student3.setGradeForCourse("SE101", 75.0);
        
        course1.getEnrolledStudents().add(student1);
        course1.getEnrolledStudents().add(student2);
        course1.getEnrolledStudents().add(student3);
        
        // Execute: Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGradeInCourse("SE101");
        
        // Verify: Average should be the common grade value (75.0)
        assertEquals(75.0, averageGrade, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Enroll students with mix of valid grades and 0 in course1
        student1.setGradeForCourse("SE101", 90.0);
        student2.setGradeForCourse("SE101", 0.0);
        student3.setGradeForCourse("SE101", 80.0);
        student4.setGradeForCourse("SE101", 0.0);
        
        course1.getEnrolledStudents().add(student1);
        course1.getEnrolledStudents().add(student2);
        course1.getEnrolledStudents().add(student3);
        course1.getEnrolledStudents().add(student4);
        
        // Execute: Calculate average grade for course1
        double averageGrade = academicProgram.calculateAverageGradeInCourse("SE101");
        
        // Verify: Average should be (90 + 0 + 80 + 0) / 4 = 42.5
        assertEquals(42.5, averageGrade, 0.001);
    }
}