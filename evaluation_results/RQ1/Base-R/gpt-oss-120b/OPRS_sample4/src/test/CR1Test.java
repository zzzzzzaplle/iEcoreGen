import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R001");
        
        // Setup: Create Paper P1, P2, P3
        Paper paper1 = new Paper();
        paper1.setTitle("P1");
        Paper paper2 = new Paper();
        paper2.setTitle("P2");
        Paper paper3 = new Paper();
        paper3.setTitle("P3");
        
        // Setup: Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review();
        review1.setPaper(paper1);
        Review review2 = new Review();
        review2.setPaper(paper2);
        Review review3 = new Review();
        review3.setPaper(paper3);
        
        reviewer.assignReview(review1);
        reviewer.assignReview(review2);
        reviewer.assignReview(review3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R002");
        
        // Setup: Create Paper P4, P5
        Paper paper4 = new Paper();
        paper4.setTitle("P4");
        Paper paper5 = new Paper();
        paper5.setTitle("P5");
        
        // Setup: Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review();
        review4.setPaper(paper4);
        review4.submit("Good paper", Grade.ACCEPT);
        
        Review review5 = new Review();
        review5.setPaper(paper5);
        review5.submit("Excellent work", Grade.ACCEPT);
        
        reviewer.assignReview(review4);
        reviewer.assignReview(review5);
        
        // Expected Output: 0
        assertEquals(0, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R003");
        
        // Setup: Create Papers P6-P10
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
        
        // Setup: Assign P6/P7 to R003 without feedback and grade from R003
        Review review6 = new Review();
        review6.setPaper(paper6);
        Review review7 = new Review();
        review7.setPaper(paper7);
        
        // Setup: Assign P8-P10 to R003 with grade=REJECT
        Review review8 = new Review();
        review8.setPaper(paper8);
        review8.submit("Needs improvement", Grade.REJECT);
        
        Review review9 = new Review();
        review9.setPaper(paper9);
        review9.submit("Poor methodology", Grade.REJECT);
        
        Review review10 = new Review();
        review10.setPaper(paper10);
        review10.submit("Insufficient results", Grade.REJECT);
        
        reviewer.assignReview(review6);
        reviewer.assignReview(review7);
        reviewer.assignReview(review8);
        reviewer.assignReview(review9);
        reviewer.assignReview(review10);
        
        // Expected Output: 2
        assertEquals(2, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R004");
        
        // Expected Output: 0
        assertEquals(0, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R005");
        
        // Setup: Create Papers P11-P13
        Paper paper11 = new Paper();
        paper11.setTitle("P11");
        Paper paper12 = new Paper();
        paper12.setTitle("P12");
        Paper paper13 = new Paper();
        paper13.setTitle("P13");
        
        // Setup: Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Review review11 = new Review();
        review11.setPaper(paper11);
        review11.submit("Well written", Grade.ACCEPT);
        
        Review review12 = new Review();
        review12.setPaper(paper12);
        // No submission for review12
        
        Review review13 = new Review();
        review13.setPaper(paper13);
        review13.submit("Weak conclusions", Grade.REJECT);
        
        reviewer.assignReview(review11);
        reviewer.assignReview(review12);
        reviewer.assignReview(review13);
        
        // Expected Output: 1
        assertEquals(1, reviewer.getUnsubmittedReviewCount());
    }
}