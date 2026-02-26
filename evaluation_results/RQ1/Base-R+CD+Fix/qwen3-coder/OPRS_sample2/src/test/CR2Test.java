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
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create 3 reviewers with ACCEPT grades for Paper P14
        
        // Create 3 review assignments with ACCEPT grades
        List<ReviewAssignment> reviewsP14 = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviewsP14.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviewsP14.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviewsP14.add(review3);
        
        paperP14.setReviews(reviewsP14);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create 2 reviewers with mixed grades for Paper P15
        
        List<ReviewAssignment> reviewsP15 = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviewsP15.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviewsP15.add(review2);
        
        paperP15.setReviews(reviewsP15);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with split decision (ACCEPT and REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create 4 reviewers with REJECT grades for Paper P16
        
        List<ReviewAssignment> reviewsP16 = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        reviewsP16.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviewsP16.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviewsP16.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviewsP16.add(review4);
        
        paperP16.setReviews(reviewsP16);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: 2 reviewers give grades (1 ACCEPT, 1 REJECT), 1 reviewer has UNDECIDED
        
        List<ReviewAssignment> reviewsP17 = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviewsP17.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviewsP17.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Undecided review
        reviewsP17.add(review3);
        
        paperP17.setReviews(reviewsP17);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: 4 reviewers with 2 ACCEPT and 2 REJECT grades for Paper P18
        
        List<ReviewAssignment> reviewsP18 = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviewsP18.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        reviewsP18.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviewsP18.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviewsP18.add(review4);
        
        paperP18.setReviews(reviewsP18);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}