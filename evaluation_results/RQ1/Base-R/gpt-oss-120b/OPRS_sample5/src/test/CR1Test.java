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
    
    private Paper paper1;
    private Paper paper2;
    private Paper paper3;
    private Paper paper4;
    private Paper paper5;
    private Paper paper6;
    private Paper paper7;
    private Paper paper8;
    private Paper paper9;
    private Paper paper10;
    private Paper paper11;
    private Paper paper12;
    private Paper paper13;
    
    @Before
    public void setUp() {
        // Initialize reviewers
        reviewer1 = new Reviewer("R001");
        reviewer2 = new Reviewer("R002");
        reviewer3 = new Reviewer("R003");
        reviewer4 = new Reviewer("R004");
        reviewer5 = new Reviewer("R005");
        
        // Initialize papers
        paper1 = new Paper("Paper1", PaperType.RESEARCH);
        paper2 = new Paper("Paper2", PaperType.EXPERIENCE_REPORT);
        paper3 = new Paper("Paper3", PaperType.RESEARCH);
        paper4 = new Paper("Paper4", PaperType.RESEARCH);
        paper5 = new Paper("Paper5", PaperType.EXPERIENCE_REPORT);
        paper6 = new Paper("Paper6", PaperType.RESEARCH);
        paper7 = new Paper("Paper7", PaperType.EXPERIENCE_REPORT);
        paper8 = new Paper("Paper8", PaperType.RESEARCH);
        paper9 = new Paper("Paper9", PaperType.EXPERIENCE_REPORT);
        paper10 = new Paper("Paper10", PaperType.RESEARCH);
        paper11 = new Paper("Paper11", PaperType.RESEARCH);
        paper12 = new Paper("Paper12", PaperType.EXPERIENCE_REPORT);
        paper13 = new Paper("Paper13", PaperType.RESEARCH);
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned but no reviews submitted
        reviewer1.assignPaper(paper1);
        reviewer1.assignPaper(paper2);
        reviewer1.assignPaper(paper3);
        
        // Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer1.getUnsubmittedReviewCount();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned and all reviews submitted
        reviewer2.assignPaper(paper4);
        reviewer2.assignPaper(paper5);
        
        // Submit reviews for both papers with ACCEPT grade
        reviewer2.submitReview(paper4, "Good paper", Grade.ACCEPT);
        reviewer2.submitReview(paper5, "Excellent work", Grade.ACCEPT);
        
        // Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer2.getUnsubmittedReviewCount();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned, 3 submitted and 2 pending
        reviewer3.assignPaper(paper6);
        reviewer3.assignPaper(paper7);
        reviewer3.assignPaper(paper8);
        reviewer3.assignPaper(paper9);
        reviewer3.assignPaper(paper10);
        
        // Submit reviews for P8, P9, P10 with REJECT grade
        reviewer3.submitReview(paper8, "Needs improvement", Grade.REJECT);
        reviewer3.submitReview(paper9, "Not suitable", Grade.REJECT);
        reviewer3.submitReview(paper10, "Weak methodology", Grade.REJECT);
        
        // P6 and P7 remain without reviews
        
        // Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer3.getUnsubmittedReviewCount();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        // No papers assigned to reviewer4
        
        // Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer4.getUnsubmittedReviewCount();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned, 2 submitted and 1 pending
        reviewer5.assignPaper(paper11);
        reviewer5.assignPaper(paper12);
        reviewer5.assignPaper(paper13);
        
        // Submit reviews for P11 (ACCEPT) and P13 (REJECT)
        reviewer5.submitReview(paper11, "Strong contribution", Grade.ACCEPT);
        reviewer5.submitReview(paper13, "Limited novelty", Grade.REJECT);
        
        // P12 remains without review
        
        // Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer5.getUnsubmittedReviewCount();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}