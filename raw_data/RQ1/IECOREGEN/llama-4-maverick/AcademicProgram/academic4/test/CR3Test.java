package edu.academic.academic4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.academic.AcademicFactory;
import edu.academic.AcademicProgram;
import edu.academic.Course;
import edu.academic.Student;
import edu.academic.Enrollment;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private AcademicFactory factory;
    private AcademicProgram academicProgram;
    
    @Before
    public void setUp() {
        // Initialize factory using Ecore factory pattern
        factory = AcademicFactory.eINSTANCE;
        
        // Create academic program instance
        academicProgram = new AcademicProgram() {
            // Anonymous implementation for abstract class
        };
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForStudentsInSpecificCourse() {
        // Create a course with multiple students having different grades
        Course course = factory.createCourse();
        course.setCode("SE101");
        
        // Create students and enrollments with different grades
        Student student1 = factory.createStudent();
        Student student2 = factory.createStudent();
        Student student3 = factory.createStudent();
        
        Enrollment enrollment1 = factory.createEnrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(3.5);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = factory.createEnrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(2.8);
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = factory.createEnrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(4.0);
        enrollment3.setCourse(course);
        
        // Add enrollments to course
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate expected average: (3.5 + 2.8 + 4.0) / 3 = 3.4333...
        double expectedAverage = (3.5 + 2.8 + 4.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("SE101");
        
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Create a course with only one student
        Course course = factory.createCourse();
        course.setCode("CS201");
        
        // Create single student with specific grade
        Student student = factory.createStudent();
        
        Enrollment enrollment = factory.createEnrollment();
        enrollment.setStudent(student);
        enrollment.setGrade(3.7);
        enrollment.setCourse(course);
        
        // Add enrollment to course
        course.getEnrollments().add(enrollment);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate average - should be the single student's grade
        double expectedAverage = 3.7;
        double actualAverage = academicProgram.calculateAverageGrade("CS201");
        
        assertEquals("Average grade for single student should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Create a course with no students enrolled
        Course course = factory.createCourse();
        course.setCode("MATH101");
        
        // Add course to academic program (no enrollments)
        academicProgram.getCourses().add(course);
        
        // Calculate average for course with no students
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade for course with no students should be 0.0", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Create a course where all students have the same grade
        Course course = factory.createCourse();
        course.setCode("PHY301");
        
        // Create multiple students with identical grades
        Student student1 = factory.createStudent();
        Student student2 = factory.createStudent();
        Student student3 = factory.createStudent();
        
        Enrollment enrollment1 = factory.createEnrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(4.0);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = factory.createEnrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(4.0);
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = factory.createEnrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(4.0);
        enrollment3.setCourse(course);
        
        // Add enrollments to course
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate average - should be the common grade value
        double expectedAverage = 4.0;
        double actualAverage = academicProgram.calculateAverageGrade("PHY301");
        
        assertEquals("Average grade with all same grades should equal the common grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Create a course with mix of valid and zero grades
        Course course = factory.createCourse();
        course.setCode("CHEM202");
        
        // Create students with mix of valid grades and zero grades
        Student student1 = factory.createStudent();
        Student student2 = factory.createStudent();
        Student student3 = factory.createStudent();
        Student student4 = factory.createStudent();
        
        Enrollment enrollment1 = factory.createEnrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(3.2);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = factory.createEnrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0); // Zero grade
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = factory.createEnrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(2.8);
        enrollment3.setCourse(course);
        
        Enrollment enrollment4 = factory.createEnrollment();
        enrollment4.setStudent(student4);
        enrollment4.setGrade(0.0); // Zero grade
        enrollment4.setCourse(course);
        
        // Add enrollments to course
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        course.getEnrollments().add(enrollment4);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate expected average: (3.2 + 0.0 + 2.8 + 0.0) / 4 = 1.5
        double expectedAverage = (3.2 + 0.0 + 2.8 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("CHEM202");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}