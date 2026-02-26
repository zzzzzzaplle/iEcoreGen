import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 ACCEPT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 papers and assign ACCEPT reviews
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + (i + 1));
            
            Review review = new Review();
            review.setPaper(paper);
            review.submit("Excellent paper", Grade.ACCEPT);
            
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Verify: All acceptances should yield 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper ACCEPT " + (i + 1));
            
            Review review = new Review();
            review.setPaper(paper);
            review.submit("Good paper", Grade.ACCEPT);
            
            reviewer.assignReview(review);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper REJECT " + (i + 1));
            
            Review review = new Review();
            review.setPaper(paper);
            review.submit("Needs improvement", Grade.REJECT);
            
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Verify: 3 ACCEPT (3 points) and 3 REJECT (0 points) = 3/6 = 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews assigned or submitted
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Verify: No submitted reviews should yield 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Paper paper1 = new Paper();
        paper1.setTitle("Paper ACCEPT 1");
        Review review1 = new Review();
        review1.setPaper(paper1);
        review1.submit("Good work", Grade.ACCEPT);
        reviewer.assignReview(review1);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper REJECT " + (i + 1));
            
            Review review = new Review();
            review.setPaper(paper);
            review.submit("Not suitable", Grade.REJECT);
            
            reviewer.assignReview(review);
        }
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Verify: 1 ACCEPT (1 point) and 4 REJECT (0 points) = 1/5 = 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        Paper paper = new Paper();
        paper.setTitle("Single Paper");
        
        Review review = new Review();
        review.setPaper(paper);
        review.submit("Needs major revisions", Grade.REJECT);
        
        reviewer.assignReview(review);
        
        // Calculate historical average score
        double result = reviewer.getHistoricalAverageScore();
        
        // Verify: Single REJECT review should yield 0.00
        assertEquals(0.00, result, 0.001);
    }
}