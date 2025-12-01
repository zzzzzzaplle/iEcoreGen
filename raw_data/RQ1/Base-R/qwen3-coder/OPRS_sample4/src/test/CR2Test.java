import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade(Review.Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade(Review.Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if all reviews are consistent (should be true for unanimous accept)
        boolean result = paper.areAllReviewsConsistent();
        assertTrue("All reviews should be consistent for unanimous accept", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade(Review.Grade.REJECT);
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if all reviews are consistent (should be false for split decision)
        boolean result = paper.areAllReviewsConsistent();
        assertFalse("Reviews should not be consistent for split decision", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade(Review.Grade.REJECT);
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade(Review.Grade.REJECT);
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade(Review.Grade.REJECT);
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        
        Review review4 = new Review();
        review4.setGrade(Review.Grade.REJECT);
        review4.setSubmitted(true);
        review4.setReviewer(reviewer4);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are consistent (should be true for unanimous reject)
        boolean result = paper.areAllReviewsConsistent();
        assertTrue("All reviews should be consistent for unanimous reject", result);
    }
    
    @Test
    public void testCase4_mixedGradesWithUnsubmittedReview() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 submitted reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade(Review.Grade.REJECT);
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        
        // Create 1 unsubmitted review
        Review review3 = new Review();
        review3.setSubmitted(false); // Not submitted
        review3.setReviewer(reviewer3);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if all reviews are consistent (should be false due to mixed grades and unsubmitted review)
        boolean result = paper.areAllReviewsConsistent();
        assertFalse("Reviews should not be consistent with mixed grades and unsubmitted review", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade(Review.Grade.ACCEPT);
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade(Review.Grade.REJECT);
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        
        Review review4 = new Review();
        review4.setGrade(Review.Grade.REJECT);
        review4.setSubmitted(true);
        review4.setReviewer(reviewer4);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are consistent (should be false for 50-50 split)
        boolean result = paper.areAllReviewsConsistent();
        assertFalse("Reviews should not be consistent for 50-50 split decision", result);
    }
}