import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Setup runs before each test method
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a classroom (it's required for Course constructor but not used in this test)
        Classroom classroom = new Classroom("A101", 50, "1", "A");
        
        // Create a course with initial quota of 20
        Course course = new Course("Math", "MATH101", new Date(), new Date(), 
                                  new ArrayList<String>(), 20, 3, classroom);
        
        // Add the course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Increase the quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate and verify the remaining quota
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, result);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a classroom
        Classroom classroom = new Classroom("B202", 60, "2", "B");
        
        // Create a course with initial quota of 30
        Course course = new Course("Physics", "PHYS201", new Date(), new Date(), 
                                  new ArrayList<String>(), 30, 4, classroom);
        
        // Add the course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Decrease the quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate and verify the remaining quota
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create classrooms
        Classroom classroom1 = new Classroom("C303", 40, "3", "C");
        Classroom classroom2 = new Classroom("D404", 50, "4", "D");
        Classroom classroom3 = new Classroom("E505", 60, "5", "E");
        
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course("Chemistry", "CHEM301", new Date(), new Date(), 
                                   new ArrayList<String>(), 15, 2, classroom1);
        Course course2 = new Course("Biology", "BIO401", new Date(), new Date(), 
                                   new ArrayList<String>(), 25, 3, classroom2);
        Course course3 = new Course("History", "HIST501", new Date(), new Date(), 
                                   new ArrayList<String>(), 35, 4, classroom3);
        
        // Add all courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // No quota changes made
        
        // Calculate and verify the remaining quota (15 + 25 + 35 = 75)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 75 for three courses without changes", 75, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create classrooms
        Classroom classroom1 = new Classroom("F606", 45, "6", "F");
        Classroom classroom2 = new Classroom("G707", 55, "7", "G");
        
        // Create two courses with quotas 20 and 25
        Course course1 = new Course("English", "ENG601", new Date(), new Date(), 
                                   new ArrayList<String>(), 20, 2, classroom1);
        Course course2 = new Course("Art", "ART701", new Date(), new Date(), 
                                   new ArrayList<String>(), 25, 3, classroom2);
        
        // Add both courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        instructor.setCourses(courses);
        
        // Increase the quota of the first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate and verify the remaining quota (30 + 25 = 55)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 55 after increasing first course by 10", 55, result);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create classrooms
        Classroom classroom1 = new Classroom("H808", 35, "8", "H");
        Classroom classroom2 = new Classroom("I909", 45, "9", "I");
        Classroom classroom3 = new Classroom("J1010", 55, "10", "J");
        
        // Create three courses with quotas 18, 22, and 28
        Course course1 = new Course("Geography", "GEO801", new Date(), new Date(), 
                                   new ArrayList<String>(), 18, 2, classroom1);
        Course course2 = new Course("Music", "MUS901", new Date(), new Date(), 
                                   new ArrayList<String>(), 22, 3, classroom2);
        Course course3 = new Course("Drama", "DRA1001", new Date(), new Date(), 
                                   new ArrayList<String>(), 28, 4, classroom3);
        
        // Add all courses to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Increase the quota of the second course by 6 and decrease the third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Calculate and verify the remaining quota (18 + 28 + 24 = 70)
        int result = instructor.calculateRemainingQuota();
        assertEquals("Remaining quota should be 70 after changes: 18 + 28 + 24 = 70", 70, result);
    }
}