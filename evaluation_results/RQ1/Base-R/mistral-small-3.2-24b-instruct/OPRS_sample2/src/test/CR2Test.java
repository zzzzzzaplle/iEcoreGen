import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper p14 = new Paper("P14", true);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Assign paper P14 to 3 reviewers
        p14.addReviewer(reviewer1);
        p14.addReviewer(reviewer2);
        p14.addReviewer(reviewer3);
        
        // Create reviews with ACCEPT grade and 'revise' feedback
        Review review1 = new Review(p14);
        review1.setGrade(Grade.Accept);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review(p14);
        review2.setGrade(Grade.Accept);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review(p14);
        review3.setGrade(Grade.Accept);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        
        // Add reviews to reviewers
        reviewer1.addReview(review1);
        reviewer2.addReview(review2);
        reviewer3.addReview(review3);
        
        // Check consensus for Paper P14
        boolean result = p14.checkConsistentReviews();
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT - consensus should be true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Create Paper P15
        Paper p15 = new Paper("P15", true);
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        
        // Assign paper P15 to 2 reviewers
        p15.addReviewer(reviewer1);
        p15.addReviewer(reviewer2);
        
        // Create reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review(p15);
        review1.setGrade(Grade.Accept);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review(p15);
        review2.setGrade(Grade.Reject);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        // Add reviews to reviewers
        reviewer1.addReview(review1);
        reviewer2.addReview(review2);
        
        // Check consensus for Paper P15
        boolean result = p15.checkConsistentReviews();
        
        // Expected Output: False
        assertFalse("Reviews are split ACCEPT/REJECT - consensus should be false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        Paper p16 = new Paper("P16", true);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Assign paper P16 to 4 reviewers
        p16.addReviewer(reviewer1);
        p16.addReviewer(reviewer2);
        p16.addReviewer(reviewer3);
        p16.addReviewer(reviewer4);
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review(p16);
        review1.setGrade(Grade.Reject);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review(p16);
        review2.setGrade(Grade.Reject);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review(p16);
        review3.setGrade(Grade.Reject);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review(p16);
        review4.setGrade(Grade.Reject);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        // Add reviews to reviewers
        reviewer1.addReview(review1);
        reviewer2.addReview(review2);
        reviewer3.addReview(review3);
        reviewer4.addReview(review4);
        
        // Check consensus for Paper P16
        boolean result = p16.checkConsistentReviews();
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT - consensus should be true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Create Paper P17
        Paper p17 = new Paper("P17", true);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Assign paper P17 to 3 reviewers
        p17.addReviewer(reviewer1);
        p17.addReviewer(reviewer2);
        p17.addReviewer(reviewer3);
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT (1 reviewer doesn't give feedback)
        Review review1 = new Review(p17);
        review1.setGrade(Grade.Accept);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review(p17);
        review2.setGrade(Grade.Reject);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        // Reviewer3 doesn't submit review (no review created)
        
        // Add reviews to reviewers
        reviewer1.addReview(review1);
        reviewer2.addReview(review2);
        // reviewer3 has no review added
        
        // Check consensus for Paper P17
        boolean result = p17.checkConsistentReviews();
        
        // Expected Output: False
        assertFalse("Mixed grades and missing review - consensus should be false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper p18 = new Paper("P18", true);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Assign paper P18 to 4 reviewers
        p18.addReviewer(reviewer1);
        p18.addReviewer(reviewer2);
        p18.addReviewer(reviewer3);
        p18.addReviewer(reviewer4);
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review(p18);
        review1.setGrade(Grade.Accept);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review(p18);
        review2.setGrade(Grade.Accept);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review(p18);
        review3.setGrade(Grade.Reject);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review(p18);
        review4.setGrade(Grade.Reject);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        // Add reviews to reviewers
        reviewer1.addReview(review1);
        reviewer2.addReview(review2);
        reviewer3.addReview(review3);
        reviewer4.addReview(review4);
        
        // Check consensus for Paper P18
        boolean result = p18.checkConsistentReviews();
        
        // Expected Output: False
        assertFalse("50% ACCEPT, 50% REJECT - consensus should be false", result);
    }
}