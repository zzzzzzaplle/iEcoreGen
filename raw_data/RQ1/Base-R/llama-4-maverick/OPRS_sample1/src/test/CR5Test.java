import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Accept");
            review.setFeedback("Excellent paper " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score and verify it's 1.00
        double result = reviewer.calculateAverageScore();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Assign 3 ACCEPT and 3 REJECT reviews to 6 papers
        for (int i = 1; i <= 6; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            
            // First 3 papers get ACCEPT, next 3 get REJECT
            if (i <= 3) {
                review.setGrade("Accept");
                review.setFeedback("Good paper " + i);
            } else {
                review.setGrade("Reject");
                review.setFeedback("Needs improvement " + i);
            }
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score and verify it's 0.50
        double result = reviewer.calculateAverageScore();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No papers or reviews assigned
        
        // Calculate average score and verify it's 0.00
        double result = reviewer.calculateAverageScore();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Assign 1 ACCEPT and 4 REJECT reviews to 5 papers
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            
            // First paper gets ACCEPT, remaining 4 get REJECT
            if (i == 1) {
                review.setGrade("Accept");
                review.setFeedback("Good paper " + i);
            } else {
                review.setGrade("Reject");
                review.setFeedback("Needs work " + i);
            }
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score and verify it's 0.20
        double result = reviewer.calculateAverageScore();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Assign 1 REJECT review to 1 paper
        Paper paper = new Paper();
        paper.setTitle("Paper 1");
        
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setGrade("Reject");
        review.setFeedback("Not suitable");
        
        paper.addReview(review);
        reviewer.addAssignedPaper(paper);
        
        // Calculate average score and verify it's 0.00
        double result = reviewer.calculateAverageScore();
        assertEquals(0.00, result, 0.001);
    }
}