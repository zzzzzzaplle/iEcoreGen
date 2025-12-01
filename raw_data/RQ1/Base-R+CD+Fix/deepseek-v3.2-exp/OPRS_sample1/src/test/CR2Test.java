import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Input: Check consensus for Paper P14
        // Setup: Create Paper P14, 3 reviewers, assign paper, all give ACCEPT reviews
        
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review: ACCEPT with feedback 'revise'
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        // Create second review: ACCEPT with feedback 'revise'
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        // Create third review: ACCEPT with feedback 'revise'
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are ACCEPT)
        assertTrue("All reviews should be positive (ACCEPT) for unanimous accept case", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Input: Check consensus for Paper P15
        // Setup: Create Paper P15, 2 reviewers, assign paper, 1 ACCEPT and 1 REJECT
        
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        reviews.add(review1);
        
        // Create second review: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        reviews.add(review2);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed reviews)
        assertFalse("Should return false for split decision (ACCEPT and REJECT)", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Input: Check consensus for Paper P16
        // Setup: Create Paper P16, 4 reviewers, assign paper, all give REJECT reviews
        
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review: REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("poor methodology");
        reviews.add(review1);
        
        // Create second review: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("insufficient results");
        reviews.add(review2);
        
        // Create third review: REJECT
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("weak contribution");
        reviews.add(review3);
        
        // Create fourth review: REJECT
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("not suitable for publication");
        reviews.add(review4);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are REJECT - exclusively one type)
        assertTrue("All reviews should be positive (exclusively REJECT) for all reject case", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Input: Check consensus for Paper P17
        // Setup: Create Paper P17, 3 reviewers, assign paper, 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("well-written paper");
        reviews.add(review1);
        
        // Create second review: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("methodology issues");
        reviews.add(review2);
        
        // Create third review: UNDECIDED (reviewer didn't submit feedback)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        // No feedback as per specification
        reviews.add(review3);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed grades among submitted reviews)
        assertFalse("Should return false for mixed grades (ACCEPT, REJECT, and UNDECIDED)", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Input: Check consensus for Paper P18
        // Setup: Create Paper P18, 4 reviewers, assign paper, 2 ACCEPT and 2 REJECT
        
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("strong contribution");
        reviews.add(review1);
        
        // Create second review: ACCEPT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("good experimental design");
        reviews.add(review2);
        
        // Create third review: REJECT
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("limited novelty");
        reviews.add(review3);
        
        // Create fourth review: REJECT
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("weak evaluation");
        reviews.add(review4);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed reviews - 2 ACCEPT and 2 REJECT)
        assertFalse("Should return false for exactly 50% acceptance (mixed reviews)", result);
    }
}