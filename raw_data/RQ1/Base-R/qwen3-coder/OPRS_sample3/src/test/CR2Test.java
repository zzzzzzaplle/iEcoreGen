import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
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
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT", paper.areReviewsConsensus());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, one ACCEPT and one REJECT
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Expected Output: False
        assertFalse("Reviews should not be consensus with mixed grades", paper.areReviewsConsensus());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.REJECT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setPaper(paper);
        review4.setReviewer(reviewer4);
        review4.setGrade(Review.Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT", paper.areReviewsConsensus());
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneUnsubmitted() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 1 ACCEPT, 1 REJECT, 1 unsubmitted
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.ACCEPT); // Grade doesn't matter since not submitted
        review3.setFeedback(""); // No feedback since not submitted
        review3.setSubmitted(false);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Expected Output: False
        assertFalse("Reviews should not be consensus with mixed submitted grades", paper.areReviewsConsensus());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT, 2 REJECT
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review();
        review1.setPaper(paper);
        review1.setReviewer(reviewer1);
        review1.setGrade(Review.Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setPaper(paper);
        review2.setReviewer(reviewer2);
        review2.setGrade(Review.Grade.ACCEPT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setPaper(paper);
        review3.setReviewer(reviewer3);
        review3.setGrade(Review.Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setPaper(paper);
        review4.setReviewer(reviewer4);
        review4.setGrade(Review.Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Expected Output: False
        assertFalse("Reviews should not be consensus with 50-50 split", paper.areReviewsConsensus());
    }
}