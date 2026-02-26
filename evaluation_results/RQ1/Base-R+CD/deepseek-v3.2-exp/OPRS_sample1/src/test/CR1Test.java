import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer r001 = new Reviewer();
        
        // Create 3 papers
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create review assignments without grades (UNDECIDED by default)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra1);
        assignments.add(ra2);
        assignments.add(ra3);
        r001.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer r002 = new Reviewer();
        
        // Create 2 papers
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra4);
        assignments.add(ra5);
        r002.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        Reviewer r003 = new Reviewer();
        
        // Create 5 papers (P6-P10)
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Create review assignments: P6, P7 without grades (UNDECIDED), P8-P10 with REJECT grades
        ReviewAssignment ra6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra7 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra6);
        assignments.add(ra7);
        assignments.add(ra8);
        assignments.add(ra9);
        assignments.add(ra10);
        r003.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        
        // Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with partially submitted reviews
        Reviewer r005 = new Reviewer();
        
        // Create 3 papers (P11-P13)
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Create review assignments: P11 (ACCEPT), P12 (UNDECIDED), P13 (REJECT)
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra11);
        assignments.add(ra12);
        assignments.add(ra13);
        r005.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}