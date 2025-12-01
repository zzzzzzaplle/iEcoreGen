import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign P1/P2/P3 to R001 without feedback and grade from R001
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create 3 review assignments with UNDECIDED grade (unsubmitted)
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        assignment1.setGrade(Grade.UNDECIDED);
        assignment2.setGrade(Grade.UNDECIDED);
        assignment3.setGrade(Grade.UNDECIDED);
        
        // Add assignments to reviewer
        reviewer.addReviewAssignment(assignment1);
        reviewer.addReviewAssignment(assignment2);
        reviewer.addReviewAssignment(assignment3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign P4/P5 to R002 with grades=ACCEPT
        Reviewer reviewer = new Reviewer();
        
        // Create 2 papers
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create 2 review assignments with ACCEPT grade (submitted)
        ReviewAssignment assignment4 = new ReviewAssignment();
        ReviewAssignment assignment5 = new ReviewAssignment();
        
        assignment4.setGrade(Grade.ACCEPT);
        assignment5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.addReviewAssignment(assignment4);
        reviewer.addReviewAssignment(assignment5);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Create Papers P6-P10, Assign P6/P7 to R003 without feedback and grade from R003, Assign P8-P10 to R003 with grade=REJECT
        Reviewer reviewer = new Reviewer();
        
        // Create 5 papers (P6-P10)
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Create 5 review assignments
        ReviewAssignment assignment6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment assignment7 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment assignment8 = new ReviewAssignment(); // REJECT
        ReviewAssignment assignment9 = new ReviewAssignment(); // REJECT
        ReviewAssignment assignment10 = new ReviewAssignment(); // REJECT
        
        assignment6.setGrade(Grade.UNDECIDED);
        assignment7.setGrade(Grade.UNDECIDED);
        assignment8.setGrade(Grade.REJECT);
        assignment9.setGrade(Grade.REJECT);
        assignment10.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        reviewer.addReviewAssignment(assignment6);
        reviewer.addReviewAssignment(assignment7);
        reviewer.addReviewAssignment(assignment8);
        reviewer.addReviewAssignment(assignment9);
        reviewer.addReviewAssignment(assignment10);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Calculate unsubmitted reviews for reviewer with no assignments
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Create Papers P11-P13, Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers (P11-P13)
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Create 3 review assignments
        ReviewAssignment assignment11 = new ReviewAssignment(); // ACCEPT
        ReviewAssignment assignment12 = new ReviewAssignment(); // UNDECIDED (no grade)
        ReviewAssignment assignment13 = new ReviewAssignment(); // REJECT
        
        assignment11.setGrade(Grade.ACCEPT);
        assignment12.setGrade(Grade.UNDECIDED); // Default is UNDECIDED, but setting explicitly for clarity
        assignment13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        reviewer.addReviewAssignment(assignment11);
        reviewer.addReviewAssignment(assignment12);
        reviewer.addReviewAssignment(assignment13);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}