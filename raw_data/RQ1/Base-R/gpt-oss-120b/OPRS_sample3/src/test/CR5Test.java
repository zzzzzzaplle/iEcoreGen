import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 ACCEPT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 reviews with ACCEPT grade and mark them as submitted
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT and 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.assignReview(review);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews assigned - reviewer starts with empty review list
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 and assign 1 ACCEPT and 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setSubmitted(true);
        reviewer.assignReview(acceptReview);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Create 1 REJECT review
        Review review = new Review();
        review.setGrade(Grade.REJECT);
        review.setSubmitted(true);
        reviewer.assignReview(review);
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}