import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign P1/P2/P3 to R001 without feedback and grade
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R001");
        
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
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
        
        reviewer.addAssignedReview(review1);
        reviewer.addAssignedReview(review2);
        reviewer.addAssignedReview(review3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign P4/P5 to R002 with grades=ACCEPT
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R002");
        
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        Review review4 = new Review();
        review4.setReviewer(reviewer);
        review4.setPaper(paper4);
        review4.setGrade(Review.Grade.ACCEPT);
        review4.setSubmitted(true);
        
        Review review5 = new Review();
        review5.setReviewer(reviewer);
        review5.setPaper(paper5);
        review5.setGrade(Review.Grade.ACCEPT);
        review5.setSubmitted(true);
        
        reviewer.addAssignedReview(review4);
        reviewer.addAssignedReview(review5);
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Create Papers P6-P10, 
        // Assign P6/P7 to R003 without feedback and grade, Assign P8-P10 to R003 with grade=REJECT
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R003");
        
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // P6 and P7 - not submitted
        Review review6 = new Review();
        review6.setReviewer(reviewer);
        review6.setPaper(paper6);
        review6.setSubmitted(false);
        
        Review review7 = new Review();
        review7.setReviewer(reviewer);
        review7.setPaper(paper7);
        review7.setSubmitted(false);
        
        // P8, P9, P10 - submitted with REJECT grade
        Review review8 = new Review();
        review8.setReviewer(reviewer);
        review8.setPaper(paper8);
        review8.setGrade(Review.Grade.REJECT);
        review8.setSubmitted(true);
        
        Review review9 = new Review();
        review9.setReviewer(reviewer);
        review9.setPaper(paper9);
        review9.setGrade(Review.Grade.REJECT);
        review9.setSubmitted(true);
        
        Review review10 = new Review();
        review10.setReviewer(reviewer);
        review10.setPaper(paper10);
        review10.setGrade(Review.Grade.REJECT);
        review10.setSubmitted(true);
        
        reviewer.addAssignedReview(review6);
        reviewer.addAssignedReview(review7);
        reviewer.addAssignedReview(review8);
        reviewer.addAssignedReview(review9);
        reviewer.addAssignedReview(review10);
        
        // Expected Output: 2
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R004");
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Create Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R005");
        
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // P11 - submitted with ACCEPT grade
        Review review11 = new Review();
        review11.setReviewer(reviewer);
        review11.setPaper(paper11);
        review11.setGrade(Review.Grade.ACCEPT);
        review11.setSubmitted(true);
        
        // P12 - not submitted
        Review review12 = new Review();
        review12.setReviewer(reviewer);
        review12.setPaper(paper12);
        review12.setSubmitted(false);
        
        // P13 - submitted with REJECT grade
        Review review13 = new Review();
        review13.setReviewer(reviewer);
        review13.setPaper(paper13);
        review13.setGrade(Review.Grade.REJECT);
        review13.setSubmitted(true);
        
        reviewer.addAssignedReview(review11);
        reviewer.addAssignedReview(review12);
        reviewer.addAssignedReview(review13);
        
        // Expected Output: 1
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}