import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign them without feedback and grade
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create 3 review assignments with UNDECIDED grade (unsubmitted)
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5, assign them with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        
        // Create 2 papers
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create 2 review assignments with ACCEPT grade (submitted)
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment4);
        assignments.add(assignment5);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10, assign P6/P7 without grade, P8-P10 with REJECT
        Reviewer reviewer = new Reviewer();
        
        // Create 5 papers
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Create 5 review assignments: 2 UNDECIDED, 3 REJECT
        ReviewAssignment assignment6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment assignment7 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment assignment8 = new ReviewAssignment();
        assignment8.setGrade(Grade.REJECT);
        ReviewAssignment assignment9 = new ReviewAssignment();
        assignment9.setGrade(Grade.REJECT);
        ReviewAssignment assignment10 = new ReviewAssignment();
        assignment10.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment6);
        assignments.add(assignment7);
        assignments.add(assignment8);
        assignments.add(assignment9);
        assignments.add(assignment10);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 with no assignments
        Reviewer reviewer = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13, assign P11 (ACCEPT), P12 (no grade), P13 (REJECT)
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Create 3 review assignments: ACCEPT, UNDECIDED, REJECT
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT);
        ReviewAssignment assignment12 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment11);
        assignments.add(assignment12);
        assignments.add(assignment13);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}