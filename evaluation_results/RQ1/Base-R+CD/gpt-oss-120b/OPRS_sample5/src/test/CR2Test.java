import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    
    @Before
    public void setUp() {
        // Initialize papers for each test case
        paperP14 = new Paper("P14", PaperType.RESEARCH);
        paperP15 = new Paper("P15", PaperType.RESEARCH);
        paperP16 = new Paper("P16", PaperType.RESEARCH);
        paperP17 = new Paper("P17", PaperType.RESEARCH);
        paperP18 = new Paper("P18", PaperType.RESEARCH);
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        // All 3 reviewers give ACCEPT grade with "revise" feedback
        ReviewAssignment review1 = new ReviewAssignment("revise", Grade.ACCEPT);
        ReviewAssignment review2 = new ReviewAssignment("revise", Grade.ACCEPT);
        ReviewAssignment review3 = new ReviewAssignment("revise", Grade.ACCEPT);
        
        paperP14.addReview(review1);
        paperP14.addReview(review2);
        paperP14.addReview(review3);
        
        // Check consensus for Paper P14
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        // 1 reviewer gives ACCEPT, 1 gives REJECT
        ReviewAssignment review1 = new ReviewAssignment("good paper", Grade.ACCEPT);
        ReviewAssignment review2 = new ReviewAssignment("needs improvement", Grade.REJECT);
        
        paperP15.addReview(review1);
        paperP15.addReview(review2);
        
        // Check consensus for Paper P15
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        // All 4 reviewers give REJECT grade
        ReviewAssignment review1 = new ReviewAssignment("poor quality", Grade.REJECT);
        ReviewAssignment review2 = new ReviewAssignment("not suitable", Grade.REJECT);
        ReviewAssignment review3 = new ReviewAssignment("reject", Grade.REJECT);
        ReviewAssignment review4 = new ReviewAssignment("decline", Grade.REJECT);
        
        paperP16.addReview(review1);
        paperP16.addReview(review2);
        paperP16.addReview(review3);
        paperP16.addReview(review4);
        
        // Check consensus for Paper P16
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        // 2 reviewers have given feedback: 1 ACCEPT, 1 REJECT
        // 1 reviewer has not given feedback (UNDECIDED)
        ReviewAssignment review1 = new ReviewAssignment("accept", Grade.ACCEPT);
        ReviewAssignment review2 = new ReviewAssignment("reject", Grade.REJECT);
        ReviewAssignment review3 = new ReviewAssignment(); // UNDECIDED by default
        
        paperP17.addReview(review1);
        paperP17.addReview(review2);
        paperP17.addReview(review3);
        
        // Check consensus for Paper P17
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed grades and UNDECIDED review should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        // 2 reviewers give ACCEPT, 2 give REJECT
        ReviewAssignment review1 = new ReviewAssignment("good", Grade.ACCEPT);
        ReviewAssignment review2 = new ReviewAssignment("excellent", Grade.ACCEPT);
        ReviewAssignment review3 = new ReviewAssignment("poor", Grade.REJECT);
        ReviewAssignment review4 = new ReviewAssignment("weak", Grade.REJECT);
        
        paperP18.addReview(review1);
        paperP18.addReview(review2);
        paperP18.addReview(review3);
        paperP18.addReview(review4);
        
        // Check consensus for Paper P18
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with exactly 50% acceptance (2 ACCEPT, 2 REJECT) should return false", result);
    }
}