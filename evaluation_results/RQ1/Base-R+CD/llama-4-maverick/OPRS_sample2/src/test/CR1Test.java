import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Create Reviewer R001
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P1, P2, P3
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment assignment1 = new ReviewAssignment();
        ReviewAssignment assignment2 = new ReviewAssignment();
        ReviewAssignment assignment3 = new ReviewAssignment();
        
        // Assignments are created with default UNDECIDED grade
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        assignments.add(assignment3);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Create Reviewer R002
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment assignment4 = new ReviewAssignment();
        assignment4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment5 = new ReviewAssignment();
        assignment5.setGrade(Grade.ACCEPT);
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(assignment4);
        assignments.add(assignment5);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Create Reviewer R003
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P6-P10
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (default UNDECIDED)
        ReviewAssignment assignment6 = new ReviewAssignment();
        ReviewAssignment assignment7 = new ReviewAssignment();
        
        // Assign P8-P10 to R003 with grade=REJECT
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
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void