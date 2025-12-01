import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper("P14", PaperType.RESEARCH);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Assign paper P14 to 3 reviewers
        paper.addAssignedReviewer(reviewer1);
        paper.addAssignedReviewer(reviewer2);
        paper.addAssignedReviewer(reviewer3);
        
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        Review review1 = new Review(reviewer1, paper, "revise", Grade.ACCEPT);
        Review review2 = new Review(reviewer2, paper, "revise", Grade.ACCEPT);
        Review review3 = new Review(reviewer3, paper, "revise", Grade.ACCEPT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if all reviews are uniform
        boolean result = paper.areReviewsUniform();
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT, so result should be true", result);
    }
    
    @Test
    public void testCase2_splitDecision2To1() {
        // Create Paper P15
        Paper paper = new Paper("P15", PaperType.RESEARCH);
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        
        // Assign paper P15 to 2 reviewers
        paper.addAssignedReviewer(reviewer1);
        paper.addAssignedReviewer(reviewer2);
        
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        Review review1 = new Review(reviewer1, paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(reviewer2, paper, "feedback2", Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if all reviews are uniform
        boolean result = paper.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Reviews are mixed (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        Paper paper = new Paper("P16", PaperType.RESEARCH);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Assign paper P16 to 4 reviewers
        paper.addAssignedReviewer(reviewer1);
        paper.addAssignedReviewer(reviewer2);
        paper.addAssignedReviewer(reviewer3);
        paper.addAssignedReviewer(reviewer4);
        
        // 4 reviewers give the same reviews: grade=REJECT
        Review review1 = new Review(reviewer1, paper, "feedback1", Grade.REJECT);
        Review review2 = new Review(reviewer2, paper, "feedback2", Grade.REJECT);
        Review review3 = new Review(reviewer3, paper, "feedback3", Grade.REJECT);
        Review review4 = new Review(reviewer4, paper, "feedback4", Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are uniform
        boolean result = paper.areReviewsUniform();
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT, so result should be true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = new Paper("P17", PaperType.RESEARCH);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Assign paper P17 to 3 reviewers
        paper.addAssignedReviewer(reviewer1);
        paper.addAssignedReviewer(reviewer2);
        paper.addAssignedReviewer(reviewer3);
        
        // 2 reviewers have given feedback and grade to the paper P17: 1 ACCEPT, 1 REJECT
        Review review1 = new Review(reviewer1, paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(reviewer2, paper, "feedback2", Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        // 1 reviewer do not give feedback to the paper (no review added)
        
        // Check if all reviews are uniform
        boolean result = paper.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Reviews are mixed (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = new Paper("P18", PaperType.RESEARCH);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Assign paper P18 to 4 reviewers
        paper.addAssignedReviewer(reviewer1);
        paper.addAssignedReviewer(reviewer2);
        paper.addAssignedReviewer(reviewer3);
        paper.addAssignedReviewer(reviewer4);
        
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review(reviewer1, paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(reviewer2, paper, "feedback2", Grade.ACCEPT);
        Review review3 = new Review(reviewer3, paper, "feedback3", Grade.REJECT);
        Review review4 = new Review(reviewer4, paper, "feedback4", Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if all reviews are uniform
        boolean result = paper.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Reviews are mixed (2 ACCEPT and 2 REJECT), so result should be false", result);
    }
}