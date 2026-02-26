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
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        paperP18 = new Paper();
        paperP18.setTitle("P18");
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        // All 3 reviewers give ACCEPT reviews with feedback 'revise'
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        // Add reviews to paper P14
        paperP14.addReview(review1);
        paperP14.addReview(review2);
        paperP14.addReview(review3);
        
        // Check if all reviews are consistent (should be true for unanimous accept)
        boolean result = paperP14.isAllReviewsConsistent();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should have consistent reviews", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        // 1 reviewer gives ACCEPT, 1 gives REJECT
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        
        // Add reviews to paper P15
        paperP15.addReview(review1);
        paperP15.addReview(review2);
        
        // Check if all reviews are consistent (should be false for split decision)
        boolean result = paperP15.isAllReviewsConsistent();
        
        // Expected Output: False
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should not have consistent reviews", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        // All 4 reviewers give REJECT reviews
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper P16
        paperP16.addReview(review1);
        paperP16.addReview(review2);
        paperP16.addReview(review3);
        paperP16.addReview(review4);
        
        // Check if all reviews are consistent (should be true for unanimous reject)
        boolean result = paperP16.isAllReviewsConsistent();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should have consistent reviews", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        // 2 reviewers have given feedback: 1 ACCEPT, 1 REJECT
        // 1 reviewer has not given feedback (UNDECIDED)
        
        // Create 3 review assignments with mixed grades including UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("acceptable");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("not suitable");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't submitted feedback
        review3.setFeedback(null);
        
        // Add reviews to paper P17
        paperP17.addReview(review1);
        paperP17.addReview(review2);
        paperP17.addReview(review3);
        
        // Check if all reviews are consistent (should be false due to UNDECIDED and mixed grades)
        boolean result = paperP17.isAllReviewsConsistent();
        
        // Expected Output: False
        assertFalse("Paper with UNDECIDED review and mixed grades should not have consistent reviews", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        // 2 reviewers give ACCEPT, 2 give REJECT (50% acceptance)
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper P18
        paperP18.addReview(review1);
        paperP18.addReview(review2);
        paperP18.addReview(review3);
        paperP18.addReview(review4);
        
        // Check if all reviews are consistent (should be false for split decision)
        boolean result = paperP18.isAllReviewsConsistent();
        
        // Expected Output: False
        assertFalse("Paper with 50% acceptance (2 ACCEPT, 2 REJECT) should not have consistent reviews", result);
    }
}