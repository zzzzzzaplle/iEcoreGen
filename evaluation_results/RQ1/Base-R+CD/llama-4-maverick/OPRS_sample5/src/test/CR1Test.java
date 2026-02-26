import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P1, P2, P3
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment assignment1 = new ReviewAssignment(); // grade defaults to UNDECIDED
        ReviewAssignment assignment2 = new ReviewAssignment(); // grade defaults to UNDECIDED
        ReviewAssignment assignment3 = new ReviewAssignment(); // grade defaults to UNDECIDED
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        reviewer.setReviewAssignments(assignments);
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.ACCEPT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        reviewer.setReviewAssignments(assignments);
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
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
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (UNDECIDED)
        ReviewAssignment assignment1 = new ReviewAssignment(); // grade defaults to UNDECIDED
        ReviewAssignment assignment2 = new ReviewAssignment(); // grade defaults to UNDECIDED
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        assignments.add(assignment4);
        assignments.add(assignment5);
        reviewer.setReviewAssignments(assignments);
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
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
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        ReviewAssignment assignment2 = new ReviewAssignment(); // grade defaults to UNDECIDED
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        reviewer.setReviewAssignments(assignments);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}