package edu.conference.conference4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Reviewer;
import edu.conference.Paper;
import edu.conference.ReviewAssignment;
import edu.conference.Grade;
import org.eclipse.emf.common.util.EList;

public class CR1Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Input: Calculate unsubmitted reviews for Reviewer R001
        // Setup: Create Reviewer R001, Create Paper P1, P2, P3, Assign P1/P2/P3 to R001 without feedback and grade
        
        // Create reviewer
        Reviewer reviewer = factory.createReviewer();
        
        // Create papers and assignments without grades (UNDECIDED by default)
        for (int i = 1; i <= 3; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.UNDECIDED); // No grade submitted
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Input: Calculate unsubmitted reviews for Reviewer R002
        // Setup: Create Reviewer R002, Create Paper P4, P5, Assign P4/P5 to R002 with grades=ACCEPT
        
        // Create reviewer
        Reviewer reviewer = factory.createReviewer();
        
        // Create papers and assignments with ACCEPT grades (all submitted)
        for (int i = 4; i <= 5; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.ACCEPT); // Grade submitted
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Input: Calculate unsubmitted reviews for Reviewer R003
        // Setup: Create Reviewer R003, Create Papers P6-P10
        // Assign P6/P7 to R003 without feedback and grade, Assign P8-P10 to R003 with grade=REJECT
        
        // Create reviewer
        Reviewer reviewer = factory.createReviewer();
        
        // Create 2 assignments without grades (UNDECIDED)
        for (int i = 6; i <= 7; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.UNDECIDED); // No grade submitted
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Create 3 assignments with REJECT grades (submitted)
        for (int i = 8; i <= 10; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("P" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.REJECT); // Grade submitted
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Input: Calculate unsubmitted reviews for Reviewer R004
        // Setup: Create Reviewer R004 (no assignments)
        
        // Create reviewer with no assignments
        Reviewer reviewer = factory.createReviewer();
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Input: Calculate unsubmitted reviews for Reviewer R005
        // Setup: Create Reviewer R005, Create Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        
        // Create reviewer
        Reviewer reviewer = factory.createReviewer();
        
        // Assignment 1: P11 with ACCEPT grade (submitted)
        Paper paper1 = factory.createPaper();
        paper1.setTitle("P11");
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper1);
        assignment1.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(assignment1);
        
        // Assignment 2: P12 with UNDECIDED grade (not submitted)
        Paper paper2 = factory.createPaper();
        paper2.setTitle("P12");
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper2);
        assignment2.setGrade(Grade.UNDECIDED); // No grade submitted
        reviewer.getAssignments().add(assignment2);
        
        // Assignment 3: P13 with REJECT grade (submitted)
        Paper paper3 = factory.createPaper();
        paper3.setTitle("P13");
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper3);
        assignment3.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}