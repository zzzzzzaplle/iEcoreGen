import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<Paper> papers;
    private List<ReviewAssignment> assignments;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        papers = new ArrayList<>();
        assignments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 3 papers
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paper);
            assignment.setReview(null); // No review submitted
            
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 3 unsubmitted reviews
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with submitted reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 2 papers
        for (int i = 4; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("Good paper");
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paper);
            assignment.setReview(review); // Review submitted
            
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 without reviews, 3 with reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 5 papers
        for (int i = 6; i <= 10; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paper);
            
            // Papers P6 and P7: no review submitted
            if (i <= 7) {
                assignment.setReview(null);
            } 
            // Papers P8-P10: review submitted with REJECT grade
            else {
                Review review = new Review();
                review.setGrade(Grade.REJECT);
                review.setFeedback("Needs improvement");
                assignment.setReview(review);
            }
            
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 2 unsubmitted reviews
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - 1 without review, 2 with reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 3 papers
        for (int i = 11; i <= 13; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setReviewer(reviewer);
            assignment.setPaper(paper);
            
            // P11: review submitted with ACCEPT grade
            if (i == 11) {
                Review review = new Review();
                review.setGrade(Grade.ACCEPT);
                review.setFeedback("Excellent work");
                assignment.setReview(review);
            } 
            // P12: no review submitted
            else if (i == 12) {
                assignment.setReview(null);
            } 
            // P13: review submitted with REJECT grade
            else {
                Review review = new Review();
                review.setGrade(Grade.REJECT);
                review.setFeedback("Major issues");
                assignment.setReview(review);
            }
            
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 1 unsubmitted review
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}