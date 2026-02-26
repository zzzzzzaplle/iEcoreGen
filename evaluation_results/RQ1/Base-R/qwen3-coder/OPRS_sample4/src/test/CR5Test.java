import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 reviews with ACCEPT grades and set them as submitted
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate average review score
        double result = reviewer.calculateAverageReviewScore();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.REJECT);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate average review score
        double result = reviewer.calculateAverageReviewScore();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews added - reviewer has empty review list
        
        // Calculate average review score
        double result = reviewer.calculateAverageReviewScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 with 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Add 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade(Review.Grade.ACCEPT);
        acceptReview.setSubmitted(true);
        reviewer.addReview(acceptReview);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.REJECT);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate average review score
        double result = reviewer.calculateAverageReviewScore();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Add 1 REJECT review
        Review review = new Review();
        review.setGrade(Review.Grade.REJECT);
        review.setSubmitted(true);
        reviewer.addReview(review);
        
        // Calculate average review score
        double result = reviewer.calculateAverageReviewScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }

}