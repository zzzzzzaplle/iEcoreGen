import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001, Papers P1, P2, P3, and assign without feedback/grade
        Reviewer reviewer = new Reviewer();
        
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        reviewer.getAssignments().add(assignment3);
        
        // Test: Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Papers P4, P5 with grades=ACCEPT
        Reviewer reviewer = new Reviewer();
        
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("Good paper");
        
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("Excellent work");
        
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        
        // Test: Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Papers P6-P10
        // Assign P6/P7 without feedback/grade, P8-P10 with grade=REJECT
        Reviewer reviewer = new Reviewer();
        
        // P6 and P7 - no grade and no feedback
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        
        // P8, P9, P10 - grade=REJECT with feedback
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("Needs improvement");
        
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("Not suitable");
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.REJECT);
        assignment5.setFeedback("Poor methodology");
        
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        reviewer.getAssignments().add(assignment3);
        reviewer.getAssignments().add(assignment4);
        reviewer.getAssignments().add(assignment5);
        
        // Test: Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer reviewer = new Reviewer();
        
        // Test: Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Papers P11-P13
        // P11 (grade=ACCEPT), P12 (no grade/feedback), P13 (grade=REJECT)
        Reviewer reviewer = new Reviewer();
        
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("Well written");
        
        ReviewAssignment assignment2 = new ReviewAssignment();
        // No grade and no feedback - unsubmitted
        
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("Insufficient evidence");
        
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        reviewer.getAssignments().add(assignment3);
        
        // Test: Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 1
        assertEquals(1, result);
    }
}