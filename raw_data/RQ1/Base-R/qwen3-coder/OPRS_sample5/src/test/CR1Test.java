import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Initialize reviewer before each test
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_reviewerWithThreePendingReviews() {
        // Test Case 1: Reviewer with 3 pending reviews
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        
        // Create papers
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Create reviews without submitting them
        Review review1 = new Review();
        review1.setReviewer(reviewer);
        review1.setPaper(paper1);
        review1.setSubmitted(false);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer);
        review2.setPaper(paper2);
        review2.setSubmitted(false);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer);
        review3.setPaper(paper3);
        review3.setSubmitted(false);
        
        // Assign reviews to reviewer
        reviewer.addReview(review1);
        reviewer.addReview(review2);
        reviewer.addReview(review3);
        
        // Verify expected output: 3 unsubmitted reviews
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_allReviewsSubmitted() {
        // Test Case 2: All reviews submitted
        // Setup: Create Reviewer R002, Create Paper P4, P5
        // Assign P4/P5 to R002 with grades=ACCEPT
        
        // Create papers
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Create reviews and submit them with ACCEPT grade
        Review review4 = new Review();
        review4.setReviewer(reviewer);
        review4.setPaper(paper4);
        review4.setGrade(Grade.ACCEPT);
        review4.setSubmitted(true);
        
        Review review5 = new Review();
        review5.setReviewer(reviewer);
        review5.setPaper(paper5);
        review5.setGrade(Grade.ACCEPT);
        review5.setSubmitted(true);
        
        // Assign reviews to reviewer
        reviewer.addReview(review4);
        reviewer.addReview(review5);
        
        // Verify expected output: 0 unsubmitted reviews
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_mixedSubmissionStatus() {
        // Test Case 3: Mixed submission status
        // Setup: Create Reviewer R003, Create Papers P6-P10
        // Assign P6/P7 to R003 without feedback and grade from R003
        // Assign P8-P10 to R003 with grade=REJECT
        
        // Create papers
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Create unsubmitted reviews for P6 and P7
        Review review6 = new Review();
        review6.setReviewer(reviewer);
        review6.setPaper(paper6);
        review6.setSubmitted(false);
        
        Review review7 = new Review();
        review7.setReviewer(reviewer);
        review7.setPaper(paper7);
        review7.setSubmitted(false);
        
        // Create submitted reviews with REJECT grade for P8-P10
        Review review8 = new Review();
        review8.setReviewer(reviewer);
        review8.setPaper(paper8);
        review8.setGrade(Grade.REJECT);
        review8.setSubmitted(true);
        
        Review review9 = new Review();
        review9.setReviewer(reviewer);
        review9.setPaper(paper9);
        review9.setGrade(Grade.REJECT);
        review9.setSubmitted(true);
        
        Review review10 = new Review();
        review10.setReviewer(reviewer);
        review10.setPaper(paper10);
        review10.setGrade(Grade.REJECT);
        review10.setSubmitted(true);
        
        // Assign all reviews to reviewer
        reviewer.addReview(review6);
        reviewer.addReview(review7);
        reviewer.addReview(review8);
        reviewer.addReview(review9);
        reviewer.addReview(review10);
        
        // Verify expected output: 2 unsubmitted reviews (P6 and P7)
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_noAssignedPapers() {
        // Test Case 4: No assigned papers
        // Setup: Create Reviewer R004 (no assignments)
        
        // Reviewer has no reviews assigned by default
        // Verify expected output: 0 unsubmitted reviews
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_partiallySubmittedReviews() {
        // Test Case 5: Partially submitted reviews
        // Setup: Create Reviewer R005, Create Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        
        // Create papers
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Create submitted review with ACCEPT grade for P11
        Review review11 = new Review();
        review11.setReviewer(reviewer);
        review11.setPaper(paper11);
        review11.setGrade(Grade.ACCEPT);
        review11.setSubmitted(true);
        
        // Create unsubmitted review for P12
        Review review12 = new Review();
        review12.setReviewer(reviewer);
        review12.setPaper(paper12);
        review12.setSubmitted(false);
        
        // Create submitted review with REJECT grade for P13
        Review review13 = new Review();
        review13.setReviewer(reviewer);
        review13.setPaper(paper13);
        review13.setGrade(Grade.REJECT);
        review13.setSubmitted(true);
        
        // Assign all reviews to reviewer
        reviewer.addReview(review11);
        reviewer.addReview(review12);
        reviewer.addReview(review13);
        
        // Verify expected output: 1 unsubmitted review (P12)
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}