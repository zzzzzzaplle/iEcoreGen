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
        reviewer.getAssignments().add(assignment1);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper2);
        reviewer.getAssignments().add(assignment2);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper3);
        reviewer.getAssignments().add(assignment3);
        
        // Execute
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        Paper paper4 = factory.createPaper();
        Paper paper5 = factory.createPaper();
        
        // Create review assignments with grades and feedback (submitted)
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper4);
        assignment4.setGrade(Grade.ACCEPT);
        assignment4.setFeedback("Good paper");
        reviewer.getAssignments().add(assignment4);
        
        ReviewAssignment assignment5 = factory.createReviewAssignment();
        assignment5.setPaper(paper5);
        assignment5.setGrade(Grade.ACCEPT);
        assignment5.setFeedback("Excellent work");
        reviewer.getAssignments().add(assignment5);
        
        // Execute
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        
        // Create 5 papers (P6-P10)
        Paper[] papers = new Paper[5];
        for (int i = 0; i < 5; i++) {
            papers[i] = factory.createPaper();
            papers[i].setTitle("P" + (i + 6));
        }
        
        // P6 and P7 without feedback and grade (unsubmitted)
        ReviewAssignment assignment6 = factory.createReviewAssignment();
        assignment6.setPaper(papers[0]);
        reviewer.getAssignments().add(assignment6);
        
        ReviewAssignment assignment7 = factory.createReviewAssignment();
        assignment7.setPaper(papers[1]);
        reviewer.getAssignments().add(assignment7);
        
        // P8-P10 with grade=REJECT (submitted)
        ReviewAssignment assignment8 = factory.createReviewAssignment();
        assignment8.setPaper(papers[2]);
        assignment8.setGrade(Grade.REJECT);
        assignment8.setFeedback("Needs improvement");
        reviewer.getAssignments().add(assignment8);
        
        ReviewAssignment assignment9 = factory.createReviewAssignment();
        assignment9.setPaper(papers[3]);
        assignment9.setGrade(Grade.REJECT);
        assignment9.setFeedback("Not suitable");
        reviewer.getAssignments().add(assignment9);
        
        ReviewAssignment assignment10 = factory.createReviewAssignment();
        assignment10.setPaper(papers[4]);
        assignment10.setGrade(Grade.REJECT);
        assignment10.setFeedback("Poor methodology");
        reviewer.getAssignments().add(assignment10);
        
        // Execute
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify
        assertEquals("Reviewer with 2 unsubmitted and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup
        Reviewer reviewer = factory.createReviewer();
        // No assignments added
        
        // Execute
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
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
        
        // P11 with grade=ACCEPT (submitted)
        ReviewAssignment assignment11 = factory.createReviewAssignment();
        assignment11.setPaper(paper11);
        assignment11.setGrade(Grade.ACCEPT);
        assignment11.setFeedback("Well written");
        reviewer.getAssignments().add(assignment11);
        
        // P12 without grade and feedback (unsubmitted)
        ReviewAssignment assignment12 = factory.createReviewAssignment();
        assignment12.setPaper(paper12);
        reviewer.getAssignments().add(assignment12);
        
        // P13 with grade=REJECT (submitted)
        ReviewAssignment assignment13 = factory.createReviewAssignment();
        assignment13.setPaper(paper13);
        assignment13.setGrade(Grade.REJECT);
        assignment13.setFeedback("Insufficient evidence");
        reviewer.getAssignments().add(assignment13);
        
        // Execute
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify
        assertEquals("Reviewer with 1 unsubmitted and 2 submitted reviews should return 1", 1, result);
    }
}