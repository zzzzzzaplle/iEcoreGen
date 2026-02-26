import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setName("R001");
        
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        Review r1 = new Review();
        Review r2 = new Review();
        Review r3 = new Review();
        
        // Assign reviews to reviewer (no grades set - pending reviews)
        reviewer.addSubmittedReview(r1);
        reviewer.addSubmittedReview(r2);
        reviewer.addSubmittedReview(r3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer.setName("R002");
        
        Review r1 = new Review();
        r1.setGrade("Accept");
        Review r2 = new Review();
        r2.setGrade("Accept");
        
        reviewer.addSubmittedReview(r1);
        reviewer.addSubmittedReview(r2);
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 pending, 3 with REJECT grades
        reviewer.setName("R003");
        
        // P6 and P7 - pending reviews (no grades)
        Review r1 = new Review();
        Review r2 = new Review();
        
        // P8, P9, P10 - submitted reviews with REJECT grades
        Review r3 = new Review();
        r3.setGrade("Reject");
        Review r4 = new Review();
        r4.setGrade("Reject");
        Review r5 = new Review();
        r5.setGrade("Reject");
        
        reviewer.addSubmittedReview(r1);
        reviewer.addSubmittedReview(r2);
        reviewer.addSubmittedReview(r3);
        reviewer.addSubmittedReview(r4);
        reviewer.addSubmittedReview(r5);
        
        // Expected Output: 2
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assigned papers/reviews
        reviewer.setName("R004");
        
        // Expected Output: 0
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - P11 (ACCEPT), P12 (pending), P13 (REJECT)
        reviewer.setName("R005");
        
        Review r1 = new Review();
        r1.setGrade("Accept");
        
        Review r2 = new Review();
        // No grade set - pending review
        
        Review r3 = new Review();
        r3.setGrade("Reject");
        
        reviewer.addSubmittedReview(r1);
        reviewer.addSubmittedReview(r2);
        reviewer.addSubmittedReview(r3);
        
        // Expected Output: 1
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}