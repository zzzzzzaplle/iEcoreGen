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
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer1.setAssignments(new ArrayList<>());
        
        // Create 3 review assignments with UNDECIDED grade (no feedback/grade submitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.UNDECIDED);
        
        reviewer1.getAssignments().add(ra1);
        reviewer1.getAssignments().add(ra2);
        reviewer1.getAssignments().add(ra3);
        
        // Calculate unsubmitted reviews
        int result = reviewer1.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer2.setAssignments(new ArrayList<>());
        
        // Create 2 review assignments with ACCEPT grade (submitted reviews)
        ReviewAssignment ra4 = new ReviewAssignment("Good paper", Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment("Excellent work", Grade.ACCEPT);
        
        reviewer2.getAssignments().add(ra4);
        reviewer2.getAssignments().add(ra5);
        
        // Calculate unsubmitted reviews
        int result = reviewer2.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003 with 5 papers assigned
        // 2 without feedback/grade, 3 with REJECT grade
        reviewer3.setAssignments(new ArrayList<>());
        
        // 2 assignments with UNDECIDED grade (unsubmitted)
        ReviewAssignment ra6 = new ReviewAssignment();
        ra6.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra7 = new ReviewAssignment();
        ra7.setGrade(Grade.UNDECIDED);
        
        // 3 assignments with REJECT grade (submitted)
        ReviewAssignment ra8 = new ReviewAssignment("Poor methodology", Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment("Insufficient data", Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment("Weak conclusions", Grade.REJECT);
        
        reviewer3.getAssignments().add(ra6);
        reviewer3.getAssignments().add(ra7);
        reviewer3.getAssignments().add(ra8);
        reviewer3.getAssignments().add(ra9);
        reviewer3.getAssignments().add(ra10);
        
        // Calculate unsubmitted reviews
        int result = reviewer3.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 with no assignments (default empty list)
        // No setup needed - reviewer4 has empty assignments list by default
        
        // Calculate unsubmitted reviews
        int result = reviewer4.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005 with 3 papers assigned
        // P11 (ACCEPT), P12 (no grade), P13 (REJECT)
        reviewer5.setAssignments(new ArrayList<>());
        
        // Assignment with ACCEPT grade (submitted)
        ReviewAssignment ra11 = new ReviewAssignment("Strong contribution", Grade.ACCEPT);
        
        // Assignment with UNDECIDED grade (unsubmitted)
        ReviewAssignment ra12 = new ReviewAssignment();
        ra12.setGrade(Grade.UNDECIDED);
        
        // Assignment with REJECT grade (submitted)
        ReviewAssignment ra13 = new ReviewAssignment("Limited novelty", Grade.REJECT);
        
        reviewer5.getAssignments().add(ra11);
        reviewer5.getAssignments().add(ra12);
        reviewer5.getAssignments().add(ra13);
        
        // Calculate unsubmitted reviews
        int result = reviewer5.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}