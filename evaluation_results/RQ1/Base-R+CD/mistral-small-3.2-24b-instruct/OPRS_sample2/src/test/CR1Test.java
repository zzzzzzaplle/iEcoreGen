import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001, Papers P1, P2, P3, and assign them without feedback and grade
        Reviewer r001 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 3 review assignments with UNDECIDED grade (unsubmitted)
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        r001.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Papers P4, P5, and assign them with ACCEPT grade
        Reviewer r002 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 2 review assignments with ACCEPT grade (submitted)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        r002.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Papers P6-P10
        Reviewer r003 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Assign P6/P7 without feedback and grade (UNDECIDED)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        // Assign P8-P10 with REJECT grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        r003.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        r004.setReviewAssignments(new ArrayList<>());
        
        // Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Papers P11-P13
        Reviewer r005 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P11 with ACCEPT grade (submitted)
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        
        // P12 without grade and feedback (UNDECIDED)
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        assignments.add(assignment2);
        
        // P13 with REJECT grade (submitted)
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        
        r005.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}