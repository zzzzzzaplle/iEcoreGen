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
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 and assign 3 papers without feedback and grade
        Reviewer r001 = new Reviewer();
        r001.setName("R001");
        
        // Create papers P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        p1.setTitle("P1");
        p2.setTitle("P2");
        p3.setTitle("P3");
        
        // Create reviews without feedback and grade
        Review rev1 = new Review();
        Review rev2 = new Review();
        Review rev3 = new Review();
        
        // Assign reviews to reviewer
        r001.addReview(rev1);
        r001.addReview(rev2);
        r001.addReview(rev3);
        
        // Expected output: 3 unsubmitted reviews
        assertEquals(3, r001.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 and assign 2 papers with grades=ACCEPT
        Reviewer r002 = new Reviewer();
        r002.setName("R002");
        
        // Create papers P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        p4.setTitle("P4");
        p5.setTitle("P5");
        
        // Create reviews with grades=ACCEPT
        Review rev4 = new Review();
        Review rev5 = new Review();
        rev4.setGrade("Accept");
        rev5.setGrade("Accept");
        rev4.setFeedback("Good paper");
        rev5.setFeedback("Excellent work");
        
        // Assign reviews to reviewer
        r002.addReview(rev4);
        r002.addReview(rev5);
        
        // Expected output: 0 unsubmitted reviews
        assertEquals(0, r002.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 and assign 5 papers with mixed submission status
        Reviewer r003 = new Reviewer();
        r003.setName("R003");
        
        // Create papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        p6.setTitle("P6");
        p7.setTitle("P7");
        p8.setTitle("P8");
        p9.setTitle("P9");
        p10.setTitle("P10");
        
        // Create reviews: P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        Review rev6 = new Review();
        Review rev7 = new Review();
        Review rev8 = new Review();
        Review rev9 = new Review();
        Review rev10 = new Review();
        
        rev8.setGrade("Reject");
        rev9.setGrade("Reject");
        rev10.setGrade("Reject");
        rev8.setFeedback("Needs improvement");
        rev9.setFeedback("Major flaws");
        rev10.setFeedback("Not suitable");
        
        // Assign reviews to reviewer
        r003.addReview(rev6);
        r003.addReview(rev7);
        r003.addReview(rev8);
        r003.addReview(rev9);
        r003.addReview(rev10);
        
        // Expected output: 2 unsubmitted reviews (P6 and P7)
        assertEquals(2, r003.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        r004.setName("R004");
        
        // Expected output: 0 unsubmitted reviews
        assertEquals(0, r004.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 and assign 3 papers with mixed submission status
        Reviewer r005 = new Reviewer();
        r005.setName("R005");
        
        // Create papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        p11.setTitle("P11");
        p12.setTitle("P12");
        p13.setTitle("P13");
        
        // Create reviews: P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Review rev11 = new Review();
        Review rev12 = new Review();
        Review rev13 = new Review();
        
        rev11.setGrade("Accept");
        rev11.setFeedback("Well written");
        rev13.setGrade("Reject");
        rev13.setFeedback("Insufficient data");
        // rev12 remains without grade and feedback
        
        // Assign reviews to reviewer
        r005.addReview(rev11);
        r005.addReview(rev12);
        r005.addReview(rev13);
        
        // Expected output: 1 unsubmitted review (P12)
        assertEquals(1, r005.countUnsubmittedReviews());
    }
}