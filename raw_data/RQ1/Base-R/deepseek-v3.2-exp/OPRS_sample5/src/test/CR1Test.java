import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer1;
    private Reviewer reviewer2;
    private Reviewer reviewer3;
    private Reviewer reviewer4;
    private Reviewer reviewer5;
    
    @Before
    public void setUp() {
        // Initialize reviewers for all test cases
        reviewer1 = new Reviewer();
        reviewer2 = new Reviewer();
        reviewer3 = new Reviewer();
        reviewer4 = new Reviewer();
        reviewer5 = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 pending reviews
        Reviewer r001 = reviewer1;
        r001.setName("R001");
        
        // Create Papers P1, P2, P3
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create reviews without setting feedback, grade, and submitted=false by default
        Review review1 = new Review();
        review1.setPaper(p1);
        review1.setReviewer(r001);
        review1.setSubmitted(false); // Not submitted
        
        Review review2 = new Review();
        review2.setPaper(p2);
        review2.setReviewer(r001);
        review2.setSubmitted(false); // Not submitted
        
        Review review3 = new Review();
        review3.setPaper(p3);
        review3.setReviewer(r001);
        review3.setSubmitted(false); // Not submitted
        
        // Assign reviews to reviewer
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review1);
        assignedReviews.add(review2);
        assignedReviews.add(review3);
        r001.setAssignedReviews(assignedReviews);
        
        // Expected Output: 3
        int result = r001.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with all reviews submitted
        Reviewer r002 = reviewer2;
        r002.setName("R002");
        
        // Create Papers P4, P5
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        // Create reviews with grades=ACCEPT and submitted=true
        Review review4 = new Review();
        review4.setPaper(p4);
        review4.setReviewer(r002);
        review4.setGrade(Grade.ACCEPT);
        review4.setSubmitted(true); // Submitted
        
        Review review5 = new Review();
        review5.setPaper(p5);
        review5.setReviewer(r002);
        review5.setGrade(Grade.ACCEPT);
        review5.setSubmitted(true); // Submitted
        
        // Assign reviews to reviewer
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review4);
        assignedReviews.add(review5);
        r002.setAssignedReviews(assignedReviews);
        
        // Expected Output: 0
        int result = r002.calculateUnsubmittedReviews();
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with mixed submission status
        Reviewer r003 = reviewer3;
        r003.setName("R003");
        
        // Create Papers P6-P10
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // Assign P6/P7 without feedback and grade (not submitted)
        Review review6 = new Review();
        review6.setPaper(p6);
        review6.setReviewer(r003);
        review6.setSubmitted(false); // Not submitted
        
        Review review7 = new Review();
        review7.setPaper(p7);
        review7.setReviewer(r003);
        review7.setSubmitted(false); // Not submitted
        
        // Assign P8-P10 with grade=REJECT and submitted=true
        Review review8 = new Review();
        review8.setPaper(p8);
        review8.setReviewer(r003);
        review8.setGrade(Grade.REJECT);
        review8.setSubmitted(true); // Submitted
        
        Review review9 = new Review();
        review9.setPaper(p9);
        review9.setReviewer(r003);
        review9.setGrade(Grade.REJECT);
        review9.setSubmitted(true); // Submitted
        
        Review review10 = new Review();
        review10.setPaper(p10);
        review10.setReviewer(r003);
        review10.setGrade(Grade.REJECT);
        review10.setSubmitted(true); // Submitted
        
        // Assign all reviews to reviewer
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review6);
        assignedReviews.add(review7);
        assignedReviews.add(review8);
        assignedReviews.add(review9);
        assignedReviews.add(review10);
        r003.setAssignedReviews(assignedReviews);
        
        // Expected Output: 2
        int result = r003.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = reviewer4;
        r004.setName("R004");
        
        // No papers assigned - empty list by default constructor
        
        // Expected Output: 0
        int result = r004.calculateUnsubmittedReviews();
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with partially submitted reviews
        Reviewer r005 = reviewer5;
        r005.setName("R005");
        
        // Create Papers P11-P13
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // Assign P11 (grade=ACCEPT, submitted=true)
        Review review11 = new Review();
        review11.setPaper(p11);
        review11.setReviewer(r005);
        review11.setGrade(Grade.ACCEPT);
        review11.setSubmitted(true); // Submitted
        
        // Assign P12 (no grade and feedback, submitted=false)
        Review review12 = new Review();
        review12.setPaper(p12);
        review12.setReviewer(r005);
        review12.setSubmitted(false); // Not submitted
        
        // Assign P13 (grade=REJECT, submitted=true)
        Review review13 = new Review();
        review13.setPaper(p13);
        review13.setReviewer(r005);
        review13.setGrade(Grade.REJECT);
        review13.setSubmitted(true); // Submitted
        
        // Assign reviews to reviewer
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review11);
        assignedReviews.add(review12);
        assignedReviews.add(review13);
        r005.setAssignedReviews(assignedReviews);
        
        // Expected Output: 1
        int result = r005.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}