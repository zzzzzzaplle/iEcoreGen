import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without grades/feedback
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create 3 review assignments without grades (UNDECIDED by default)
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        reviewer.getAssignments().add(assignment3);
        
        // Expected: 3 unsubmitted reviews
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        
        // Create 2 papers
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create 2 review assignments with ACCEPT grades
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment4);
        reviewer.getAssignments().add(assignment5);
        
        // Expected: 0 unsubmitted reviews (all submitted)
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 pending, 3 submitted with REJECT
        Reviewer reviewer = new Reviewer();
        
        // Create 5 papers (P6-P10)
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Create assignments for P6 and P7 without grades (UNDECIDED)
        ReviewAssignment assignment6 = new ReviewAssignment();
        ReviewAssignment assignment7 = new ReviewAssignment();
        
        // Create assignments for P8-P10 with REJECT grades
        ReviewAssignment assignment8 = new ReviewAssignment();
        assignment8.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment9 = new ReviewAssignment();
        assignment9.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment10 = new ReviewAssignment();
        assignment10.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(assignment6);
        reviewer.getAssignments().add(assignment7);
        reviewer.getAssignments().add(assignment8);
        reviewer.getAssignments().add(assignment9);
        reviewer.getAssignments().add(assignment10);
        
        // Expected: 2 unsubmitted reviews (P6 and P7)
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer reviewer = new Reviewer();
        
        // Expected: 0 unsubmitted reviews (no assignments)
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - 1 ACCEPT, 1 pending, 1 REJECT
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers (P11-P13)
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Create assignment for P11 with ACCEPT grade
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT);
        
        // Create assignment for P12 without grade (UNDECIDED)
        ReviewAssignment assignment12 = new ReviewAssignment();
        
        // Create assignment for P13 with REJECT grade
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(assignment11);
        reviewer.getAssignments().add(assignment12);
        reviewer.getAssignments().add(assignment13);
        
        // Expected: 1 unsubmitted review (P12)
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}