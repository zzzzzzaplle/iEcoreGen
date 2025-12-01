import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer reviewer = new Reviewer();
        
        // Create papers P1, P2, P3
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create review assignments without setting grade (default is UNDECIDED)
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 3 unsubmitted reviews
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        
        // Create papers P4, P5
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment4);
        assignments.add(assignment5);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers (2 unsubmitted, 3 with REJECT)
        Reviewer reviewer = new Reviewer();
        
        // Create papers P6-P10
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Create review assignments: P6/P7 without grade, P8-P10 with REJECT
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
        
        // Expected Output: 2 unsubmitted reviews
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer reviewer = new Reviewer();
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers (1 ACCEPT, 1 UNDECIDED, 1 REJECT)
        Reviewer reviewer = new Reviewer();
        
        // Create papers P11-P13
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Create review assignments: P11 (ACCEPT), P12 (UNDECIDED), P13 (REJECT)
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
        
        // Expected Output: 1 unsubmitted review
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}