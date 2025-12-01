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
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign P1/P2/P3 to R001 without feedback and grade
        Reviewer r001 = new Reviewer();
        
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        // Grades remain UNDECIDED (default)
        
        List<ReviewAssignment> assignmentsList = new ArrayList<>();
        assignmentsList.add(ra1);
        assignmentsList.add(ra2);
        assignmentsList.add(ra3);
        
        r001.setReviewAssignments(assignmentsList);
        
        // Execute: Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign P4/P5 to R002 with grades=ACCEPT
        Reviewer r002 = new Reviewer();
        
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        List<ReviewAssignment> assignmentsList = new ArrayList<>();
        assignmentsList.add(ra4);
        assignmentsList.add(ra5);
        
        r002.setReviewAssignments(assignmentsList);
        
        // Execute: Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Create Papers P6-P10, Assign P6/P7 without grade, Assign P8-P10 with grade=REJECT
        Reviewer r003 = new Reviewer();
        
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        ReviewAssignment ra6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra7 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignmentsList = new ArrayList<>();
        assignmentsList.add(ra6);
        assignmentsList.add(ra7);
        assignmentsList.add(ra8);
        assignmentsList.add(ra9);
        assignmentsList.add(ra10);
        
        r003.setReviewAssignments(assignmentsList);
        
        // Execute: Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer r004 = new Reviewer();
        // No assignments set (default empty list)
        
        // Execute: Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Create Papers P11-P13, Assign P11 (grade=ACCEPT), P12 (no grade), P13 (grade=REJECT)
        Reviewer r005 = new Reviewer();
        
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        List<ReviewAssignment> assignmentsList = new ArrayList<>();
        assignmentsList.add(ra11);
        assignmentsList.add(ra12);
        assignmentsList.add(ra13);
        
        r005.setReviewAssignments(assignmentsList);
        
        // Execute: Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 1
        assertEquals(1, result);
    }
}