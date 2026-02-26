import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 ACCEPT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 reviews with ACCEPT grade
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            reviewer.addSubmittedReview(review);
        }
        
        // Expected Output: 1.00
        double expected = 1.00;
        double actual = reviewer.calculateAverageGradeScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            reviewer.addSubmittedReview(review);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Reject");
            reviewer.addSubmittedReview(review);
        }
        
        // Expected Output: 0.50
        double expected = 0.50;
        double actual = reviewer.calculateAverageGradeScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews added - empty list
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.calculateAverageGradeScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Add 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade("Accept");
        reviewer.addSubmittedReview(acceptReview);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade("Reject");
            reviewer.addSubmittedReview(review);
        }
        
        // Expected Output: 0.20
        double expected = 0.20;
        double actual = reviewer.calculateAverageGradeScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Add 1 REJECT review
        Review review = new Review();
        review.setGrade("Reject");
        reviewer.addSubmittedReview(review);
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.calculateAverageGradeScore();
        assertEquals(expected, actual, 0.001);
    }
}