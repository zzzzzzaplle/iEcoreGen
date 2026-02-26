import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer1;
    private Reviewer reviewer2;
    private Reviewer reviewer3;
    private Reviewer reviewer4;
    private Reviewer reviewer5;
    
    @Before
    public void setUp() {
        // Initialize reviewers for test cases
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign without feedback and grade
        Reviewer r001 = reviewer1;
        
        // Create papers
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create review assignments without grades (default UNDECIDED)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Assign to reviewer
        r001.getReviewAssignments().add(ra1);
        r001.getReviewAssignments().add(ra2);
        r001.getReviewAssignments().add(ra3);
        
        // Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5, assign with grades=ACCEPT
        Reviewer r002 = reviewer2;
        
        // Create papers
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Assign to reviewer
        r002.getReviewAssignments().add(ra4);
        r002.getReviewAssignments().add(ra5);
        
        // Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10
        Reviewer r003 = reviewer3;
        
        // Create papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Create review assignments
        // P6/P7 without feedback and grade (default UNDECIDED)
        ReviewAssignment ra6 = new ReviewAssignment();
        ReviewAssignment ra7 = new ReviewAssignment();
        
        // P8-P10 with grade=REJECT
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Assign to reviewer
        r003.getReviewAssignments().add(ra6);
        r003.getReviewAssignments().add(ra7);
        r003.getReviewAssignments().add(ra8);
        r003.getReviewAssignments().add(ra9);
        r003.getReviewAssignments().add(ra10);
        
        // Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer r004 = reviewer4;
        
        // Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13
        Reviewer r005 = reviewer5;
        
        // Create papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Create review assignments
        // P11 (grade=ACCEPT)
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        
        // P12 (no grade and feedback - default UNDECIDED)
        ReviewAssignment ra12 = new ReviewAssignment();
        
        // P13 (grade=REJECT)
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Assign to reviewer
        r005.getReviewAssignments().add(ra11);
        r005.getReviewAssignments().add(ra12);
        r005.getReviewAssignments().add(ra13);
        
        // Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}