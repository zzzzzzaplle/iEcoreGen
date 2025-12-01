import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers and their reviews
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        // Assign reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        
        // Check consensus and verify expected output
        assertTrue("All reviews should be ACCEPT - consensus should be true", 
                   paper.isAllReviewsConsistent());
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and their reviews (as per spec, though title says 2-1 split but setup says 2 reviewers)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        // Assign reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        
        // Check consensus and verify expected output
        assertFalse("Mixed ACCEPT and REJECT reviews - consensus should be false", 
                    paper.isAllReviewsConsistent());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers with REJECT grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Assign reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        paper.getReviews().add(review4);
        
        // Check consensus and verify expected output
        assertTrue("All reviews should be REJECT - consensus should be true", 
                   paper.isAllReviewsConsistent());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers - 2 with grades, 1 without grade (UNDECIDED)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer who hasn't given feedback
        
        // Assign reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        
        // Check consensus and verify expected output
        assertFalse("Mixed ACCEPT and REJECT reviews - consensus should be false", 
                    paper.isAllReviewsConsistent());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers - 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Assign reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        paper.getReviews().add(review4);
        
        // Check consensus and verify expected output
        assertFalse("50% ACCEPT and 50% REJECT - consensus should be false", 
                    paper.isAllReviewsConsistent());
    }
}