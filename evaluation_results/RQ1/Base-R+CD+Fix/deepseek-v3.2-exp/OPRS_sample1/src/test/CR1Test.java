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
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer r001 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 3 papers and assignments with UNDECIDED grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        r001.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer r002 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 2 assignments with ACCEPT grade
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        r002.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        Reviewer r003 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P6/P7: No grade (UNDECIDED) - 2 unsubmitted
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        // P8-P10: REJECT grade - 3 submitted
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        r003.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        // No assignments set (empty list by default)
        
        // Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        Reviewer r005 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P11: ACCEPT grade - submitted
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        
        // P12: No grade (UNDECIDED) - unsubmitted
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        assignments.add(assignment2);
        
        // P13: REJECT grade - submitted
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        
        r005.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}