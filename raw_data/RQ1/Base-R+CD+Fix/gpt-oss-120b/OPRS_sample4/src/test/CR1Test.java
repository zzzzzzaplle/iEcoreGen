import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = null;
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001, Papers P1, P2, P3, and assign without feedback/grade
        reviewer = new Reviewer();
        Paper paper1 = new Paper("Paper 1", true);
        Paper paper2 = new Paper("Paper 2", false);
        Paper paper3 = new Paper("Paper 3", true);
        
        Review review1 = new Review(reviewer, paper1);
        Review review2 = new Review(reviewer, paper2);
        Review review3 = new Review(reviewer, paper3);
        
        reviewer.assignReview(review1);
        reviewer.assignReview(review2);
        reviewer.assignReview(review3);
        
        // Input: Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002, Papers P4, P5, assign with grades=ACCEPT
        reviewer = new Reviewer();
        Paper paper4 = new Paper("Paper 4", true);
        Paper paper5 = new Paper("Paper 5", false);
        
        Review review4 = new Review(reviewer, paper4);
        Review review5 = new Review(reviewer, paper5);
        
        // Set feedback and grade for both reviews and submit them
        review4.setFeedback("Good paper");
        review4.setGrade(Review.Grade.ACCEPT);
        review4.submit();
        
        review5.setFeedback("Well written");
        review5.setGrade(Review.Grade.ACCEPT);
        review5.submit();
        
        reviewer.assignReview(review4);
        reviewer.assignReview(review5);
        
        // Input: Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003, Papers P6-P10
        reviewer = new Reviewer();
        Paper paper6 = new Paper("Paper 6", true);
        Paper paper7 = new Paper("Paper 7", false);
        Paper paper8 = new Paper("Paper 8", true);
        Paper paper9 = new Paper("Paper 9", false);
        Paper paper10 = new Paper("Paper 10", true);
        
        Review review6 = new Review(reviewer, paper6);
        Review review7 = new Review(reviewer, paper7);
        Review review8 = new Review(reviewer, paper8);
        Review review9 = new Review(reviewer, paper9);
        Review review10 = new Review(reviewer, paper10);
        
        // Assign P6/P7 without feedback and grade (unsubmitted)
        reviewer.assignReview(review6);
        reviewer.assignReview(review7);
        
        // Assign P8-P10 with grade=REJECT and submit them
        review8.setFeedback("Needs improvement");
        review8.setGrade(Review.Grade.REJECT);
        review8.submit();
        
        review9.setFeedback("Weak methodology");
        review9.setGrade(Review.Grade.REJECT);
        review9.submit();
        
        review10.setFeedback("Limited contribution");
        review10.setGrade(Review.Grade.REJECT);
        review10.submit();
        
        reviewer.assignReview(review8);
        reviewer.assignReview(review9);
        reviewer.assignReview(review10);
        
        // Input: Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        
        // Input: Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005, Papers P11-P13
        reviewer = new Reviewer();
        Paper paper11 = new Paper("Paper 11", true);
        Paper paper12 = new Paper("Paper 12", false);
        Paper paper13 = new Paper("Paper 13", true);
        
        Review review11 = new Review(reviewer, paper11);
        Review review12 = new Review(reviewer, paper12);
        Review review13 = new Review(reviewer, paper13);
        
        // Assign P11 (grade=ACCEPT) - submitted
        review11.setFeedback("Excellent work");
        review11.setGrade(Review.Grade.ACCEPT);
        review11.submit();
        
        // Assign P12 (no grade and feedback) - unsubmitted
        // P13 (grade=REJECT) - submitted
        review13.setFeedback("Poor results");
        review13.setGrade(Review.Grade.REJECT);
        review13.submit();
        
        reviewer.assignReview(review11);
        reviewer.assignReview(review12);
        reviewer.assignReview(review13);
        
        // Input: Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}