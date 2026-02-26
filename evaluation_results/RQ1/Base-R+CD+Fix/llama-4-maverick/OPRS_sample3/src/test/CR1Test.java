import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer r001 = new Reviewer();
        
        // Create papers P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Assign papers to reviewer without setting grade (default is UNDECIDED)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        r001.getAssignments().add(ra1);
        r001.getAssignments().add(ra2);
        r001.getAssignments().add(ra3);
        
        // Expected Output: 3 unsubmitted reviews
        assertEquals(3, r001.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer r002 = new Reviewer();
        
        // Create papers P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Assign papers to reviewer with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        r002.getAssignments().add(ra4);
        r002.getAssignments().add(ra5);
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, r002.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        Reviewer r003 = new Reviewer();
        
        // Create papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Assign P6/P7 without grade (UNDECIDED), P8-P10 with REJECT grade
        ReviewAssignment ra6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra7 = new ReviewAssignment(); // UNDECIDED
        
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        r003.getAssignments().add(ra6);
        r003.getAssignments().add(ra7);
        r003.getAssignments().add(ra8);
        r003.getAssignments().add(ra9);
        r003.getAssignments().add(ra10);
        
        // Expected Output: 2 unsubmitted reviews (P6 and P7)
        assertEquals(2, r003.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, r004.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        Reviewer r005 = new Reviewer();
        
        // Create papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Assign P11 (ACCEPT), P12 (UNDECIDED), P13 (REJECT)
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED
        
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        r005.getAssignments().add(ra11);
        r005.getAssignments().add(ra12);
        r005.getAssignments().add(ra13);
        
        // Expected Output: 1 unsubmitted review (P12)
        assertEquals(1, r005.calculateUnsubmittedReviews());
    }
}