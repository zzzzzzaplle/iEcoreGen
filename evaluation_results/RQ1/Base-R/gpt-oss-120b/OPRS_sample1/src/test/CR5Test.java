import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades, respectively
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setSubmitted(true);
            review.setGrade(Grade.ACCEPT);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.getHistoricalGradeAverage();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers, respectively
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setSubmitted(true);
            review.setGrade(Grade.ACCEPT);
            
            reviewer.getReviews().add(review);
        }
        
        for (int i = 3; i < 6; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setSubmitted(true);
            review.setGrade(Grade.REJECT);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.getHistoricalGradeAverage();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // Calculate historical grade average
        double result = reviewer.getHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers, respectively
        Paper paper1 = new Paper();
        paper1.setTitle("Paper1");
        
        Review review1 = new Review();
        review1.setReviewer(reviewer);
        review1.setPaper(paper1);
        review1.setSubmitted(true);
        review1.setGrade(Grade.ACCEPT);
        reviewer.getReviews().add(review1);
        
        for (int i = 2; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setSubmitted(true);
            review.setGrade(Grade.REJECT);
            
            reviewer.getReviews().add(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.getHistoricalGradeAverage();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Assign 1 REJECT review to 1 paper
        Paper paper = new Paper();
        paper.setTitle("Paper1");
        
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setPaper(paper);
        review.setSubmitted(true);
        review.setGrade(Grade.REJECT);
        
        reviewer.getReviews().add(review);
        
        // Calculate historical grade average
        double result = reviewer.getHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}