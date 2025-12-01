import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer1;
    private Reviewer reviewer2;
    private Reviewer reviewer3;
    private Reviewer reviewer4;
    private Reviewer reviewer5;
    
    @Before
    public void setUp() {
        // Initialize reviewers for each test
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer R001 = reviewer1;
        
        // Create 3 papers
        Paper P1 = new Paper();
        Paper P2 = new Paper();
        Paper P3 = new Paper();
        
        // Create 3 review assignments without feedback and grade (default UNDECIDED)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        R001.getAssignments().add(ra1);
        R001.getAssignments().add(ra2);
        R001.getAssignments().add(ra3);
        
        // Expected Output: 3
        assertEquals(3, R001.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer R002 = reviewer2;
        
        // Create 2 papers
        Paper P4 = new Paper();
        Paper P5 = new Paper();
        
        // Create 2 review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        R002.getAssignments().add(ra4);
        R002.getAssignments().add(ra5);
        
        // Expected Output: 0
        assertEquals(0, R002.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        Reviewer R003 = reviewer3;
        
        // Create 5 papers (P6-P10)
        Paper P6 = new Paper();
        Paper P7 = new Paper();
        Paper P8 = new Paper();
        Paper P9 = new Paper();
        Paper P10 = new Paper();
        
        // Assign P6/P7 without feedback and grade (UNDECIDED)
        ReviewAssignment ra6 = new ReviewAssignment();
        ReviewAssignment ra7 = new ReviewAssignment();
        
        // Assign P8-P10 with REJECT grade
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        R003.getAssignments().add(ra6);
        R003.getAssignments().add(ra7);
        R003.getAssignments().add(ra8);
        R003.getAssignments().add(ra9);
        R003.getAssignments().add(ra10);
        
        // Expected Output: 2 (P6 and P7 are UNDECIDED)
        assertEquals(2, R003.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer R004 = reviewer4;
        
        // Expected Output: 0
        assertEquals(0, R004.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        Reviewer R005 = reviewer5;
        
        // Create 3 papers (P11-P13)
        Paper P11 = new Paper();
        Paper P12 = new Paper();
        Paper P13 = new Paper();
        
        // Assign P11 with ACCEPT grade
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        
        // Assign P12 without grade and feedback (UNDECIDED)
        ReviewAssignment ra12 = new ReviewAssignment();
        
        // Assign P13 with REJECT grade
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        R005.getAssignments().add(ra11);
        R005.getAssignments().add(ra12);
        R005.getAssignments().add(ra13);
        
        // Expected Output: 1 (only P12 is UNDECIDED)
        assertEquals(1, R005.calculateUnsubmittedReviews());
    }
}