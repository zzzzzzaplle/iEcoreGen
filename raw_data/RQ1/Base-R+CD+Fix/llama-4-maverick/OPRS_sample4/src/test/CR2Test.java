import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paper;
    private List<ReviewAssignment> reviews;
    
    @Before
    public void setUp() {
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers and assign paper P14 to them
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Setup: Create Paper P15
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and assign paper P15 to them
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Expected Output: False
        assertFalse("Reviews should not be all same", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers and assign paper P16 to them
        // 4 reviewers give the same reviews: grade=REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers and assign paper P17 to them
        // 2 reviewers have given feedback and grade to the paper P17: 1 ACCEPT, 1 REJECT
        // 1 reviewer do not give feedback to the paper (grade=UNDECIDED)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Default grade is UNDECIDED
        review3.setFeedback(null); // No feedback given
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Expected Output: False
        assertFalse("Reviews should not be all same due to UNDECIDED grade", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers and assign paper P18 to them
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("feedback2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Expected Output: False
        assertFalse("Reviews should not be all same with mixed ACCEPT and REJECT", paper.isAllReviewsSame());
    }
}