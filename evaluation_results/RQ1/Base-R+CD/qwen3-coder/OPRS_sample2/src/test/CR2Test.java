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
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create 3 review assignments with ACCEPT grade and feedback
        List<ReviewAssignment> reviews = new ArrayList<>();
        
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
        
        // Test: Check if all reviews are exclusively Accept
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous accept
        assertTrue("Paper P14 with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create 2 review assignments with split decision (1 ACCEPT, 1 REJECT)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify: Should return false for split decision
        assertFalse("Paper P15 with 1 ACCEPT and 1 REJECT should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("feedback1");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        reviews.add(review4);
        
        paperP16.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous reject
        assertTrue("Paper P16 with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create 3 review assignments with mixed grades (1 ACCEPT, 1 REJECT, 1 undecided)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Undecided review
        review3.setFeedback(null); // No feedback given
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify: Should return false for mixed grades
        assertFalse("Paper P17 with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 review assignments with 2 ACCEPT and 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("feedback2");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        reviews.add(review4);
        
        paperP18.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify: Should return false for 50-50 split
        assertFalse("Paper P18 with 2 ACCEPT and 2 REJECT should return false", result);
    }
}