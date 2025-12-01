package edu.conference.conference2.test;

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
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 3 papers
        Paper paper1 = factory.createPaper();
        Paper paper2 = factory.createPaper();
        Paper paper3 = factory.createPaper();
        
        // Create review assignments without feedback and grade (unsubmitted)
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper1);
        // No feedback or grade set - represents unsubmitted review
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper2);
        // No feedback or grade set - represents unsubmitted review
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper3);
        // No feedback or grade set - represents unsubmitted review
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment1);
        reviewer.getAssignments().add(assignment2);
        reviewer.getAssignments().add(assignment3);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 2 papers
        Paper paper4 = factory.createPaper();
        Paper paper5 = factory.createPaper();
        
        // Create review assignments with grades and feedback (submitted)
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper4);
        assignment4.setGrade(Grade.ACCEPT);
        assignment4.setFeedback("Excellent paper with strong contributions");
        
        ReviewAssignment assignment5 = factory.createReviewAssignment();
        assignment5.setPaper(paper5);
        assignment5.setGrade(Grade.ACCEPT);
        assignment5.setFeedback("Well-written and relevant to the conference");
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment4);
        reviewer.getAssignments().add(assignment5);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 5 papers (P6-P10)
        Paper paper6 = factory.createPaper();
        Paper paper7 = factory.createPaper();
        Paper paper8 = factory.createPaper();
        Paper paper9 = factory.createPaper();
        Paper paper10 = factory.createPaper();
        
        // P6 and P7: No feedback and grade (unsubmitted)
        ReviewAssignment assignment6 = factory.createReviewAssignment();
        assignment6.setPaper(paper6);
        // No feedback or grade set
        
        ReviewAssignment assignment7 = factory.createReviewAssignment();
        assignment7.setPaper(paper7);
        // No feedback or grade set
        
        // P8-P10: With grade=REJECT (submitted)
        ReviewAssignment assignment8 = factory.createReviewAssignment();
        assignment8.setPaper(paper8);
        assignment8.setGrade(Grade.REJECT);
        assignment8.setFeedback("Methodology issues identified");
        
        ReviewAssignment assignment9 = factory.createReviewAssignment();
        assignment9.setPaper(paper9);
        assignment9.setGrade(Grade.REJECT);
        assignment9.setFeedback("Lacks novelty in approach");
        
        ReviewAssignment assignment10 = factory.createReviewAssignment();
        assignment10.setPaper(paper10);
        assignment10.setGrade(Grade.REJECT);
        assignment10.setFeedback("Experimental results inconclusive");
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(assignment6);
        reviewer.getAssignments().add(assignment7);
        reviewer.getAssignments().add(assignment8);
        reviewer.getAssignments().add(assignment9);
        reviewer.getAssignments().add(assignment10);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 2 (P6 and P7 are unsubmitted)
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        // No assignments added - reviewer has empty assignment list
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
        
        // Additional verification: ensure assignments list is empty
        assertTrue("Assignments list should be empty", reviewer.getAssignments().isEmpty());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 3 papers (P11-P13)
        Paper paper11 = factory.createPaper();
        Paper paper12 = factory.createPaper();
        Paper paper13 = factory.createPaper();
        
        // P11: grade=ACCEPT (submitted)
        ReviewAssignment assignment11 = factory.createReviewAssignment();
        assignment11.setPaper(paper11);
        assignment11.setGrade(Grade.ACCEPT);
        assignment11.setFeedback("Strong theoretical foundation");
        
        // P12: no grade and feedback (unsubmitted)
        ReviewAssignment assignment12 = factory.createReviewAssignment();
        assignment12.setPaper(paper12);
        // No feedback or grade set
        
        // P13: grade=REJECT (submitted)
        ReviewAssignment assignment13 = factory.createReviewAssignment();
        assignment13.setPaper(paper13);
        assignment13.setGrade(Grade.REJECT);
        assignment13.setFeedback("Limited practical application");
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment11);
        reviewer.getAssignments().add(assignment12);
        reviewer.getAssignments().add(assignment13);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify: Expected output is 1 (only P12 is unsubmitted)
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}