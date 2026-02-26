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
        // Setup: Create Reviewer R001
        reviewer = new Reviewer();
        
        // Create Paper P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        // Default grade is UNDECIDED, no feedback set
        
        assignments.add(ra1);
        assignments.add(ra2);
        assignments.add(ra3);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        assignments.add(ra4);
        assignments.add(ra5);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        reviewer = new Reviewer();
        
        // Create Papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003
        ReviewAssignment ra6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra7 = new ReviewAssignment(); // UNDECIDED
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        assignments.add(ra6);
        assignments.add(ra7);
        assignments.add(ra8);
        assignments.add(ra9);
        assignments.add(ra10);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>()); // Empty assignments
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        reviewer = new Reviewer();
        
        // Create Papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        assignments.add(ra11);
        assignments.add(ra12);
        assignments.add(ra13);
        reviewer.setAssignments(assignments);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}