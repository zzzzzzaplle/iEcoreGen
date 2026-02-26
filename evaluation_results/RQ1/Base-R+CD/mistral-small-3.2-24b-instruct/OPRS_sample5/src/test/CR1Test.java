import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign without feedback and grade
        Reviewer reviewer = new Reviewer();
        
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.UNDECIDED);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        
        reviewer.setReviewAssignments(assignments);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign with grades=ACCEPT
        Reviewer reviewer = new Reviewer();
        
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment4);
        assignments.add(assignment5);
        
        reviewer.setReviewAssignments(assignments);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Create Papers P6-P10
        // Assign P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        Reviewer reviewer = new Reviewer();
        
        ReviewAssignment assignment6 = new ReviewAssignment();
        assignment6.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment assignment7 = new ReviewAssignment();
        assignment7.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment assignment8 = new ReviewAssignment();
        assignment8.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment9 = new ReviewAssignment();
        assignment9.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment10 = new ReviewAssignment();
        assignment10.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment6);
        assignments.add(assignment7);
        assignments.add(assignment8);
        assignments.add(assignment9);
        assignments.add(assignment10);
        
        reviewer.setReviewAssignments(assignments);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Create Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Reviewer reviewer = new Reviewer();
        
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment12 = new ReviewAssignment();
        assignment12.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment11);
        assignments.add(assignment12);
        assignments.add(assignment13);
        
        reviewer.setReviewAssignments(assignments);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 1
        assertEquals(1, result);
    }
}