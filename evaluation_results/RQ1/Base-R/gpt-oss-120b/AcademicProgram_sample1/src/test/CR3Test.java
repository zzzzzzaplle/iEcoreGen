import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.*;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        academicProgram.setProgramName("Software Engineering");
        
        course = new Course();
        course.setCourseName("Advanced Java Programming");
        course.setCourseCode("CS401");
        course.setQuotaLimit(10);
        
        academicProgram.addCourse(course);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Create students with different grades
        Student student1 = new Student();
        student1.setName("John");
        student1.setSurname("Doe");
        student1.setStudentId("S001");
        
        Student student2 = new Student();
        student2.setName("Jane");
        student2.setSurname("Smith");
        student2.setStudentId("S002");
        
        Student student3 = new Student();
        student3.setName("Bob");
        student3.setSurname("Johnson");
        student3.setStudentId("S003");
        
        // Enroll students in the course
        student1.enrolInCourse(course);
        student2.enrolInCourse(course);
        student3.enrolInCourse(course);
        
        // Set different grades for each student
        student1.setGrade(course, 85.0);
        student2.setGrade(course, 92.0);
        student3.setGrade(course, 78.0);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Create a single student
        Student student = new Student();
        student.setName("Alice");
        student.setSurname("Brown");
        student.setStudentId("S004");
        
        // Enroll the student in the course
        student.enrolInCourse(course);
        
        // Set a specific grade for the student
        double expectedGrade = 95.0;
        student.setGrade(course, expectedGrade);
        
        // The average should equal the single student's grade
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedGrade, actualAverage, 0.01);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // No students enrolled in the course
        
        // The average should be 0 when there are no students
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should be 0 for a course with no students", 
                     0.0, actualAverage, 0.01);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Create students with the same grade
        Student student1 = new Student();
        student1.setName("Charlie");
        student1.setSurname("Davis");
        student1.setStudentId("S005");
        
        Student student2 = new Student();
        student2.setName("Diana");
        student2.setSurname("Wilson");
        student2.setStudentId("S006");
        
        Student student3 = new Student();
        student3.setName("Edward");
        student3.setSurname("Miller");
        student3.setStudentId("S007");
        
        // Enroll students in the course
        student1.enrolInCourse(course);
        student2.enrolInCourse(course);
        student3.enrolInCourse(course);
        
        // Set the same grade for all students
        double commonGrade = 88.0;
        student1.setGrade(course, commonGrade);
        student2.setGrade(course, commonGrade);
        student3.setGrade(course, commonGrade);
        
        // The average should equal the common grade value
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     commonGrade, actualAverage, 0.01);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Create students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setName("Frank");
        student1.setSurname("Taylor");
        student1.setStudentId("S008");
        
        Student student2 = new Student();
        student2.setName("Grace");
        student2.setSurname("Anderson");
        student2.setStudentId("S009");
        
        Student student3 = new Student();
        student3.setName("Henry");
        student3.setSurname("Thomas");
        student3.setStudentId("S010");
        
        // Enroll students in the course
        student1.enrolInCourse(course);
        student2.enrolInCourse(course);
        student3.enrolInCourse(course);
        
        // Set mix of valid and zero grades
        student1.setGrade(course, 75.0);  // Valid grade
        student2.setGrade(course, 0.0);   // Zero grade
        student3.setGrade(course, 90.0);  // Valid grade
        
        // Calculate expected average: (75 + 0 + 90) / 3 = 55.0
        double expectedAverage = (75.0 + 0.0 + 90.0) / 3;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.01);
    }
}