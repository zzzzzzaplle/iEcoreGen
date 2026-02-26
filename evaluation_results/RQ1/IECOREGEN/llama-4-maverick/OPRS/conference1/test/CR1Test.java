package edu.conference.conference1.test;

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
        
        // Execute and Verify
        int result = reviewer.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        Paper paper4 = factory.createPaper();
        Paper paper5 = factory.createPaper();
        
        // Create review assignments with feedback and grade (submitted)
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper4);
        assignment4.setFeedback("Good paper");
        assignment4.setGrade(Grade.ACCEPT);
        
        ReviewAssignment assignment5 = factory.createReviewAssignment();
        assignment5.setPaper(paper5);
        assignment5.setFeedback("Excellent work");
        assignment5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment4);
        reviewer.getAssignments().add(assignment5);
        
        // Execute and Verify
        int result = reviewer.calculateUnsubmittedReviews();
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 5 papers
        Paper paper6 = factory.createPaper();
        Paper paper7 = factory.createPaper();
        Paper paper8 = factory.createPaper();
        Paper paper9 = factory.createPaper();
        Paper paper10 = factory.createPaper();
        
        // P6 and P7: unsubmitted reviews (no feedback/grade)
        ReviewAssignment assignment6 = factory.createReviewAssignment();
        assignment6.setPaper(paper6);
        // No feedback or grade
        
        ReviewAssignment assignment7 = factory.createReviewAssignment();
        assignment7.setPaper(paper7);
        // No feedback or grade
        
        // P8-P10: submitted reviews with REJECT grade
        ReviewAssignment assignment8 = factory.createReviewAssignment();
        assignment8.setPaper(paper8);
        assignment8.setFeedback("Needs improvement");
        assignment8.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment9 = factory.createReviewAssignment();
        assignment9.setPaper(paper9);
        assignment9.setFeedback("Not suitable");
        assignment9.setGrade(Grade.REJECT);
        
        ReviewAssignment assignment10 = factory.createReviewAssignment();
        assignment10.setPaper(paper10);
        assignment10.setFeedback("Poor methodology");
        assignment10.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(assignment6);
        reviewer.getAssignments().add(assignment7);
        reviewer.getAssignments().add(assignment8);
        reviewer.getAssignments().add(assignment9);
        reviewer.getAssignments().add(assignment10);
        
        // Execute and Verify
        int result = reviewer.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        // No assignments added - empty assignments list
        
        // Execute and Verify
        int result = reviewer.calculateUnsubmittedReviews();
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        Paper paper11 = factory.createPaper();
        Paper paper12 = factory.createPaper();
        Paper paper13 = factory.createPaper();
        
        // P11: submitted review with ACCEPT grade
        ReviewAssignment assignment11 = factory.createReviewAssignment();
        assignment11.setPaper(paper11);
        assignment11.setFeedback("Well-written paper");
        assignment11.setGrade(Grade.ACCEPT);
        
        // P12: unsubmitted review (no feedback/grade)
        ReviewAssignment assignment12 = factory.createReviewAssignment();
        assignment12.setPaper(paper12);
        // No feedback or grade
        
        // P13: submitted review with REJECT grade
        ReviewAssignment assignment13 = factory.createReviewAssignment();
        assignment13.setPaper(paper13);
        assignment13.setFeedback("Insufficient evidence");
        assignment13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(assignment11);
        reviewer.getAssignments().add(assignment12);
        reviewer.getAssignments().add(assignment13);
        
        // Execute and Verify
        int result = reviewer.calculateUnsubmittedReviews();
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}