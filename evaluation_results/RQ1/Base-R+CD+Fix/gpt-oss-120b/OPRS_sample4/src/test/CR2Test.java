import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    private Reviewer reviewer1;
    private Reviewer reviewer2;
    private Reviewer reviewer3;
    private Reviewer reviewer4;
    private Reviewer reviewer5;
    private Reviewer reviewer6;
    private Reviewer reviewer7;
    private Reviewer reviewer8;
    private Reviewer reviewer9;
    private Reviewer reviewer10;
    private Reviewer reviewer11;
    private Reviewer reviewer12;
    private Reviewer reviewer13;
    private Reviewer reviewer14;
    
    @Before
    public void setUp() {
        // Create reviewers for all test cases
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
        reviewer6 = new Reviewer();
        reviewer7 = new Reviewer();
        reviewer8 = new Reviewer();
        reviewer9 = new Reviewer();
        reviewer10 = new Reviewer();
        reviewer11 = new Reviewer();
        reviewer12 = new Reviewer();
        reviewer13 = new Reviewer();
        reviewer14 = new Reviewer();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14
        paperP14 = new Paper("P14 Title", true);
        
        // Assign paper P14 to 3 reviewers
        Review review1 = new Review(reviewer1, paperP14);
        Review review2 = new Review(reviewer2, paperP14);
        Review review3 = new Review(reviewer3, paperP14);
        
        paperP14.addReview(review1);
        paperP14.addReview(review2);
        paperP14.addReview(review3);
        
        reviewer1.assignReview(review1);
        reviewer2.assignReview(review2);
        reviewer3.assignReview(review3);
        
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        review1.setFeedback("revise");
        review1.setGrade(Review.Grade.ACCEPT);
        review1.submit();
        
        review2.setFeedback("revise");
        review2.setGrade(Review.Grade.ACCEPT);
        review2.submit();
        
        review3.setFeedback("revise");
        review3.setGrade(Review.Grade.ACCEPT);
        review3.submit();
        
        // Check consensus for Paper P14 - Expected Output: True
        assertTrue("All reviews should be unanimous ACCEPT", paperP14.allReviewsUniformDecision());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15
        paperP15 = new Paper("P15 Title", true);
        
        // Assign paper P15 to 2 reviewers
        Review review1 = new Review(reviewer4, paperP15);
        Review review2 = new Review(reviewer5, paperP15);
        
        paperP15.addReview(review1);
        paperP15.addReview(review2);
        
        reviewer4.assignReview(review1);
        reviewer5.assignReview(review2);
        
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        review1.setFeedback("Good paper");
        review1.setGrade(Review.Grade.ACCEPT);
        review1.submit();
        
        review2.setFeedback("Needs improvement");
        review2.setGrade(Review.Grade.REJECT);
        review2.submit();
        
        // Check consensus for Paper P15 - Expected Output: False
        assertFalse("Reviews should not be unanimous with split decision", paperP15.allReviewsUniformDecision());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16
        paperP16 = new Paper("P16 Title", true);
        
        // Assign paper P16 to 4 reviewers
        Review review1 = new Review(reviewer6, paperP16);
        Review review2 = new Review(reviewer7, paperP16);
        Review review3 = new Review(reviewer8, paperP16);
        Review review4 = new Review(reviewer9, paperP16);
        
        paperP16.addReview(review1);
        paperP16.addReview(review2);
        paperP16.addReview(review3);
        paperP16.addReview(review4);
        
        reviewer6.assignReview(review1);
        reviewer7.assignReview(review2);
        reviewer8.assignReview(review3);
        reviewer9.assignReview(review4);
        
        // 4 reviewers give the same reviews: grade=REJECT
        review1.setFeedback("Poor methodology");
        review1.setGrade(Review.Grade.REJECT);
        review1.submit();
        
        review2.setFeedback("Weak results");
        review2.setGrade(Review.Grade.REJECT);
        review2.submit();
        
        review3.setFeedback("Insufficient data");
        review3.setGrade(Review.Grade.REJECT);
        review3.submit();
        
        review4.setFeedback("Not novel");
        review4.setGrade(Review.Grade.REJECT);
        review4.submit();
        
        // Check consensus for Paper P16 - Expected Output: True
        assertTrue("All reviews should be unanimous REJECT", paperP16.allReviewsUniformDecision());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17
        paperP17 = new Paper("P17 Title", true);
        
        // Assign paper P17 to 3 reviewers
        Review review1 = new Review(reviewer10, paperP17);
        Review review2 = new Review(reviewer11, paperP17);
        Review review3 = new Review(reviewer12, paperP17);
        
        paperP17.addReview(review1);
        paperP17.addReview(review2);
        paperP17.addReview(review3);
        
        reviewer10.assignReview(review1);
        reviewer11.assignReview(review2);
        reviewer12.assignReview(review3);
        
        // 2 reviewers have given feedback and grade to the paper P17: 1 ACCEPT, 1 REJECT
        review1.setFeedback("Well written");
        review1.setGrade(Review.Grade.ACCEPT);
        review1.submit();
        
        review2.setFeedback("Methodology issues");
        review2.setGrade(Review.Grade.REJECT);
        review2.submit();
        
        // 1 reviewer do not give feedback to the paper (not submitted)
        // review3 remains unsubmitted
        
        // Check consensus for Paper P17 - Expected Output: False
        assertFalse("Reviews should not be unanimous with mixed grades and unsubmitted review", 
                   paperP17.allReviewsUniformDecision());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18
        paperP18 = new Paper("P18 Title", true);
        
        // Assign paper P18 to 4 reviewers
        Review review1 = new Review(reviewer13, paperP18);
        Review review2 = new Review(reviewer14, paperP18);
        Review review3 = new Review(reviewer1, paperP18);
        Review review4 = new Review(reviewer2, paperP18);
        
        paperP18.addReview(review1);
        paperP18.addReview(review2);
        paperP18.addReview(review3);
        paperP18.addReview(review4);
        
        reviewer13.assignReview(review1);
        reviewer14.assignReview(review2);
        reviewer1.assignReview(review3);
        reviewer2.assignReview(review4);
        
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        review1.setFeedback("Good contribution");
        review1.setGrade(Review.Grade.ACCEPT);
        review1.submit();
        
        review2.setFeedback("Needs more experiments");
        review2.setGrade(Review.Grade.REJECT);
        review2.submit();
        
        review3.setFeedback("Well structured");
        review3.setGrade(Review.Grade.ACCEPT);
        review3.submit();
        
        review4.setFeedback("Limited scope");
        review4.setGrade(Review.Grade.REJECT);
        review4.submit();
        
        // Check consensus for Paper P18 - Expected Output: False
        assertFalse("Reviews should not be unanimous with 50-50 split", paperP18.allReviewsUniformDecision());
    }
}