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
        
        // Create papers P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create review assignments without setting grade (default is UNDECIDED)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        r001.getAssignments().add(ra1);
        r001.getAssignments().add(ra2);
        r001.getAssignments().add(ra3);
        
        // Expected Output: 3
        assertEquals(3, r001.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer r002 = new Reviewer();
        
        // Create papers P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        r002.getAssignments().add(ra4);
        r002.getAssignments().add(ra5);
        
        // Expected Output: 0
        assertEquals(0, r002.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        Reviewer r003 = new Reviewer();
        
        // Create papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Create review assignments
        // P6, P7: no grade (UNDECIDED)
        ReviewAssignment ra6 = new ReviewAssignment();
        ReviewAssignment ra7 = new ReviewAssignment();
        
        // P8-P10: REJECT grades
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        r003.getAssignments().add(ra6);
        r003.getAssignments().add(ra7);
        r003.getAssignments().add(ra8);
        r003.getAssignments().add(ra9);
        r003.getAssignments().add(ra10);
        
        // Expected Output: 2
        assertEquals(2, r003.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, r004.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with partially submitted reviews
        Reviewer r005 = new Reviewer();
        
        // Create papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Create review assignments
        // P11: ACCEPT grade
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        
        // P12: no grade (UNDECIDED)
        ReviewAssignment ra12 = new ReviewAssignment();
        
        // P13: REJECT grade
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        r005.getAssignments().add(ra11);
        r005.getAssignments().add(ra12);
        r005.getAssignments().add(ra13);
        
        // Expected Output: 1
        assertEquals(1, r005.calculateUnsubmittedReviews());
    }
}