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
        paperP15 = new Paper();
        paperP16 = new Paper();
        paperP17 = new Paper();
        paperP18 = new Paper();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14 and 3 reviews with ACCEPT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        paperP14.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or Reject
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify: Expected output is True
        assertTrue("All 3 reviews are ACCEPT, should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create Paper P15 and 2 reviews with split decision
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Reviews are split between ACCEPT and REJECT, should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16 and 4 reviews with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paperP16.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify: Expected output is True
        assertTrue("All 4 reviews are REJECT, should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17 with 3 reviews (2 with grades, 1 without grade)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // Create 1 review assignment without grade (UNDECIDED)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Reviews have mixed grades including UNDECIDED, should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18 with 4 reviews: 2 ACCEPT, 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        reviews.add(review2);
        
        // Create 2 review assignments with REJECT grade
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviews.add(review4);
        
        paperP18.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT, should return false", result);
    }
}