import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P1, P2, P3
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.UNDECIDED); // No grade submitted
        
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED); // No grade submitted
        
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.UNDECIDED); // No grade submitted
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT); // Grade submitted
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT); // Grade submitted
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment4);
        assignments.add(assignment5);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P6-P10
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003
        ReviewAssignment assignment6 = new ReviewAssignment();
        assignment6.setGrade(Grade.UNDECIDED); // No grade submitted
        
        ReviewAssignment assignment7 = new ReviewAssignment();
        assignment7.setGrade(Grade.UNDECIDED); // No grade submitted
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment assignment8 = new ReviewAssignment();
        assignment8.setGrade(Grade.REJECT); // Grade submitted
        
        ReviewAssignment assignment9 = new ReviewAssignment();
        assignment9.setGrade(Grade.REJECT); // Grade submitted
        
        ReviewAssignment assignment10 = new ReviewAssignment();
        assignment10.setGrade(Grade.REJECT); // Grade submitted
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment6);
        assignments.add(assignment7);
        assignments.add(assignment8);
        assignments.add(assignment9);
        assignments.add(assignment10);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P11-P13
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT); // Grade submitted
        
        ReviewAssignment assignment12 = new ReviewAssignment();
        assignment12.setGrade(Grade.UNDECIDED); // No grade submitted
        
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT); // Grade submitted
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment11);
        assignments.add(assignment12);
        assignments.add(assignment13);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}