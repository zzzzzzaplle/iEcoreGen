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
        
        Review rev1 = new Review();
        rev1.setSubmitted(false);
        Review rev2 = new Review();
        rev2.setSubmitted(false);
        Review rev3 = new Review();
        rev3.setSubmitted(false);
        
        reviewer.addReview(rev1);
        reviewer.addReview(rev2);
        reviewer.addReview(rev3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with grades=ACCEPT
        reviewer.setName("R002");
        
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        Review rev4 = new Review();
        rev4.setGrade("Accept");
        rev4.setSubmitted(true);
        Review rev5 = new Review();
        rev5.setGrade("Accept");
        rev5.setSubmitted(true);
        
        reviewer.addReview(rev4);
        reviewer.addReview(rev5);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        // P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        reviewer.setName("R003");
        
        // Papers P6 and P7 - unsubmitted reviews
        Review rev6 = new Review();
        rev6.setSubmitted(false);
        Review rev7 = new Review();
        rev7.setSubmitted(false);
        
        // Papers P8-P10 - submitted reviews with REJECT grade
        Review rev8 = new Review();
        rev8.setGrade("Reject");
        rev8.setSubmitted(true);
        Review rev9 = new Review();
        rev9.setGrade("Reject");
        rev9.setSubmitted(true);
        Review rev10 = new Review();
        rev10.setGrade("Reject");
        rev10.setSubmitted(true);
        
        reviewer.addReview(rev6);
        reviewer.addReview(rev7);
        reviewer.addReview(rev8);
        reviewer.addReview(rev9);
        reviewer.addReview(rev10);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setName("R004");
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        // P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        reviewer.setName("R005");
        
        // P11 - submitted with ACCEPT grade
        Review rev11 = new Review();
        rev11.setGrade("Accept");
        rev11.setSubmitted(true);
        
        // P12 - unsubmitted (no grade and feedback)
        Review rev12 = new Review();
        rev12.setSubmitted(false);
        
        // P13 - submitted with REJECT grade
        Review rev13 = new Review();
        rev13.setGrade("Reject");
        rev13.setSubmitted(true);
        
        reviewer.addReview(rev11);
        reviewer.addReview(rev12);
        reviewer.addReview(rev13);
        
        // Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}