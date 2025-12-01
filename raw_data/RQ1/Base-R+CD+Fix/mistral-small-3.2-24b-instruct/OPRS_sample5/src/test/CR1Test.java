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
        // Test Case 1: Reviewer with 3 pending reviews
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign them without feedback and grade
        reviewer = new Reviewer();
        
        // Create papers
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create review assignments with UNDECIDED grade (unsubmitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.UNDECIDED);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra1);
        assignments.add(ra2);
        assignments.add(ra3);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: All reviews submitted
        // Setup: Create Reviewer R002, Papers P4, P5, assign with grades=ACCEPT
        reviewer = new Reviewer();
        
        // Create papers
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Create review assignments with ACCEPT grade (submitted)
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra4);
        assignments.add(ra5);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: Mixed submission status
        // Setup: Create Reviewer R003, Papers P6-P10
        // Assign P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        reviewer = new Reviewer();
        
        // Create review assignments
        ReviewAssignment ra6 = new ReviewAssignment();
        ra6.setGrade(Grade.UNDECIDED); // Unsubmitted
        
        ReviewAssignment ra7 = new ReviewAssignment();
        ra7.setGrade(Grade.UNDECIDED); // Unsubmitted
        
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT); // Submitted
        
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT); // Submitted
        
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT); // Submitted
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra6);
        assignments.add(ra7);
        assignments.add(ra8);
        assignments.add(ra9);
        assignments.add(ra10);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return correct count", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: No assigned papers
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        
        // Reviewer has no assignments by default
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assignments should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: Partially submitted reviews
        // Setup: Create Reviewer R005, Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        reviewer = new Reviewer();
        
        // Create review assignments
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT); // Submitted
        
        ReviewAssignment ra12 = new ReviewAssignment();
        ra12.setGrade(Grade.UNDECIDED); // Unsubmitted
        
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT); // Submitted
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(ra11);
        assignments.add(ra12);
        assignments.add(ra13);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return correct count", 1, result);
    }
}