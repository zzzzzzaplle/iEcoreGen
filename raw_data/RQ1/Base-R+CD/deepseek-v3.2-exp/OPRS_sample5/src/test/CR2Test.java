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
        // Initialize papers for test cases
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
        // All 3 reviewers give ACCEPT grades with feedback
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        // Create second review with ACCEPT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        // Create third review with ACCEPT grade
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        paperP14.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous (all ACCEPT)
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify: Expected output is True
        assertTrue("All 3 reviews should be unanimous ACCEPT", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        // One gives ACCEPT, one gives REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        // Create second review with REJECT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify: Expected output is False (split decision)
        assertFalse("Split decision 2-1 should not be unanimous", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        // All 4 reviewers give REJECT grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 reviews with REJECT grades
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paperP16.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify: Expected output is True (all REJECT is still unanimous)
        assertTrue("All 4 reviews should be unanimous REJECT", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        // 2 reviewers give grades (1 ACCEPT, 1 REJECT), 1 reviewer doesn't give feedback (UNDECIDED)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        // Create second review with REJECT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // Create third review with UNDECIDED grade (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify: Expected output is False (mixed grades among submitted reviews)
        assertFalse("Mixed grades with UNDECIDED should not be unanimous", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        // 2 reviewers give ACCEPT, 2 reviewers give REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 reviews with ACCEPT grades
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            reviews.add(review);
        }
        
        // Create 2 reviews with REJECT grades
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paperP18.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify: Expected output is False (50-50 split is not unanimous)
        assertFalse("50% acceptance rate should not be unanimous", result);
    }
}