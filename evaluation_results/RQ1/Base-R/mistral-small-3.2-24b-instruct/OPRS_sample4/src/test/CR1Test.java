import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer reviewer = new Reviewer("R001");
        
        Paper paper1 = new Paper("P1", true);
        Paper paper2 = new Paper("P2", true);
        Paper paper3 = new Paper("P3", true);
        
        // Create reviews without grade and feedback
        Review review1 = new Review("", "");
        Review review2 = new Review("", "");
        Review review3 = new Review("", "");
        
        paper1.addReview(review1);
        paper2.addReview(review2);
        paper3.addReview(review3);
        
        reviewer.addAssignedPaper(paper1);
        reviewer.addAssignedPaper(paper2);
        reviewer.addAssignedPaper(paper3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer reviewer = new Reviewer("R002");
        
        Paper paper4 = new Paper("P4", true);
        Paper paper5 = new Paper("P5", true);
        
        // Create reviews with ACCEPT grade
        Review review4 = new Review("Good paper", "Accept");
        Review review5 = new Review("Excellent work", "Accept");
        
        paper4.addReview(review4);
        paper5.addReview(review5);
        
        reviewer.addAssignedPaper(paper4);
        reviewer.addAssignedPaper(paper5);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        Reviewer reviewer = new Reviewer("R003");
        
        // Create 5 papers
        Paper paper6 = new Paper("P6", true);
        Paper paper7 = new Paper("P7", true);
        Paper paper8 = new Paper("P8", true);
        Paper paper9 = new Paper("P9", true);
        Paper paper10 = new Paper("P10", true);
        
        // P6 and P7: no grade and feedback (unsubmitted)
        Review review6 = new Review("", "");
        Review review7 = new Review("", "");
        
        // P8, P9, P10: with REJECT grade (submitted)
        Review review8 = new Review("Needs improvement", "Reject");
        Review review9 = new Review("Not suitable", "Reject");
        Review review10 = new Review("Poor methodology", "Reject");
        
        paper6.addReview(review6);
        paper7.addReview(review7);
        paper8.addReview(review8);
        paper9.addReview(review9);
        paper10.addReview(review10);
        
        reviewer.addAssignedPaper(paper6);
        reviewer.addAssignedPaper(paper7);
        reviewer.addAssignedPaper(paper8);
        reviewer.addAssignedPaper(paper9);
        reviewer.addAssignedPaper(paper10);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assigned papers
        Reviewer reviewer = new Reviewer("R004");
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with mixed submission status
        Reviewer reviewer = new Reviewer("R005");
        
        Paper paper11 = new Paper("P11", true);
        Paper paper12 = new Paper("P12", true);
        Paper paper13 = new Paper("P13", true);
        
        // P11: ACCEPT grade (submitted)
        Review review11 = new Review("Well written", "Accept");
        
        // P12: no grade and feedback (unsubmitted)
        Review review12 = new Review("", "");
        
        // P13: REJECT grade (submitted)
        Review review13 = new Review("Weak results", "Reject");
        
        paper11.addReview(review11);
        paper12.addReview(review12);
        paper13.addReview(review13);
        
        reviewer.addAssignedPaper(paper11);
        reviewer.addAssignedPaper(paper12);
        reviewer.addAssignedPaper(paper13);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}