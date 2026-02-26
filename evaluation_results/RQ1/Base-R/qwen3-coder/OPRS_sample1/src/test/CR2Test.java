import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if all reviews are consistent (all ACCEPT)
        assertTrue("All reviews should be consistent (all ACCEPT)", paper.areAllReviewsConsistent());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews with different grades
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("reject");
        review2.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if all reviews are consistent (mixed ACCEPT and REJECT)
        assertFalse("Reviews should not be consistent (mixed ACCEPT and REJECT)", paper.areAllReviewsConsistent());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
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
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.REJECT);
        review1.setFeedback("reject");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("reject");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.REJECT);
        review3.setFeedback("reject");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setPaper(paper);
        review4.setReviewer(reviewer4);
        review4.setGrade(Review.Grade.REJECT);
        review4.setFeedback("reject");
        review4.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are consistent (all REJECT)
        assertTrue("All reviews should be consistent (all REJECT)", paper.areAllReviewsConsistent());
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneUnsubmitted() {
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 submitted reviews with different grades
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("reject");
        review2.setSubmitted(true);
        
        // Create 1 unsubmitted review
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.ACCEPT); // Grade set but not submitted
        review3.setFeedback("pending");
        review3.setSubmitted(false);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if all reviews are consistent (mixed grades and one unsubmitted)
        assertFalse("Reviews should not be consistent (mixed grades and unsubmitted review)", paper.areAllReviewsConsistent());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
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
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.REJECT);
        review3.setFeedback("reject");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setPaper(paper);
        review4.setReviewer(reviewer4);
        review4.setGrade(Review.Grade.REJECT);
        review4.setFeedback("reject");
        review4.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are consistent (mixed ACCEPT and REJECT)
        assertFalse("Reviews should not be consistent (50% ACCEPT, 50% REJECT)", paper.areAllReviewsConsistent());
    }
}