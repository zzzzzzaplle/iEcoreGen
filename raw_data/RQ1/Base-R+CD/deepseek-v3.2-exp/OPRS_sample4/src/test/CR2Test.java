import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    
    @Before
    public void setUp() {
        // Initialize papers for each test case
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        paperP18 = new Paper();
        paperP18.setTitle("P18");
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments with ACCEPT grades
        for (Reviewer reviewer : reviewers) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paperP14);
            
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            assignment.setReview(review);
            
            paperP14.getReviewAssignments().add(assignment);
        }
        
        // Test: Check if reviews are unanimous
        assertTrue("All 3 reviews should be unanimous ACCEPT", paperP14.areReviewsUnanimous());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create first review assignment with ACCEPT grade
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setReviewer(reviewers.get(0));
        assignment1.setPaper(paperP15);
        
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Good paper");
        assignment1.setReview(review1);
        paperP15.getReviewAssignments().add(assignment1);
        
        // Create second review assignment with REJECT grade
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setReviewer(reviewers.get(1));
        assignment2.setPaper(paperP15);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Needs major revisions");
        assignment2.setReview(review2);
        paperP15.getReviewAssignments().add(assignment2);
        
        // Test: Check if reviews are unanimous (should be false for split decision)
        assertFalse("2-1 split decision should not be unanimous", paperP15.areReviewsUnanimous());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments with REJECT grades
        for (Reviewer reviewer : reviewers) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paperP16);
            
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Not suitable");
            assignment.setReview(review);
            
            paperP16.getReviewAssignments().add(assignment);
        }
        
        // Test: Check if reviews are unanimous
        assertTrue("All 4 REJECT reviews should be unanimous", paperP16.areReviewsUnanimous());
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneMissing() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create first review assignment with ACCEPT grade
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setReviewer(reviewers.get(0));
        assignment1.setPaper(paperP17);
        
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Acceptable");
        assignment1.setReview(review1);
        paperP17.getReviewAssignments().add(assignment1);
        
        // Create second review assignment with REJECT grade
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setReviewer(reviewers.get(1));
        assignment2.setPaper(paperP17);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Rejected");
        assignment2.setReview(review2);
        paperP17.getReviewAssignments().add(assignment2);
        
        // Create third review assignment with NO review (null)
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setReviewer(reviewers.get(2));
        assignment3.setPaper(paperP17);
        // No review set (null) - reviewer hasn't submitted feedback
        paperP17.getReviewAssignments().add(assignment3);
        
        // Test: Check if reviews are unanimous (should be false due to mixed grades)
        assertFalse("Mixed grades with one missing review should not be unanimous", paperP17.areReviewsUnanimous());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create 2 review assignments with ACCEPT grades
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewers.get(i));
            assignment.setPaper(paperP18);
            
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("Accept");
            assignment.setReview(review);
            
            paperP18.getReviewAssignments().add(assignment);
        }
        
        // Create 2 review assignments with REJECT grades
        for (int i = 2; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewers.get(i));
            assignment.setPaper(paperP18);
            
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Reject");
            assignment.setReview(review);
            
            paperP18.getReviewAssignments().add(assignment);
        }
        
        // Test: Check if reviews are unanimous (should be false for 2-2 split)
        assertFalse("50-50 split should not be unanimous", paperP18.areReviewsUnanimous());
    }
}