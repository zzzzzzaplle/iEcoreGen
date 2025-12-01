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
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001, Papers P1, P2, P3
        reviewer = new Reviewer();
        papers.add(new Paper());
        papers.add(new Paper());
        papers.add(new Paper());
        
        // Assign papers to reviewer without feedback and grade (UNDECIDED by default)
        for (Paper paper : papers) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignments.add(assignment);
            reviewer.addAssignment(assignment);
        }
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Papers P4, P5
        reviewer = new Reviewer();
        papers.add(new Paper());
        papers.add(new Paper());
        
        // Assign papers to reviewer with grades=ACCEPT
        for (Paper paper : papers) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
            reviewer.addAssignment(assignment);
        }
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Papers P6-P10
        reviewer = new Reviewer();
        for (int i = 0; i < 5; i++) {
            papers.add(new Paper());
        }
        
        // Assign P6/P7 without feedback and grade (UNDECIDED)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignments.add(assignment);
            reviewer.addAssignment(assignment);
        }
        
        // Assign P8-P10 with grade=REJECT
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
            reviewer.addAssignment(assignment);
        }
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Papers P11-P13
        reviewer = new Reviewer();
        papers.add(new Paper());
        papers.add(new Paper());
        papers.add(new Paper());
        
        // Assign P11 with grade=ACCEPT
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        reviewer.addAssignment(assignment1);
        
        // Assign P12 without feedback and grade (UNDECIDED)
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignments.add(assignment2);
        reviewer.addAssignment(assignment2);
        
        // Assign P13 with grade=REJECT
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        reviewer.addAssignment(assignment3);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}