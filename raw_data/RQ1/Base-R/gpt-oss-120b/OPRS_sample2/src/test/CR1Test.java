import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Create Reviewer R001
        Reviewer reviewer = new Reviewer("R001");
        
        // Create Papers P1, P2, P3
        Paper paper1 = new Paper("P1", PaperType.RESEARCH);
        Paper paper2 = new Paper("P2", PaperType.EXPERIENCE_REPORT);
        Paper paper3 = new Paper("P3", PaperType.RESEARCH);
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review(reviewer);
        Review review2 = new Review(reviewer);
        Review review3 = new Review(reviewer);
        
        paper1.addReview(review1);
        paper2.addReview(review2);
        paper3.addReview(review3);
        
        reviewer.assignReview(review1);
        reviewer.assignReview(review2);
        reviewer.assignReview(review3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Create Reviewer R002
        Reviewer reviewer = new Reviewer("R002");
        
        // Create Papers P4, P5
        Paper paper4 = new Paper("P4", PaperType.RESEARCH);
        Paper paper5 = new Paper("P5", PaperType.EXPERIENCE_REPORT);
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review(reviewer);
        Review review5 = new Review(reviewer);
        
        paper4.addReview(review4);
        paper5.addReview(review5);
        
        reviewer.assignReview(review4);
        reviewer.assignReview(review5);
        
        // Submit reviews with grades=ACCEPT
        review4.submit("Good paper", Grade.ACCEPT);
        review5.submit("Excellent work", Grade.ACCEPT);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Create Reviewer R003
        Reviewer reviewer = new Reviewer("R003");
        
        // Create Papers P6-P10
        Paper paper6 = new Paper("P6", PaperType.RESEARCH);
        Paper paper7 = new Paper("P7", PaperType.EXPERIENCE_REPORT);
        Paper paper8 = new Paper("P8", PaperType.RESEARCH);
        Paper paper9 = new Paper("P9", PaperType.EXPERIENCE_REPORT);
        Paper paper10 = new Paper("P10", PaperType.RESEARCH);
        
        // Create reviews for all papers
        Review review6 = new Review(reviewer);
        Review review7 = new Review(reviewer);
        Review review8 = new Review(reviewer);
        Review review9 = new Review(reviewer);
        Review review10 = new Review(reviewer);
        
        // Add reviews to papers
        paper6.addReview(review6);
        paper7.addReview(review7);
        paper8.addReview(review8);
        paper9.addReview(review9);
        paper10.addReview(review10);
        
        // Assign all reviews to reviewer
        reviewer.assignReview(review6);
        reviewer.assignReview(review7);
        reviewer.assignReview(review8);
        reviewer.assignReview(review9);
        reviewer.assignReview(review10);
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (unsubmitted)
        // P6 and P7 remain unsubmitted (no submit() called)
        
        // Assign P8-P10 to R003 with grade=REJECT (submitted)
        review8.submit("Needs improvement", Grade.REJECT);
        review9.submit("Not suitable", Grade.REJECT);
        review10.submit("Poor methodology", Grade.REJECT);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer("R004");
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Create Reviewer R005
        Reviewer reviewer = new Reviewer("R005");
        
        // Create Papers P11-P13
        Paper paper11 = new Paper("P11", PaperType.RESEARCH);
        Paper paper12 = new Paper("P12", PaperType.EXPERIENCE_REPORT);
        Paper paper13 = new Paper("P13", PaperType.RESEARCH);
        
        // Create reviews for all papers
        Review review11 = new Review(reviewer);
        Review review12 = new Review(reviewer);
        Review review13 = new Review(reviewer);
        
        // Add reviews to papers
        paper11.addReview(review11);
        paper12.addReview(review12);
        paper13.addReview(review13);
        
        // Assign all reviews to reviewer
        reviewer.assignReview(review11);
        reviewer.assignReview(review12);
        reviewer.assignReview(review13);
        
        // Assign P11 (grade=ACCEPT) - submitted
        review11.submit("Well written", Grade.ACCEPT);
        
        // Assign P12 (no grade and feedback) - unsubmitted
        // P12 remains unsubmitted (no submit() called)
        
        // Assign P13 (grade=REJECT) - submitted
        review13.submit("Insufficient data", Grade.REJECT);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}