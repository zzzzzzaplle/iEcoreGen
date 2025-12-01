import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades, respectively
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.addAssignedReview(review);
        }
        
        // Calculate and verify the average grade
        double result = reviewer.calculateAverageGrade();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers, respectively
        for (int i = 0; i < 3; i++) {
            Review acceptReview = new Review();
            acceptReview.setGrade(Review.Grade.ACCEPT);
            acceptReview.setSubmitted(true);
            reviewer.addAssignedReview(acceptReview);
        }
        
        for (int i = 0; i < 3; i++) {
            Review rejectReview = new Review();
            rejectReview.setGrade(Review.Grade.REJECT);
            rejectReview.setSubmitted(true);
            reviewer.addAssignedReview(rejectReview);
        }
        
        // Calculate and verify the average grade
        double result = reviewer.calculateAverageGrade();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews added - all reviews are unsubmitted by default
        
        // Calculate and verify the average grade
        double result = reviewer.calculateAverageGrade();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers, respectively
        Review acceptReview = new Review();
        acceptReview.setGrade(Review.Grade.ACCEPT);
        acceptReview.setSubmitted(true);
        reviewer.addAssignedReview(acceptReview);
        
        for (int i = 0; i < 4; i++) {
            Review rejectReview = new Review();
            rejectReview.setGrade(Review.Grade.REJECT);
            rejectReview.setSubmitted(true);
            reviewer.addAssignedReview(rejectReview);
        }
        
        // Calculate and verify the average grade
        double result = reviewer.calculateAverageGrade();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Assign 1 REJECT review to 1 paper
        Review rejectReview = new Review();
        rejectReview.setGrade(Review.Grade.REJECT);
        rejectReview.setSubmitted(true);
        reviewer.addAssignedReview(rejectReview);
        
        // Calculate and verify the average grade
        double result = reviewer.calculateAverageGrade();
        assertEquals(0.00, result, 0.001);
    }
}