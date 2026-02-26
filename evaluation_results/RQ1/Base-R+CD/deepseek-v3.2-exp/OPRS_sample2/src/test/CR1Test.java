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
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 3 papers
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            // Create review assignment without grade (UNDECIDED by default)
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            
            assignments.add(assignment);
        }
        
        reviewer.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 2 papers
        for (int i = 4; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            // Create review assignment with ACCEPT grade
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            
            assignments.add(assignment);
        }
        
        reviewer.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 without grade, 3 with REJECT grade
        reviewer.setAssignments(new ArrayList<>());
        
        // Papers P6-P7: No grade (UNDECIDED)
        for (int i = 6; i <= 7; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            
            assignments.add(assignment);
        }
        
        // Papers P8-P10: REJECT grade
        for (int i = 8; i <= 10; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            
            assignments.add(assignment);
        }
        
        reviewer.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setAssignments(new ArrayList<>());
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - 1 ACCEPT, 1 no grade, 1 REJECT
        reviewer.setAssignments(new ArrayList<>());
        
        // Paper P11: ACCEPT grade
        Paper paper11 = new Paper();
        paper11.setTitle("P11");
        ReviewAssignment assignment11 = new ReviewAssignment();
        assignment11.setGrade(Grade.ACCEPT);
        assignments.add(assignment11);
        
        // Paper P12: No grade (UNDECIDED)
        Paper paper12 = new Paper();
        paper12.setTitle("P12");
        ReviewAssignment assignment12 = new ReviewAssignment();
        assignment12.setGrade(Grade.UNDECIDED);
        assignments.add(assignment12);
        
        // Paper P13: REJECT grade
        Paper paper13 = new Paper();
        paper13.setTitle("P13");
        ReviewAssignment assignment13 = new ReviewAssignment();
        assignment13.setGrade(Grade.REJECT);
        assignments.add(assignment13);
        
        reviewer.setAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}