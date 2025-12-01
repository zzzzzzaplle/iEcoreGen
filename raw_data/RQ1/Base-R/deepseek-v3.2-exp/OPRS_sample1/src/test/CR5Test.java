import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        User reviewer = new User();
        reviewer.setName("R006");
        
        // Create 5 papers and 5 ACCEPT reviews
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            review.setFeedback("Excellent paper " + i);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = ReviewSystem.calculateReviewerAverageScore(reviewer);
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        User reviewer = new User();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            review.setFeedback("Good paper " + i);
            
            reviewer.getReviews().add(review);
        }
        
        // Create 3 REJECT reviews
        for (int i = 4; i <= 6; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            review.setFeedback("Needs improvement " + i);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = ReviewSystem.calculateReviewerAverageScore(reviewer);
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        User reviewer = new User();
        reviewer.setName("R008");
        
        // No reviews added - empty reviews list
        
        // Calculate average score
        double result = ReviewSystem.calculateReviewerAverageScore(reviewer);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        User reviewer = new User();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Paper paper1 = new Paper();
        paper1.setTitle("Paper 1");
        
        Review review1 = new Review();
        review1.setReviewer(reviewer);
        review1.setPaper(paper1);
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setFeedback("Good paper");
        
        reviewer.getReviews().add(review1);
        
        // Create 4 REJECT reviews
        for (int i = 2; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            review.setFeedback("Rejected paper " + i);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = ReviewSystem.calculateReviewerAverageScore(reviewer);
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        User reviewer = new User();
        reviewer.setName("R010");
        
        // Create 1 REJECT review
        Paper paper = new Paper();
        paper.setTitle("Paper 1");
        
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setPaper(paper);
        review.setGrade(Grade.REJECT);
        review.setSubmitted(true);
        review.setFeedback("Poor quality");
        
        reviewer.getReviews().add(review);
        
        // Calculate average score
        double result = ReviewSystem.calculateReviewerAverageScore(reviewer);
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}