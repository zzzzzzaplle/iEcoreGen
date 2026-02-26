package edu.conference.conference1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Paper;
import edu.conference.ReviewAssignment;
import edu.conference.Reviewer;
import edu.conference.Grade;

public class CR2Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = factory.createPaper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 3 review assignments for paper P14 with ACCEPT grades
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("revise");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper);
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("revise");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper);
        assignment3.setGrade(Grade.ACCEPT);
        assignment3.setFeedback("revise");
        
        // Add assignments to paper's reviews
        paper.getReviews().add(assignment1);
        paper.getReviews().add(assignment2);
        paper.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All 3 reviews should be ACCEPT, returning true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Create Paper P15
        Paper paper = factory.createPaper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        
        // Create 2 review assignments with split decisions
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("good work");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs improvement");
        
        // Add assignments to paper's reviews
        paper.getReviews().add(assignment1);
        paper.getReviews().add(assignment2);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed grades)
        assertFalse("Mixed grades (ACCEPT and REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Create Paper P16
        Paper paper = factory.createPaper();
        paper.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments with REJECT grades
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper);
        assignment1.setGrade(Grade.REJECT);
        assignment1.setFeedback("poor methodology");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("insufficient data");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper);
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("weak conclusions");
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper);
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("not novel");
        
        // Add assignments to paper's reviews
        paper.getReviews().add(assignment1);
        paper.getReviews().add(assignment2);
        paper.getReviews().add(assignment3);
        paper.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True (all REJECT is considered consistent)
        assertTrue("All 4 reviews should be REJECT, returning true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = factory.createPaper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED (no feedback)
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("well written");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs major revisions");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper);
        assignment3.setGrade(Grade.UNDECIDED); // Reviewer did not give feedback
        assignment3.setFeedback(null); // No feedback given
        
        // Add assignments to paper's reviews
        paper.getReviews().add(assignment1);
        paper.getReviews().add(assignment2);
        paper.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed grades including UNDECIDED)
        assertFalse("Mixed grades including UNDECIDED should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = factory.createPaper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("strong contribution");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper);
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("good analysis");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper);
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("limited scope");
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper);
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("methodology concerns");
        
        // Add assignments to paper's reviews
        paper.getReviews().add(assignment1);
        paper.getReviews().add(assignment2);
        paper.getReviews().add(assignment3);
        paper.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (split decision 2-2)
        assertFalse("Split decision (2 ACCEPT, 2 REJECT) should return false", result);
    }
}