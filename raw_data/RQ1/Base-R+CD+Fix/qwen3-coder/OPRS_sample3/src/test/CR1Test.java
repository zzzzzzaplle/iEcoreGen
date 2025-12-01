import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<Paper> papers;
    private List<ReviewAssignment> assignments;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        papers = new ArrayList<>();
        assignments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001, Papers P1, P2, P3, and assign them without feedback/grade
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 3 papers and assignments with UNDECIDED grade
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED); // No grade submitted
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Papers P4, P5, assign with ACCEPT grades
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 2 papers and assignments with ACCEPT grade
        for (int i = 4; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT); // Grade submitted
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Papers P6-P10
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 5 papers
        for (int i = 6; i <= 10; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            
            // P6, P7: UNDECIDED (unsubmitted)
            // P8-P10: REJECT (submitted)
            if (i <= 7) {
                assignment.setGrade(Grade.UNDECIDED);
            } else {
                assignment.setGrade(Grade.REJECT);
            }
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setAssignments(new ArrayList<>()); // Empty assignments list
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Papers P11-P13
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 3 papers
        for (int i = 11; i <= 13; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            
            // P11: ACCEPT (submitted)
            // P12: UNDECIDED (unsubmitted)
            // P13: REJECT (submitted)
            if (i == 11) {
                assignment.setGrade(Grade.ACCEPT);
            } else if (i == 12) {
                assignment.setGrade(Grade.UNDECIDED);
            } else {
                assignment.setGrade(Grade.REJECT);
            }
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}