import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, create 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers (simulated by ReviewAssignment objects)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
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
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All reviews should be unanimously Accept", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, create 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments with mixed grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Needs major revisions");
        
        reviews.add(review1);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews should not be unanimous with split decision", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, create 4 reviewers, assign paper to reviewers, all give REJECT grade
        
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All reviews should be unanimously Reject", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, create 3 reviewers, assign paper to reviewers, 
        // 2 reviewers give grades (1 ACCEPT, 1 REJECT), 1 reviewer doesn't give feedback
        
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Acceptable");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Rejected");
        
        ReviewAssignment review3 = new ReviewAssignment();
        // Reviewer 3 doesn't give feedback (grade remains UNDECIDED)
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews should not be unanimous with mixed grades and undecided", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, create 4 reviewers, assign paper to reviewers, 2 ACCEPT and 2 REJECT
        
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments with 2 ACCEPT and 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews should not be unanimous with 50-50 split", result);
    }
}