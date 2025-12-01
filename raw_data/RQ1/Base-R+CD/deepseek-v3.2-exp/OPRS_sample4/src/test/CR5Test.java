import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            assignment.setReview(review);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            assignment.setReview(review);
            assignments.add(assignment);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            assignment.setReview(review);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add assignments without reviews (unsubmitted)
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            // No review set (null)
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        Review acceptReview = new Review();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptAssignment.setReview(acceptReview);
        assignments.add(acceptAssignment);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            assignment.setReview(review);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        Review review = new Review();
        review.setGrade(Grade.REJECT);
        assignment.setReview(review);
        assignments.add(assignment);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}