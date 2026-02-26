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
        // Initialize reviewers for test cases
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign without feedback and grade
        reviewer1 = new Reviewer();
        
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create review assignments with UNDECIDED grade (default)
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        reviewer1.addAssignment(assignment1);
        reviewer1.addAssignment(assignment2);
        reviewer1.addAssignment(assignment3);
        
        // Expected Output: 3
        assertEquals(3, reviewer1.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5, assign with grades=ACCEPT
        reviewer2 = new Reviewer();
        
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create review assignments with ACCEPT grade
        ReviewAssignment assignment4 = new ReviewAssignment(null, Grade.ACCEPT);
        ReviewAssignment assignment5 = new ReviewAssignment(null, Grade.ACCEPT);
        
        reviewer2.addAssignment(assignment4);
        reviewer2.addAssignment(assignment5);
        
        // Expected Output: 0
        assertEquals(0, reviewer2.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10
        // Assign P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        reviewer3 = new Reviewer();
        
        // Papers P6, P7 - no grade (UNDECIDED)
        ReviewAssignment assignment6 = new ReviewAssignment();
        ReviewAssignment assignment7 = new ReviewAssignment();
        
        // Papers P8, P9, P10 - with REJECT grade
        ReviewAssignment assignment8 = new ReviewAssignment(null, Grade.REJECT);
        ReviewAssignment assignment9 = new ReviewAssignment(null, Grade.REJECT);
        ReviewAssignment assignment10 = new ReviewAssignment(null, Grade.REJECT);
        
        reviewer3.addAssignment(assignment6);
        reviewer3.addAssignment(assignment7);
        reviewer3.addAssignment(assignment8);
        reviewer3.addAssignment(assignment9);
        reviewer3.addAssignment(assignment10);
        
        // Expected Output: 2
        assertEquals(2, reviewer3.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        reviewer4 = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, reviewer4.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        reviewer5 = new Reviewer();
        
        // Paper P11 - ACCEPT grade
        ReviewAssignment assignment11 = new ReviewAssignment(null, Grade.ACCEPT);
        
        // Paper P12 - no grade (UNDECIDED)
        ReviewAssignment assignment12 = new ReviewAssignment();
        
        // Paper P13 - REJECT grade
        ReviewAssignment assignment13 = new ReviewAssignment(null, Grade.REJECT);
        
        reviewer5.addAssignment(assignment11);
        reviewer5.addAssignment(assignment12);
        reviewer5.addAssignment(assignment13);
        
        // Expected Output: 1
        assertEquals(1, reviewer5.calculateUnsubmittedReviews());
    }
}