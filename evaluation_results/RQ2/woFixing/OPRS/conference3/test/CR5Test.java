package edu.conference.conference3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Reviewer;
import edu.conference.ReviewAssignment;
import edu.conference.Paper;
import edu.conference.Grade;

public class CR5Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the conference factory using Ecore factory pattern
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AllAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 5 papers and assign ACCEPT reviews
        for (int i = 0; i < 5; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Paper_" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("Good paper " + i);
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_Balanced5050Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Accept_Paper_" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("Accepted paper " + i);
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Reject_Paper_" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("Rejected paper " + i);
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // No assignments added - reviewer has no completed reviews
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_RecentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 1 ACCEPT review
        Paper acceptPaper = factory.createPaper();
        acceptPaper.setTitle("Accept_Paper");
        
        ReviewAssignment acceptAssignment = factory.createReviewAssignment();
        acceptAssignment.setPaper(acceptPaper);
        acceptAssignment.setGrade(Grade.ACCEPT);
        acceptAssignment.setFeedback("Only accepted paper");
        
        reviewer.getAssignments().add(acceptAssignment);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Reject_Paper_" + i);
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("Rejected paper " + i);
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 with 1 REJECT review
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 1 REJECT review
        Paper paper = factory.createPaper();
        paper.setTitle("Single_Reject_Paper");
        
        ReviewAssignment assignment = factory.createReviewAssignment();
        assignment.setPaper(paper);
        assignment.setGrade(Grade.REJECT);
        assignment.setFeedback("Single rejected paper");
        
        reviewer.getAssignments().add(assignment);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}