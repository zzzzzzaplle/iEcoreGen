import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001
        Reviewer reviewer = new Reviewer("R001");
        
        // Setup: Create Paper P1, P2, P3
        Paper paper1 = new Paper("P1", true);
        Paper paper2 = new Paper("P2", true);
        Paper paper3 = new Paper("P3", false);
        
        // Setup: Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review(paper1);
        Review review2 = new Review(paper2);
        Review review3 = new Review(paper3);
        
        // All reviews are not submitted by default
        reviewer.addReview(review1);
        reviewer.addReview(review2);
        reviewer.addReview(review3);
        
        // Input: Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        Reviewer reviewer = new Reviewer("R002");
        
        // Setup: Create Paper P4, P5
        Paper paper4 = new Paper("P4", true);
        Paper paper5 = new Paper("P5", false);
        
        // Setup: Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review(paper4);
        review4.setGrade(Grade.Accept);
        review4.setSubmitted(true);
        
        Review review5 = new Review(paper5);
        review5.setGrade(Grade.Accept);
        review5.setSubmitted(true);
        
        reviewer.addReview(review4);
        reviewer.addReview(review5);
        
        // Input: Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        Reviewer reviewer = new Reviewer("R003");
        
        // Setup: Create Papers P6-P10
        Paper paper6 = new Paper("P6", true);
        Paper paper7 = new Paper("P7", true);
        Paper paper8 = new Paper("P8", false);
        Paper paper9 = new Paper("P9", true);
        Paper paper10 = new Paper("P10", false);
        
        // Setup: Assign P6/P7 to R003 without feedback and grade from R003
        Review review6 = new Review(paper6);
        Review review7 = new Review(paper7);
        
        // Setup: Assign P8-P10 to R003 with grade=REJECT
        Review review8 = new Review(paper8);
        review8.setGrade(Grade.Reject);
        review8.setSubmitted(true);
        
        Review review9 = new Review(paper9);
        review9.setGrade(Grade.Reject);
        review9.setSubmitted(true);
        
        Review review10 = new Review(paper10);
        review10.setGrade(Grade.Reject);
        review10.setSubmitted(true);
        
        reviewer.addReview(review6);
        reviewer.addReview(review7);
        reviewer.addReview(review8);
        reviewer.addReview(review9);
        reviewer.addReview(review10);
        
        // Input: Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer("R004");
        
        // Input: Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        Reviewer reviewer = new Reviewer("R005");
        
        // Setup: Create Papers P11-P13
        Paper paper11 = new Paper("P11", true);
        Paper paper12 = new Paper("P12", false);
        Paper paper13 = new Paper("P13", true);
        
        // Setup: Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Review review11 = new Review(paper11);
        review11.setGrade(Grade.Accept);
        review11.setSubmitted(true);
        
        Review review12 = new Review(paper12);
        // No grade set and not submitted
        
        Review review13 = new Review(paper13);
        review13.setGrade(Grade.Reject);
        review13.setSubmitted(true);
        
        reviewer.addReview(review11);
        reviewer.addReview(review12);
        reviewer.addReview(review13);
        
        // Input: Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}