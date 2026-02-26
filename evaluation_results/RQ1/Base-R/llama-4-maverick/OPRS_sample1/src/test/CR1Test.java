import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P1, P2, P3
        Paper paper1 = new Paper();
        Paper paper2 = new Paper();
        Paper paper3 = new Paper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review();
        review1.setReviewer(reviewer);
        review1.setFeedback(null);
        review1.setGrade(null);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer);
        review2.setFeedback(null);
        review2.setGrade(null);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer);
        review3.setFeedback(null);
        review3.setGrade(null);
        
        paper1.addReview(review1);
        paper2.addReview(review2);
        paper3.addReview(review3);
        
        reviewer.addAssignedPaper(paper1);
        reviewer.addAssignedPaper(paper2);
        reviewer.addAssignedPaper(paper3);
        
        // Execute: Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        Reviewer reviewer = new Reviewer();
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        Paper paper5 = new Paper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review();
        review4.setReviewer(reviewer);
        review4.setFeedback("Good paper");
        review4.setGrade("Accept");
        
        Review review5 = new Review();
        review5.setReviewer(reviewer);
        review5.setFeedback("Excellent work");
        review5.setGrade("Accept");
        
        paper4.addReview(review4);
        paper5.addReview(review5);
        
        reviewer.addAssignedPaper(paper4);
        reviewer.addAssignedPaper(paper5);
        
        // Execute: Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P6-P10
        Paper paper6 = new Paper();
        Paper paper7 = new Paper();
        Paper paper8 = new Paper();
        Paper paper9 = new Paper();
        Paper paper10 = new Paper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003
        Review review6 = new Review();
        review6.setReviewer(reviewer);
        review6.setFeedback(null);
        review6.setGrade(null);
        
        Review review7 = new Review();
        review7.setReviewer(reviewer);
        review7.setFeedback(null);
        review7.setGrade(null);
        
        // Assign P8-P10 to R003 with grade=REJECT
        Review review8 = new Review();
        review8.setReviewer(reviewer);
        review8.setFeedback("Needs improvement");
        review8.setGrade("Reject");
        
        Review review9 = new Review();
        review9.setReviewer(reviewer);
        review9.setFeedback("Not suitable");
        review9.setGrade("Reject");
        
        Review review10 = new Review();
        review10.setReviewer(reviewer);
        review10.setFeedback("Poor methodology");
        review10.setGrade("Reject");
        
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
        
        // Execute: Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // Execute: Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        Reviewer reviewer = new Reviewer();
        
        // Create Papers P11-P13
        Paper paper11 = new Paper();
        Paper paper12 = new Paper();
        Paper paper13 = new Paper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Review review11 = new Review();
        review11.setReviewer(reviewer);
        review11.setFeedback("Well written");
        review11.setGrade("Accept");
        
        Review review12 = new Review();
        review12.setReviewer(reviewer);
        review12.setFeedback(null);
        review12.setGrade(null);
        
        Review review13 = new Review();
        review13.setReviewer(reviewer);
        review13.setFeedback("Major flaws");
        review13.setGrade("Reject");
        
        paper11.addReview(review11);
        paper12.addReview(review12);
        paper13.addReview(review13);
        
        reviewer.addAssignedPaper(paper11);
        reviewer.addAssignedPaper(paper12);
        reviewer.addAssignedPaper(paper13);
        
        // Execute: Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected Output: 1
        assertEquals(1, result);
    }
}