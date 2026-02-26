import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private ReviewSystemService service;
    
    @Before
    public void setUp() {
        service = new ReviewSystemService();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Create Reviewer R001
        User reviewer = new User();
        reviewer.setName("R001");
        reviewer.setReviews(new ArrayList<Review>());
        
        // Create Paper P1, P2, P3
        Paper paper1 = new Paper();
        paper1.setTitle("P1");
        Paper paper2 = new Paper();
        paper2.setTitle("P2");
        Paper paper3 = new Paper();
        paper3.setTitle("P3");
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review review1 = new Review();
        review1.setReviewer(reviewer);
        review1.setPaper(paper1);
        review1.setSubmitted(false);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer);
        review2.setPaper(paper2);
        review2.setSubmitted(false);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer);
        review3.setPaper(paper3);
        review3.setSubmitted(false);
        
        reviewer.getReviews().add(review1);
        reviewer.getReviews().add(review2);
        reviewer.getReviews().add(review3);
        
        // Calculate unsubmitted reviews
        int result = service.calculateUnsubmittedReviews(reviewer);
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Create Reviewer R002
        User reviewer = new User();
        reviewer.setName("R002");
        reviewer.setReviews(new ArrayList<Review>());
        
        // Create Paper P4, P5
        Paper paper4 = new Paper();
        paper4.setTitle("P4");
        Paper paper5 = new Paper();
        paper5.setTitle("P5");
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        Review review4 = new Review();
        review4.setReviewer(reviewer);
        review4.setPaper(paper4);
        review4.setGrade(Grade.ACCEPT);
        review4.setSubmitted(true);
        
        Review review5 = new Review();
        review5.setReviewer(reviewer);
        review5.setPaper(paper5);
        review5.setGrade(Grade.ACCEPT);
        review5.setSubmitted(true);
        
        reviewer.getReviews().add(review4);
        reviewer.getReviews().add(review5);
        
        // Calculate unsubmitted reviews
        int result = service.calculateUnsubmittedReviews(reviewer);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Create Reviewer R003
        User reviewer = new User();
        reviewer.setName("R003");
        reviewer.setReviews(new ArrayList<Review>());
        
        // Create Papers P6-P10
        Paper paper6 = new Paper();
        paper6.setTitle("P6");
        Paper paper7 = new Paper();
        paper7.setTitle("P7");
        Paper paper8 = new Paper();
        paper8.setTitle("P8");
        Paper paper9 = new Paper();
        paper9.setTitle("P9");
        Paper paper10 = new Paper();
        paper10.setTitle("P10");
        
        // Assign P6/P7 to R003 without feedback and grade from R003
        Review review6 = new Review();
        review6.setReviewer(reviewer);
        review6.setPaper(paper6);
        review6.setSubmitted(false);
        
        Review review7 = new Review();
        review7.setReviewer(reviewer);
        review7.setPaper(paper7);
        review7.setSubmitted(false);
        
        // Assign P8-P10 to R003 with grade=REJECT
        Review review8 = new Review();
        review8.setReviewer(reviewer);
        review8.setPaper(paper8);
        review8.setGrade(Grade.REJECT);
        review8.setSubmitted(true);
        
        Review review9 = new Review();
        review9.setReviewer(reviewer);
        review9.setPaper(paper9);
        review9.setGrade(Grade.REJECT);
        review9.setSubmitted(true);
        
        Review review10 = new Review();
        review10.setReviewer(reviewer);
        review10.setPaper(paper10);
        review10.setGrade(Grade.REJECT);
        review10.setSubmitted(true);
        
        reviewer.getReviews().add(review6);
        reviewer.getReviews().add(review7);
        reviewer.getReviews().add(review8);
        reviewer.getReviews().add(review9);
        reviewer.getReviews().add(review10);
        
        // Calculate unsubmitted reviews
        int result = service.calculateUnsubmittedReviews(reviewer);
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Create Reviewer R004 (no assignments)
        User reviewer = new User();
        reviewer.setName("R004");
        reviewer.setReviews(new ArrayList<Review>());
        
        // Calculate unsubmitted reviews
        int result = service.calculateUnsubmittedReviews(reviewer);
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Create Reviewer R005
        User reviewer = new User();
        reviewer.setName("R005");
        reviewer.setReviews(new ArrayList<Review>());
        
        // Create Papers P11-P13
        Paper paper11 = new Paper();
        paper11.setTitle("P11");
        Paper paper12 = new Paper();
        paper12.setTitle("P12");
        Paper paper13 = new Paper();
        paper13.setTitle("P13");
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Review review11 = new Review();
        review11.setReviewer(reviewer);
        review11.setPaper(paper11);
        review11.setGrade(Grade.ACCEPT);
        review11.setSubmitted(true);
        
        Review review12 = new Review();
        review12.setReviewer(reviewer);
        review12.setPaper(paper12);
        review12.setSubmitted(false);
        
        Review review13 = new Review();
        review13.setReviewer(reviewer);
        review13.setPaper(paper13);
        review13.setGrade(Grade.REJECT);
        review13.setSubmitted(true);
        
        reviewer.getReviews().add(review11);
        reviewer.getReviews().add(review12);
        reviewer.getReviews().add(review13);
        
        // Calculate unsubmitted reviews
        int result = service.calculateUnsubmittedReviews(reviewer);
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}