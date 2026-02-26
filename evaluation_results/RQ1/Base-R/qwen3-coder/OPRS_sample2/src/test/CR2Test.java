import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup
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
        
        // Verify expected output
        assertTrue("All reviews should be unanimous ACCEPT", paper.areAllReviewsUnanimous());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup
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
        
        // Verify expected output
        assertFalse("Split decision should not be unanimous", paper.areAllReviewsUnanimous());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup
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
        
        // Verify expected output
        assertTrue("All reviews should be unanimous REJECT", paper.areAllReviewsUnanimous());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup
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
        review3.setGrade(Review.Grade.ACCEPT); // Grade doesn't matter since not submitted
        review3.setSubmitted(false);
        review3.setReviewer(reviewer3);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Verify expected output
        assertFalse("Mixed grades should not be unanimous", paper.areAllReviewsUnanimous());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup
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
        
        // Verify expected output
        assertFalse("50-50 split should not be unanimous", paper.areAllReviewsUnanimous());
    }
}