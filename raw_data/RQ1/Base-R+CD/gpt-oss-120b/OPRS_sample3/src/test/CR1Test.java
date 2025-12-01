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
        // Clear any existing state before each test
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 pending reviews
        Reviewer R001 = reviewer1;
        
        // Create Papers P1, P2, P3
        Paper P1 = new Paper();
        Paper P2 = new Paper();
        Paper P3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        // Grades remain UNDECIDED (default)
        R001.addAssignment(assignment1);
        R001.addAssignment(assignment2);
        R001.addAssignment(assignment3);
        
        // Expected Output: 3
        assertEquals(3, R001.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with all reviews submitted
        Reviewer R002 = reviewer2;
        
        // Create Papers P4, P5
        Paper P4 = new Paper();
        Paper P5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        R002.addAssignment(assignment4);
        R002.addAssignment(assignment5);
        
        // Expected Output: 0
        assertEquals(0, R002.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        Reviewer R003 = reviewer3;
        
        // Create Papers P6-P10
        Paper P6 = new Paper();
        Paper P7 = new Paper();
        Paper P8 = new Paper();
        Paper P9 = new Paper();
        Paper P10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (UNDECIDED)
        ReviewAssignment assignment6 = new ReviewAssignment();
        ReviewAssignment assignment7 = new ReviewAssignment();
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment assignment8 = new ReviewAssignment();
        assignment8.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment9 = new ReviewAssignment();
        assignment9.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment10 = new ReviewAssignment();
        assignment10.setGrade(Grade.REJECT);
        
        R003.addAssignment(assignment6);
        R003.addAssignment(assignment7);
        R003.addAssignment(assignment8);
        R003.addAssignment(assignment9);
        R003.addAssignment(assignment10);
        
        // Expected Output: 2 (P6 and P7 are undecided)
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
        // Setup: Create Reviewer R005 with partially submitted reviews
        Reviewer R005 = reviewer5;
        
        // Create Papers P11-P13
        Paper P11 = new Paper();
        Paper P12 = new Paper();
        Paper P13 = new Paper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment12 = new ReviewAssignment(); // UNDECIDED
        
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT);
        
        R005.addAssignment(assignment11);
        R005.addAssignment(assignment12);
        R005.addAssignment(assignment13);
        
        // Expected Output: 1 (only P12 is undecided)
        assertEquals(1, R005.calculateUnsubmittedReviews());
    }
}