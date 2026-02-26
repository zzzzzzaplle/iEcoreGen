package edu.conference.conference4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Paper;
import edu.conference.ReviewAssignment;
import edu.conference.Reviewer;
import edu.conference.Grade;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the conference factory using Ecore factory pattern
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Input: Check consensus for Paper P14
        // Expected Output: True
        
        // Setup: Create Paper P14
        Paper paperP14 = factory.createPaper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers and assign paper P14 to them
        for (int i = 0; i < 3; i++) {
            Reviewer reviewer = factory.createReviewer();
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paperP14);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("revise");
            reviewer.getAssignments().add(assignment);
            paperP14.getReviews().add(assignment);
        }
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper P14 should have unanimous ACCEPT reviews", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Input: Check consensus for Paper P15
        // Expected Output: False
        
        // Setup: Create Paper P15
        Paper paperP15 = factory.createPaper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers and assign paper P15 to them
        // First reviewer gives ACCEPT
        Reviewer reviewer1 = factory.createReviewer();
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP15);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("good paper");
        reviewer1.getAssignments().add(assignment1);
        paperP15.getReviews().add(assignment1);
        
        // Second reviewer gives REJECT
        Reviewer reviewer2 = factory.createReviewer();
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP15);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs major revisions");
        reviewer2.getAssignments().add(assignment2);
        paperP15.getReviews().add(assignment2);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P15 should not have consensus with split decision", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Input: Check consensus for Paper P16
        // Expected Output: True
        
        // Setup: Create Paper P16
        Paper paperP16 = factory.createPaper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers and assign paper P16 to them
        for (int i = 0; i < 4; i++) {
            Reviewer reviewer = factory.createReviewer();
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paperP16);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("reject reason " + (i + 1));
            reviewer.getAssignments().add(assignment);
            paperP16.getReviews().add(assignment);
        }
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper P16 should have unanimous REJECT reviews", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Input: Check consensus for Paper P17
        // Expected Output: False
        
        // Setup: Create Paper P17
        Paper paperP17 = factory.createPaper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers and assign paper P17 to them
        // First reviewer gives ACCEPT
        Reviewer reviewer1 = factory.createReviewer();
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paperP17);
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("accept feedback");
        reviewer1.getAssignments().add(assignment1);
        paperP17.getReviews().add(assignment1);
        
        // Second reviewer gives REJECT
        Reviewer reviewer2 = factory.createReviewer();
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paperP17);
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("reject feedback");
        reviewer2.getAssignments().add(assignment2);
        paperP17.getReviews().add(assignment2);
        
        // Third reviewer does not give feedback (UNDECIDED)
        Reviewer reviewer3 = factory.createReviewer();
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paperP17);
        assignment3.setGrade(Grade.UNDECIDED); // Default is UNDECIDED
        assignment3.setFeedback(""); // No feedback given
        reviewer3.getAssignments().add(assignment3);
        paperP17.getReviews().add(assignment3);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P17 should not have consensus with mixed grades including UNDECIDED", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Input: Check consensus for Paper P18
        // Expected Output: False
        
        // Setup: Create Paper P18
        Paper paperP18 = factory.createPaper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers and assign paper P18 to them
        // First 2 reviewers give ACCEPT
        for (int i = 0; i < 2; i++) {
            Reviewer reviewer = factory.createReviewer();
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paperP18);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("accept " + (i + 1));
            reviewer.getAssignments().add(assignment);
            paperP18.getReviews().add(assignment);
        }
        
        // Last 2 reviewers give REJECT
        for (int i = 0; i < 2; i++) {
            Reviewer reviewer = factory.createReviewer();
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paperP18);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("reject " + (i + 1));
            reviewer.getAssignments().add(assignment);
            paperP18.getReviews().add(assignment);
        }
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P18 should not have consensus with exactly 50% split", result);
    }
}