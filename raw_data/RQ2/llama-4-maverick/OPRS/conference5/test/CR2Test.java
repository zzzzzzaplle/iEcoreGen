package edu.conference.conference5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.conference.Author;
import edu.conference.CoChair;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Grade;
import edu.conference.Paper;
import edu.conference.ReviewAssignment;
import edu.conference.Reviewer;
import edu.conference.User;
import edu.conference.UserRole;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class CR2Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paperP14 = factory.createPaper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 3 review assignments for paper P14
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP14);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("revise");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP14);
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("revise");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paperP14);
        assignment3.setGrade(Grade.ACCEPT);
        assignment3.setFeedback("revise");
        
        // Add assignments to paper's reviews
        paperP14.getReviews().add(assignment1);
        paperP14.getReviews().add(assignment2);
        paperP14.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT - unanimous accept", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Create Paper P15
        Paper paperP15 = factory.createPaper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        
        // Create 2 review assignments for paper P15 with split decision
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP15);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("good paper");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP15);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs major revisions");
        
        // Add assignments to paper's reviews
        paperP15.getReviews().add(assignment1);
        paperP15.getReviews().add(assignment2);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades - should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Create Paper P16
        Paper paperP16 = factory.createPaper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments for paper P16, all with REJECT
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP16);
        assignment1.setGrade(Grade.REJECT);
        assignment1.setFeedback("poor methodology");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP16);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("insufficient data");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paperP16);
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("weak contribution");
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paperP16);
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("not novel enough");
        
        // Add assignments to paper's reviews
        paperP16.getReviews().add(assignment1);
        paperP16.getReviews().add(assignment2);
        paperP16.getReviews().add(assignment3);
        paperP16.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are consistently REJECT)
        assertTrue("All reviews should be REJECT - unanimous reject", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paperP17 = factory.createPaper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 2 review assignments with completed reviews
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP17);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("well-written");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP17);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs more experiments");
        
        // Create 1 review assignment without feedback (UNDECIDED grade)
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paperP17);
        assignment3.setGrade(Grade.UNDECIDED); // Default grade is UNDECIDED
        // No feedback set - simulating reviewer who hasn't submitted review
        
        // Add assignments to paper's reviews
        paperP17.getReviews().add(assignment1);
        paperP17.getReviews().add(assignment2);
        paperP17.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False (mixed grades including UNDECIDED)
        assertFalse("Reviews have mixed grades including UNDECIDED - should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paperP18 = factory.createPaper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP18);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("strong results");
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP18);
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("good contribution");
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paperP18);
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("limited scope");
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paperP18);
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("methodology concerns");
        
        // Add assignments to paper's reviews
        paperP18.getReviews().add(assignment1);
        paperP18.getReviews().add(assignment2);
        paperP18.getReviews().add(assignment3);
        paperP18.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False (split decision 2-2)
        assertFalse("Reviews are split 2-2 - should return false", result);
    }
}