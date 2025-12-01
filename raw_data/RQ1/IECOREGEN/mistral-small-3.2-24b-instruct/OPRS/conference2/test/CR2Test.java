package edu.conference.conference2.test;

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
        // Setup
        Paper paperP14 = factory.createPaper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers and review assignments
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create review assignments with ACCEPT grades
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
        
        // Test: Check consensus for Paper P14
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup
        Paper paperP15 = factory.createPaper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers and review assignments
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        
        // Create review assignments with mixed grades
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("good paper");
        assignment1.setPaper(paperP15);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs major revisions");
        assignment2.setPaper(paperP15);
        
        // Add assignments to paper's reviews
        paperP15.getReviews().add(assignment1);
        paperP15.getReviews().add(assignment2);
        
        // Test: Check consensus for Paper P15
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with split decision (ACCEPT and REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup
        Paper paperP16 = factory.createPaper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers and review assignments
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create review assignments with REJECT grades
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.REJECT);
        assignment1.setFeedback("poor methodology");
        assignment1.setPaper(paperP16);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("insufficient data");
        assignment2.setPaper(paperP16);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("weak conclusions");
        assignment3.setPaper(paperP16);
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("not novel enough");
        assignment4.setPaper(paperP16);
        
        // Add assignments to paper's reviews
        paperP16.getReviews().add(assignment1);
        paperP16.getReviews().add(assignment2);
        paperP16.getReviews().add(assignment3);
        paperP16.getReviews().add(assignment4);
        
        // Test: Check consensus for Paper P16
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup
        Paper paperP17 = factory.createPaper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers and review assignments
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        
        // Create review assignments with mixed grades (one without feedback)
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("well-written");
        assignment1.setPaper(paperP17);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.REJECT);
        assignment2.setFeedback("needs more experiments");
        assignment2.setPaper(paperP17);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.UNDECIDED); // Default grade when not set
        assignment3.setFeedback(""); // Empty feedback
        assignment3.setPaper(paperP17);
        
        // Add assignments to paper's reviews
        paperP17.getReviews().add(assignment1);
        paperP17.getReviews().add(assignment2);
        paperP17.getReviews().add(assignment3);
        
        // Test: Check consensus for Paper P17
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup
        Paper paperP18 = factory.createPaper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers and review assignments
        Reviewer reviewer1 = factory.createReviewer();
        Reviewer reviewer2 = factory.createReviewer();
        Reviewer reviewer3 = factory.createReviewer();
        Reviewer reviewer4 = factory.createReviewer();
        
        // Create review assignments with 2 ACCEPT and 2 REJECT
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignment1.setFeedback("strong contribution");
        assignment1.setPaper(paperP18);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setGrade(Grade.ACCEPT);
        assignment2.setFeedback("good analysis");
        assignment2.setPaper(paperP18);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignment3.setFeedback("limited scope");
        assignment3.setPaper(paperP18);
        
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setGrade(Grade.REJECT);
        assignment4.setFeedback("methodology issues");
        assignment4.setPaper(paperP18);
        
        // Add assignments to paper's reviews
        paperP18.getReviews().add(assignment1);
        paperP18.getReviews().add(assignment2);
        paperP18.getReviews().add(assignment3);
        paperP18.getReviews().add(assignment4);
        
        // Test: Check consensus for Paper P18
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with exactly 50% acceptance (2 ACCEPT, 2 REJECT) should return false", result);
    }
}