import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign P1/P2/P3 to R001 without feedback and grade
        Reviewer reviewer = new Reviewer();
        
        // Create 3 papers and assign them to reviewer without setting grades (default UNDECIDED)
        List<ReviewAssignment> assignments = new ArrayList<>();
        assignments.add(new ReviewAssignment(null)); // P1 - no feedback, grade=UNDECIDED
        assignments.add(new ReviewAssignment(null)); // P2 - no feedback, grade=UNDECIDED
        assignments.add(new ReviewAssignment(null)); // P3 - no feedback, grade=UNDECIDED
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign P4/P5 to R002 with grades=ACCEPT
        Reviewer reviewer = new Reviewer();
        
        // Create 2 papers and assign them to reviewer with ACCEPT grades
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        ReviewAssignment assignment1 = new ReviewAssignment("Good paper");
        assignment1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment2 = new ReviewAssignment("Excellent work");
        assignment2.setGrade(Grade.ACCEPT);
        
        assignments.add(assignment1); // P4 - grade=ACCEPT
        assignments.add(assignment2); // P5 - grade=ACCEPT
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Create Papers P6-P10, 
        // Assign P6/P7 without feedback and grade, Assign P8-P10 with grade=REJECT
        Reviewer reviewer = new Reviewer();
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P6 and P7 - no feedback, grade=UNDECIDED (unsubmitted)
        assignments.add(new ReviewAssignment(null));
        assignments.add(new ReviewAssignment(null));
        
        // P8, P9, P10 - with REJECT grades (submitted)
        ReviewAssignment assignment3 = new ReviewAssignment("Needs improvement");
        assignment3.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment4 = new ReviewAssignment("Major flaws");
        assignment4.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment5 = new ReviewAssignment("Not suitable");
        assignment5.setGrade(Grade.REJECT);
        
        assignments.add(assignment3);
        assignments.add(assignment4);
        assignments.add(assignment5);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        Reviewer reviewer = new Reviewer();
        
        // No assignments set - reviewer has empty assignments list by default
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Create Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        Reviewer reviewer = new Reviewer();
        
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P11 - grade=ACCEPT (submitted)
        ReviewAssignment assignment1 = new ReviewAssignment("Well written");
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        
        // P12 - no feedback, grade=UNDECIDED (unsubmitted)
        assignments.add(new ReviewAssignment(null));
        
        // P13 - grade=REJECT (submitted)
        ReviewAssignment assignment3 = new ReviewAssignment("Needs major revision");
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}