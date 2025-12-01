import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = null;
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without grades
        reviewer = new Reviewer();
        
        // Create 3 papers
        Paper p1 = new Paper("Paper 1", PaperType.RESEARCH);
        Paper p2 = new Paper("Paper 2", PaperType.EXPERIENCE);
        Paper p3 = new Paper("Paper 3", PaperType.RESEARCH);
        
        // Create review assignments without setting grades (default UNDECIDED)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        reviewer.addAssignment(ra1);
        reviewer.addAssignment(ra2);
        reviewer.addAssignment(ra3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer = new Reviewer();
        
        // Create 2 papers
        Paper p4 = new Paper("Paper 4", PaperType.RESEARCH);
        Paper p5 = new Paper("Paper 5", PaperType.EXPERIENCE);
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.addAssignment(ra4);
        reviewer.addAssignment(ra5);
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 pending, 3 submitted with REJECT
        reviewer = new Reviewer();
        
        // Create 5 papers
        Paper p6 = new Paper("Paper 6", PaperType.RESEARCH);
        Paper p7 = new Paper("Paper 7", PaperType.EXPERIENCE);
        Paper p8 = new Paper("Paper 8", PaperType.RESEARCH);
        Paper p9 = new Paper("Paper 9", PaperType.EXPERIENCE);
        Paper p10 = new Paper("Paper 10", PaperType.RESEARCH);
        
        // Create review assignments - first 2 without grades (UNDECIDED), next 3 with REJECT
        ReviewAssignment ra6 = new ReviewAssignment(); // UNDECIDED
        ReviewAssignment ra7 = new ReviewAssignment(); // UNDECIDED
        
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        reviewer.addAssignment(ra6);
        reviewer.addAssignment(ra7);
        reviewer.addAssignment(ra8);
        reviewer.addAssignment(ra9);
        reviewer.addAssignment(ra10);
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer = new Reviewer();
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - 1 ACCEPT, 1 UNDECIDED, 1 REJECT
        reviewer = new Reviewer();
        
        // Create 3 papers
        Paper p11 = new Paper("Paper 11", PaperType.RESEARCH);
        Paper p12 = new Paper("Paper 12", PaperType.EXPERIENCE);
        Paper p13 = new Paper("Paper 13", PaperType.RESEARCH);
        
        // Create review assignments with different grades
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT); // Submitted
        
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED (not submitted)
        
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT); // Submitted
        
        // Add assignments to reviewer
        reviewer.addAssignment(ra11);
        reviewer.addAssignment(ra12);
        reviewer.addAssignment(ra13);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}