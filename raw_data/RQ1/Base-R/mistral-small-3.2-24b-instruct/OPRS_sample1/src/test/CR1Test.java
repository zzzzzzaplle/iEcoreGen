import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private ReviewSystem reviewSystem;
    
    @Before
    public void setUp() {
        reviewSystem = new ReviewSystem();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Create Reviewer R001
        User reviewer = new User();
        reviewer.setName("R001");
        
        // Create Papers P1, P2, P3
        Paper paper1 = new Paper();
        paper1.setTitle("P1");
        Paper paper2 = new Paper();
        paper2.setTitle("P2");
        Paper paper3 = new Paper();
        paper3.setTitle("P3");
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review();
        review1.setSubmitted(false); // Not submitted
        Review review2 = new Review();
        review2.setSubmitted(false); // Not submitted
        Review review3 = new Review();
        review3.setSubmitted(false); // Not submitted
        
        reviewer.addReview(review1);
        reviewer.addReview(review2);
        reviewer.addReview(review3);
        
        // Calculate unsubmitted reviews
        int result = reviewSystem.countUnsubmittedReviews(reviewer);
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Create Reviewer R002
        User reviewer = new User();
        reviewer.setName("R002");
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        paper4.setTitle("P4");
        Paper paper5 = new Paper();
        paper5.setTitle("P5");
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review();
        review4.setGrade("Accept");
        review4.setSubmitted(true); // Submitted
        Review review5 = new Review();
        review5.setGrade("Accept");
        review5.setSubmitted(true); // Submitted
        
        reviewer.addReview(review4);
        reviewer.addReview(review5);
        
        // Calculate unsubmitted reviews
        int result = reviewSystem.countUnsubmittedReviews(reviewer);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Create Reviewer R003
        User reviewer = new User();
        reviewer.setName("R003");
        
        // Create Papers P6-P10
        Paper paper6 = new Paper();
        paper6.setTitle("P6");
        Paper paper7 = new Paper();
        paper7.setTitle("P7");
        Paper paper8 = new Paper();
        paper8.setTitle("P8");
        Paper paper9 = new Paper();
        paper9.setTitle("P9");
        Paper paper10 = new Paper();
        paper10.setTitle("P10");
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (not submitted)
        Review review6 = new Review();
        review6.setSubmitted(false); // Not submitted
        Review review7 = new Review();
        review7.setSubmitted(false); // Not submitted
        
        // Assign P8-P10 to R003 with grade=REJECT (submitted)
        Review review8 = new Review();
        review8.setGrade("Reject");
        review8.setSubmitted(true); // Submitted
        Review review9 = new Review();
        review9.setGrade("Reject");
        review9.setSubmitted(true); // Submitted
        Review review10 = new Review();
        review10.setGrade("Reject");
        review10.setSubmitted(true); // Submitted
        
        reviewer.addReview(review6);
        reviewer.addReview(review7);
        reviewer.addReview(review8);
        reviewer.addReview(review9);
        reviewer.addReview(review10);
        
        // Calculate unsubmitted reviews
        int result = reviewSystem.countUnsubmittedReviews(reviewer);
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Create Reviewer R004 (no assignments)
        User reviewer = new User();
        reviewer.setName("R004");
        
        // Calculate unsubmitted reviews
        int result = reviewSystem.countUnsubmittedReviews(reviewer);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Create Reviewer R005
        User reviewer = new User();
        reviewer.setName("R005");
        
        // Create Papers P11-P13
        Paper paper11 = new Paper();
        paper11.setTitle("P11");
        Paper paper12 = new Paper();
        paper12.setTitle("P12");
        Paper paper13 = new Paper();
        paper13.setTitle("P13");
        
        // Assign P11 (grade=ACCEPT, submitted), P12 (no grade and feedback, not submitted), P13 (grade=REJECT, submitted)
        Review review11 = new Review();
        review11.setGrade("Accept");
        review11.setSubmitted(true); // Submitted
        Review review12 = new Review();
        review12.setSubmitted(false); // Not submitted
        Review review13 = new Review();
        review13.setGrade("Reject");
        review13.setSubmitted(true); // Submitted
        
        reviewer.addReview(review11);
        reviewer.addReview(review12);
        reviewer.addReview(review13);
        
        // Calculate unsubmitted reviews
        int result = reviewSystem.countUnsubmittedReviews(reviewer);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}