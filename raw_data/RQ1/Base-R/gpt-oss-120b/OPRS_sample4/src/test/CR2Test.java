import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P14 to 3 reviewers
        Review review1 = new Review();
        review1.setPaper(paperP14);
        reviewer1.assignReview(review1);
        
        Review review2 = new Review();
        review2.setPaper(paperP14);
        reviewer2.assignReview(review2);
        
        Review review3 = new Review();
        review3.setPaper(paperP14);
        reviewer3.assignReview(review3);
        
        // Add reviews to paper
        paperP14.addReview(review1);
        paperP14.addReview(review2);
        paperP14.addReview(review3);
        
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        review1.submit("revise", Grade.ACCEPT);
        review2.submit("revise", Grade.ACCEPT);
        review3.submit("revise", Grade.ACCEPT);
        
        // Create chair and check consensus
        Chair chair = new Chair();
        boolean result = chair.areAllReviewsUniform(paperP14);
        
        // Expected Output: True
        assertTrue("All reviews should be uniform (all ACCEPT)", result);
    }
    
    @Test
    public void testCase2_splitDecision2_1() {
        // Setup: Create Paper P15
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Assign paper P15 to 2 reviewers
        Review review1 = new Review();
        review1.setPaper(paperP15);
        reviewer1.assignReview(review1);
        
        Review review2 = new Review();
        review2.setPaper(paperP15);
        reviewer2.assignReview(review2);
        
        // Add reviews to paper
        paperP15.addReview(review1);
        paperP15.addReview(review2);
        
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        review1.submit("feedback1", Grade.ACCEPT);
        review2.submit("feedback2", Grade.REJECT);
        
        // Create chair and check consensus
        Chair chair = new Chair();
        boolean result = chair.areAllReviewsUniform(paperP15);
        
        // Expected Output: False
        assertFalse("Reviews should not be uniform (mixed grades)", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P16 to 4 reviewers
        Review review1 = new Review();
        review1.setPaper(paperP16);
        reviewer1.assignReview(review1);
        
        Review review2 = new Review();
        review2.setPaper(paperP16);
        reviewer2.assignReview(review2);
        
        Review review3 = new Review();
        review3.setPaper(paperP16);
        reviewer3.assignReview(review3);
        
        Review review4 = new Review();
        review4.setPaper(paperP16);
        reviewer4.assignReview(review4);
        
        // Add reviews to paper
        paperP16.addReview(review1);
        paperP16.addReview(review2);
        paperP16.addReview(review3);
        paperP16.addReview(review4);
        
        // 4 reviewers give the same reviews: grade=REJECT
        review1.submit("feedback1", Grade.REJECT);
        review2.submit("feedback2", Grade.REJECT);
        review3.submit("feedback3", Grade.REJECT);
        review4.submit("feedback4", Grade.REJECT);
        
        // Create chair and check consensus
        Chair chair = new Chair();
        boolean result = chair.areAllReviewsUniform(paperP16);
        
        // Expected Output: True
        assertTrue("All reviews should be uniform (all REJECT)", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P17 to 3 reviewers
        Review review1 = new Review();
        review1.setPaper(paperP17);
        reviewer1.assignReview(review1);
        
        Review review2 = new Review();
        review2.setPaper(paperP17);
        reviewer2.assignReview(review2);
        
        Review review3 = new Review();
        review3.setPaper(paperP17);
        reviewer3.assignReview(review3);
        
        // Add reviews to paper
        paperP17.addReview(review1);
        paperP17.addReview(review2);
        paperP17.addReview(review3);
        
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        review1.submit("feedback1", Grade.ACCEPT);
        review2.submit("feedback2", Grade.REJECT);
        // 1 reviewer do not give feedback to the paper (review3 remains unsubmitted)
        
        // Create chair and check consensus
        Chair chair = new Chair();
        boolean result = chair.areAllReviewsUniform(paperP17);
        
        // Expected Output: False
        assertFalse("Reviews should not be uniform (mixed grades and unsubmitted review)", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P18 to 4 reviewers
        Review review1 = new Review();
        review1.setPaper(paperP18);
        reviewer1.assignReview(review1);
        
        Review review2 = new Review();
        review2.setPaper(paperP18);
        reviewer2.assignReview(review2);
        
        Review review3 = new Review();
        review3.setPaper(paperP18);
        reviewer3.assignReview(review3);
        
        Review review4 = new Review();
        review4.setPaper(paperP18);
        reviewer4.assignReview(review4);
        
        // Add reviews to paper
        paperP18.addReview(review1);
        paperP18.addReview(review2);
        paperP18.addReview(review3);
        paperP18.addReview(review4);
        
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        review1.submit("feedback1", Grade.ACCEPT);
        review2.submit("feedback2", Grade.ACCEPT);
        review3.submit("feedback3", Grade.REJECT);
        review4.submit("feedback4", Grade.REJECT);
        
        // Create chair and check consensus
        Chair chair = new Chair();
        boolean result = chair.areAllReviewsUniform(paperP18);
        
        // Expected Output: False
        assertFalse("Reviews should not be uniform (50-50 split)", result);
    }
}