import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<ReviewAssignment> assignments;
    
    @Before
    public void setUp() {
        // Reset assignments list before each test
        assignments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001
        reviewer = new Reviewer();
        
        // Create Papers P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment ra1 = new ReviewAssignment(null, Grade.UNDECIDED);
        ReviewAssignment ra2 = new ReviewAssignment(null, Grade.UNDECIDED);
        ReviewAssignment ra3 = new ReviewAssignment(null, Grade.UNDECIDED);
        
        assignments.add(ra1);
        assignments.add(ra2);
        assignments.add(ra3);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment ra4 = new ReviewAssignment("Good paper", Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment("Excellent work", Grade.ACCEPT);
        
        assignments.add(ra4);
        assignments.add(ra5);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        reviewer = new Reviewer();
        
        // Create Papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003
        ReviewAssignment ra6 = new ReviewAssignment(null, Grade.UNDECIDED);
        ReviewAssignment ra7 = new ReviewAssignment(null, Grade.UNDECIDED);
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment ra8 = new ReviewAssignment("Needs improvement", Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment("Major flaws", Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment("Not suitable", Grade.REJECT);
        
        assignments.add(ra6);
        assignments.add(ra7);
        assignments.add(ra8);
        assignments.add(ra9);
        assignments.add(ra10);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        // No assignments set, so reviewer has empty assignment list
        
        // Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        reviewer = new Reviewer();
        
        // Create Papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        ReviewAssignment ra11 = new ReviewAssignment("Well written", Grade.ACCEPT);
        ReviewAssignment ra12 = new ReviewAssignment(null, Grade.UNDECIDED);
        ReviewAssignment ra13 = new ReviewAssignment("Weak methodology", Grade.REJECT);
        
        assignments.add(ra11);
        assignments.add(ra12);
        assignments.add(ra13);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}