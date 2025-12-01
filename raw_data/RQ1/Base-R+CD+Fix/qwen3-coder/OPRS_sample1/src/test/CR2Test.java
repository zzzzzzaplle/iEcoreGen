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
        // Initialize papers for all test cases
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
        // Setup: Create 3 reviewers and assign paper P14 to them with ACCEPT grades
        
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
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paperP14.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper P14 should return true for unanimous accept reviews", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create 2 reviewers and assign paper P15 to them with split decision
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revision");
        
        // Add reviews to paper P15
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        paperP15.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper P15 should return false for split decision reviews", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create 4 reviewers and assign paper P16 to them with REJECT grades
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("poor methodology");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("insufficient data");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("weak conclusions");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("not novel");
        
        // Add reviews to paper P16
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paperP16.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper P16 should return true for all reject reviews", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create 3 reviewers, assign paper P17, but only 2 give feedback
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("well-written");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs more experiments");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't submitted review
        
        // Add reviews to paper P17
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paperP17.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper P17 should return false for mixed grades with undecided review", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create 4 reviewers and assign paper P18 with 2 ACCEPT, 2 REJECT
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("strong contribution");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("good results");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("limited scope");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("technical flaws");
        
        // Add reviews to paper P18
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paperP18.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper P18 should return false for 50-50 split decision", result);
    }
}