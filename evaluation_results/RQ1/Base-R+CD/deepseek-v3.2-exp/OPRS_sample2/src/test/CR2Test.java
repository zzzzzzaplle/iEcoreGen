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
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 3 review assignments with ACCEPT grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        
        paperP14.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are ACCEPT)
        assertTrue("All reviews should be consistent (all ACCEPT)", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Good paper");
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Needs major revisions");
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False (mixed grades: ACCEPT and REJECT)
        assertFalse("Reviews should not be consistent (mixed ACCEPT and REJECT)", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Not suitable for publication");
            reviews.add(review);
        }
        
        paperP16.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are REJECT)
        assertTrue("All reviews should be consistent (all REJECT)", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Accept with minor revisions");
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Major issues found");
        reviews.add(review2);
        
        // Third reviewer does not give feedback (UNDECIDED)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        // No feedback set for undecided reviewer
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False (mixed submitted grades: ACCEPT and REJECT)
        assertFalse("Reviews should not be consistent (mixed submitted grades)", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // 2 reviewers give ACCEPT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("Good paper");
            reviews.add(review);
        }
        
        // 2 reviewers give REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Needs improvement");
            reviews.add(review);
        }
        
        paperP18.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False (equal number of ACCEPT and REJECT)
        assertFalse("Reviews should not be consistent (50% ACCEPT, 50% REJECT)", result);
    }
}