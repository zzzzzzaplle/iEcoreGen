import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Chair chair;
    
    @Before
    public void setUp() {
        chair = new Chair("Conference Chair");
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper("P14", true);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Create 3 reviews with ACCEPT grade and 'revise' feedback
        Review review1 = new Review(paper, "revise", Grade.ACCEPT);
        Review review2 = new Review(paper, "revise", Grade.ACCEPT);
        Review review3 = new Review(paper, "revise", Grade.ACCEPT);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus
        boolean result = chair.checkReviewConsistency(paper);
        
        // Verify result is true (all reviews are ACCEPT)
        assertTrue("All reviews should be ACCEPT - expected true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Create Paper P15
        Paper paper = new Paper("P15", true);
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT
        Review review1 = new Review(paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(paper, "feedback2", Grade.REJECT);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check consensus
        boolean result = chair.checkReviewConsistency(paper);
        
        // Verify result is false (reviews are mixed)
        assertFalse("Reviews are mixed (ACCEPT and REJECT) - expected false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        Paper paper = new Paper("P16", true);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review(paper, "feedback1", Grade.REJECT);
        Review review2 = new Review(paper, "feedback2", Grade.REJECT);
        Review review3 = new Review(paper, "feedback3", Grade.REJECT);
        Review review4 = new Review(paper, "feedback4", Grade.REJECT);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus
        boolean result = chair.checkReviewConsistency(paper);
        
        // Verify result is true (all reviews are REJECT)
        assertTrue("All reviews should be REJECT - expected true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = new Paper("P17", true);
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT (1 reviewer didn't submit feedback)
        Review review1 = new Review(paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(paper, "feedback2", Grade.REJECT);
        Review review3 = new Review(paper, "", null); // Reviewer didn't give feedback/grade
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus
        boolean result = chair.checkReviewConsistency(paper);
        
        // Verify result is false (reviews are mixed and one has null grade)
        assertFalse("Reviews are mixed and one has null grade - expected false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = new Paper("P18", true);
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer("Reviewer1");
        Reviewer reviewer2 = new Reviewer("Reviewer2");
        Reviewer reviewer3 = new Reviewer("Reviewer3");
        Reviewer reviewer4 = new Reviewer("Reviewer4");
        
        // Create 4 reviews: 2 ACCEPT and 2 REJECT
        Review review1 = new Review(paper, "feedback1", Grade.ACCEPT);
        Review review2 = new Review(paper, "feedback2", Grade.ACCEPT);
        Review review3 = new Review(paper, "feedback3", Grade.REJECT);
        Review review4 = new Review(paper, "feedback4", Grade.REJECT);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus
        boolean result = chair.checkReviewConsistency(paper);
        
        // Verify result is false (reviews are split 2-2)
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT - expected false", result);
    }
}