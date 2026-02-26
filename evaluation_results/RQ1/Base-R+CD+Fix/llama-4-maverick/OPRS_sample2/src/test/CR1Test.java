import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<ReviewAssignment> assignments;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        assignments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 pending reviews
        reviewer.setReviewAssignments(assignments);
        
        // Create 3 review assignments without feedback and grade (UNDECIDED)
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 submitted reviews
        reviewer.setReviewAssignments(assignments);
        
        // Create 2 review assignments with ACCEPT grade
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        reviewer.setReviewAssignments(assignments);
        
        // Create 2 pending reviews (UNDECIDED)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        // Create 3 submitted reviews with REJECT grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setReviewAssignments(assignments); // Empty list
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with partially submitted reviews
        reviewer.setReviewAssignments(assignments);
        
        // Create submitted review with ACCEPT grade
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        
        // Create pending review (UNDECIDED)
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        assignments.add(assignment2);
        
        // Create submitted review with REJECT grade
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals(1, result);
    }
}