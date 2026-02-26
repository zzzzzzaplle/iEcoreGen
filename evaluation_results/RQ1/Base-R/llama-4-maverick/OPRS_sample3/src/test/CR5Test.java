import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            review.setSubmitted(true);
            reviews.add(review);
        }
        reviewer.setReviews(reviews);
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            review.setSubmitted(true);
            reviews.add(review);
        }
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Reject");
            review.setSubmitted(true);
            reviews.add(review);
        }
        reviewer.setReviews(reviews);
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews assigned (empty list by default)
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        List<Review> reviews = new ArrayList<>();
        
        // Add 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade("Accept");
        acceptReview.setSubmitted(true);
        reviews.add(acceptReview);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review rejectReview = new Review();
            rejectReview.setGrade("Reject");
            rejectReview.setSubmitted(true);
            reviews.add(rejectReview);
        }
        reviewer.setReviews(reviews);
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        List<Review> reviews = new ArrayList<>();
        Review review = new Review();
        review.setGrade("Reject");
        review.setSubmitted(true);
        reviews.add(review);
        reviewer.setReviews(reviews);
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}