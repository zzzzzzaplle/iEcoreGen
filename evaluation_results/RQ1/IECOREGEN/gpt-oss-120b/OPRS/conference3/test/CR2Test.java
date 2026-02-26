package edu.conference.conference3.test;

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
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Input: Check consensus for Paper P14
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        // Expected Output: True
        
        // Create Paper P14
        Paper paperP14 = factory.createPaper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("revise");
        assignment1.setPaper(paperP14);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("revise");
        assignment2.setPaper(paperP14);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.ACCEPT);
        assignment3.setFeedback("revise");
        assignment3.setPaper(paperP14);
        
        // Add assignments to paper's reviews
        paperP14.getReviews().add(assignment1);
        paperP14.getReviews().add(assignment2);
        paperP14.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Input: Check consensus for Paper P15
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        // Expected Output: False
        
        // Create Paper P15
        Paper paperP15 = factory.createPaper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        
        // Create 2 review assignments with mixed grades
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("good paper");
        assignment1.setPaper(paperP15);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs major revision");
        assignment2.setPaper(paperP15);
        
        // Add assignments to paper's reviews
        paperP15.getReviews().add(assignment1);
        paperP15.getReviews().add(assignment2);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Input: Check consensus for Paper P16
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        // Expected Output: True
        
        // Create Paper P16
        Paper paperP16 = factory.createPaper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.REJECT);
        assignment1.setFeedback("poor methodology");
        assignment1.setPaper(paperP16);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("insufficient results");
        assignment2.setPaper(paperP16);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("weak contribution");
        assignment3.setPaper(paperP16);
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("not suitable for conference");
        assignment4.setPaper(paperP16);
        
        // Add assignments to paper's reviews
        paperP16.getReviews().add(assignment1);
        paperP16.getReviews().add(assignment2);
        paperP16.getReviews().add(assignment3);
        paperP16.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Input: Check consensus for Paper P17
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 1 ACCEPT, 1 REJECT, 1 no feedback
        // Expected Output: False
        
        // Create Paper P17
        Paper paperP17 = factory.createPaper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create 3 review assignments with mixed grades (including UNDECIDED)
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("well-written");
        assignment1.setPaper(paperP17);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs more experiments");
        assignment2.setPaper(paperP17);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.UNDECIDED); // No feedback given
        assignment3.setFeedback(""); // Empty feedback
        assignment3.setPaper(paperP17);
        
        // Add assignments to paper's reviews
        paperP17.getReviews().add(assignment1);
        paperP17.getReviews().add(assignment2);
        paperP17.getReviews().add(assignment3);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Input: Check consensus for Paper P18
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT, 2 REJECT
        // Expected Output: False
        
        // Create Paper P18
        Paper paperP18 = factory.createPaper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create 4 review assignments with 2 ACCEPT and 2 REJECT
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("strong contribution");
        assignment1.setPaper(paperP18);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("novel approach");
        assignment2.setPaper(paperP18);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("limited evaluation");
        assignment3.setPaper(paperP18);
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("incremental work");
        assignment4.setPaper(paperP18);
        
        // Add assignments to paper's reviews
        paperP18.getReviews().add(assignment1);
        paperP18.getReviews().add(assignment2);
        paperP18.getReviews().add(assignment3);
        paperP18.getReviews().add(assignment4);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper with exactly 50% acceptance (2 ACCEPT, 2 REJECT) should return false", result);
    }
}