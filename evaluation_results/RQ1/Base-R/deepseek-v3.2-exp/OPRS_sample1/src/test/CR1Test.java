import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private User reviewer;
    private List<Paper> papers;
    private List<Review> reviews;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        reviewer = new User();
        papers = new ArrayList<>();
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setName("R001");
        
        // Create 3 papers
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            // Create review without setting feedback, grade, or submitted status
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            // submitted remains false by default
            reviews.add(review);
        }
        
        reviewer.setReviews(reviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = ReviewSystem.calculateUnsubmittedReviews(reviewer);
        
        // Verify: Expected output is 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with grades=ACCEPT
        reviewer.setName("R002");
        
        // Create 2 papers
        for (int i = 4; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            // Create review with grade ACCEPT and submitted=true
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            reviews.add(review);
        }
        
        reviewer.setReviews(reviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = ReviewSystem.calculateUnsubmittedReviews(reviewer);
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        reviewer.setName("R003");
        
        // Create 5 papers (P6-P10)
        for (int i = 6; i <= 10; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            
            // P6 and P7: no feedback and grade (unsubmitted)
            if (i <= 7) {
                // submitted remains false by default
            } 
            // P8-P10: grade=REJECT (submitted)
            else {
                review.setGrade(Grade.REJECT);
                review.setSubmitted(true);
            }
            
            reviews.add(review);
        }
        
        reviewer.setReviews(reviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = ReviewSystem.calculateUnsubmittedReviews(reviewer);
        
        // Verify: Expected output is 2
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setName("R004");
        reviewer.setReviews(new ArrayList<Review>()); // Empty reviews list
        
        // Execute: Calculate unsubmitted reviews
        int result = ReviewSystem.calculateUnsubmittedReviews(reviewer);
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with partially submitted reviews
        reviewer.setName("R005");
        
        // Create 3 papers (P11-P13)
        for (int i = 11; i <= 13; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setPaper(paper);
            
            // P11: grade=ACCEPT (submitted)
            if (i == 11) {
                review.setGrade(Grade.ACCEPT);
                review.setSubmitted(true);
            }
            // P12: no grade and feedback (unsubmitted)
            else if (i == 12) {
                // submitted remains false by default
            }
            // P13: grade=REJECT (submitted)
            else if (i == 13) {
                review.setGrade(Grade.REJECT);
                review.setSubmitted(true);
            }
            
            reviews.add(review);
        }
        
        reviewer.setReviews(reviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = ReviewSystem.calculateUnsubmittedReviews(reviewer);
        
        // Verify: Expected output is 1
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}