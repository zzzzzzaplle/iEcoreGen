import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P14 to 3 reviewers
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        
        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer2);
        review3.setReviewer(reviewer3);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        review1.submit("revise", Grade.ACCEPT);
        review2.submit("revise", Grade.ACCEPT);
        review3.submit("revise", Grade.ACCEPT);
        
        // Test: Check consensus for Paper P14
        Chair chair = new Chair();
        boolean result = chair.canMakeFinalDecision(paper);
        
        // Expected Output: True
        assertTrue("Unanimous accept from 3 reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Assign paper P15 to 2 reviewers
        Review review1 = new Review();
        Review review2 = new Review();
        
        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer2);
        
        paper.addReview(review1);
        paper.addReview(review2);
        
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        review1.submit("good paper", Grade.ACCEPT);
        review2.submit("needs improvement", Grade.REJECT);
        
        // Test: Check consensus for Paper P15
        Chair chair = new Chair();
        boolean result = chair.canMakeFinalDecision(paper);
        
        // Expected Output: False
        assertFalse("Split decision 2-1 should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P16 to 4 reviewers
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        Review review4 = new Review();
        
        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer2);
        review3.setReviewer(reviewer3);
        review4.setReviewer(reviewer4);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // 4 reviewers give the same reviews: grade=REJECT
        review1.submit("poor methodology", Grade.REJECT);
        review2.submit("insufficient data", Grade.REJECT);
        review3.submit("weak conclusions", Grade.REJECT);
        review4.submit("not novel", Grade.REJECT);
        
        // Test: Check consensus for Paper P16
        Chair chair = new Chair();
        boolean result = chair.canMakeFinalDecision(paper);
        
        // Expected Output: True
        assertTrue("All reject with 4 reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneUnsubmitted() {
        // Setup: Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P17 to 3 reviewers
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        
        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer2);
        review3.setReviewer(reviewer3);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        review1.submit("well-written", Grade.ACCEPT);
        review2.submit("needs more experiments", Grade.REJECT);
        
        // 1 reviewer do not give feedback to the paper (review3 remains unsubmitted)
        
        // Test: Check consensus for Paper P17
        Chair chair = new Chair();
        boolean result = chair.canMakeFinalDecision(paper);
        
        // Expected Output: False
        assertFalse("Mixed grades with one unsubmitted review should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P18 to 4 reviewers
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        Review review4 = new Review();
        
        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer2);
        review3.setReviewer(reviewer3);
        review4.setReviewer(reviewer4);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        review1.submit("good contribution", Grade.ACCEPT);
        review2.submit("weak evaluation", Grade.REJECT);
        review3.submit("novel approach", Grade.ACCEPT);
        review4.submit("limited impact", Grade.REJECT);
        
        // Test: Check consensus for Paper P18
        Chair chair = new Chair();
        boolean result = chair.canMakeFinalDecision(paper);
        
        // Expected Output: False
        assertFalse("Exactly 50% acceptance should return false", result);
    }
}