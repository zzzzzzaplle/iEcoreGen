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
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer r001 = new Reviewer();
        r001.setName("R001");
        
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        Review review1 = new Review();
        review1.setPaper(p1);
        review1.setReviewer(r001);
        review1.setSubmitted(false);
        
        Review review2 = new Review();
        review2.setPaper(p2);
        review2.setReviewer(r001);
        review2.setSubmitted(false);
        
        Review review3 = new Review();
        review3.setPaper(p3);
        review3.setReviewer(r001);
        review3.setSubmitted(false);
        
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review1);
        assignedReviews.add(review2);
        assignedReviews.add(review3);
        r001.setAssignedReviews(assignedReviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = r001.countUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with grades=ACCEPT
        Reviewer r002 = new Reviewer();
        r002.setName("R002");
        
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        Review review4 = new Review();
        review4.setPaper(p4);
        review4.setReviewer(r002);
        review4.setGrade(Review.Grade.ACCEPT);
        review4.setSubmitted(true);
        
        Review review5 = new Review();
        review5.setPaper(p5);
        review5.setReviewer(r002);
        review5.setGrade(Review.Grade.ACCEPT);
        review5.setSubmitted(true);
        
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review4);
        assignedReviews.add(review5);
        r002.setAssignedReviews(assignedReviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = r002.countUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("All reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        // P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        Reviewer r003 = new Reviewer();
        r003.setName("R003");
        
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        Review review6 = new Review();
        review6.setPaper(p6);
        review6.setReviewer(r003);
        review6.setSubmitted(false);
        
        Review review7 = new Review();
        review7.setPaper(p7);
        review7.setReviewer(r003);
        review7.setSubmitted(false);
        
        Review review8 = new Review();
        review8.setPaper(p8);
        review8.setReviewer(r003);
        review8.setGrade(Review.Grade.REJECT);
        review8.setSubmitted(true);
        
        Review review9 = new Review();
        review9.setPaper(p9);
        review9.setReviewer(r003);
        review9.setGrade(Review.Grade.REJECT);
        review9.setSubmitted(true);
        
        Review review10 = new Review();
        review10.setPaper(p10);
        review10.setReviewer(r003);
        review10.setGrade(Review.Grade.REJECT);
        review10.setSubmitted(true);
        
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review6);
        assignedReviews.add(review7);
        assignedReviews.add(review8);
        assignedReviews.add(review9);
        assignedReviews.add(review10);
        r003.setAssignedReviews(assignedReviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = r003.countUnsubmittedReviews();
        
        // Verify: Expected output is 2
        assertEquals("Mixed submission status should return 2 unsubmitted reviews", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        r004.setName("R004");
        
        // Execute: Calculate unsubmitted reviews
        int result = r004.countUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        // P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Reviewer r005 = new Reviewer();
        r005.setName("R005");
        
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        Review review11 = new Review();
        review11.setPaper(p11);
        review11.setReviewer(r005);
        review11.setGrade(Review.Grade.ACCEPT);
        review11.setSubmitted(true);
        
        Review review12 = new Review();
        review12.setPaper(p12);
        review12.setReviewer(r005);
        review12.setSubmitted(false);
        
        Review review13 = new Review();
        review13.setPaper(p13);
        review13.setReviewer(r005);
        review13.setGrade(Review.Grade.REJECT);
        review13.setSubmitted(true);
        
        List<Review> assignedReviews = new ArrayList<>();
        assignedReviews.add(review11);
        assignedReviews.add(review12);
        assignedReviews.add(review13);
        r005.setAssignedReviews(assignedReviews);
        
        // Execute: Calculate unsubmitted reviews
        int result = r005.countUnsubmittedReviews();
        
        // Verify: Expected output is 1
        assertEquals("Partially submitted reviews should return 1 unsubmitted review", 1, result);
    }
}