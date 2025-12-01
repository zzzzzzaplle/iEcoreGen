package edu.conference.conference1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Reviewer;
import edu.conference.ReviewAssignment;
import edu.conference.Paper;
import edu.conference.Grade;
import org.eclipse.emf.common.util.EList;

public class CR5Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_AllAcceptances() {
        // Test Case 1: "All acceptances"
        // Input: Calculate rating tendency for Reviewer R006
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 5 papers and assign ACCEPT reviews
        for (int i = 0; i < 5; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Paper_" + (i + 1));
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("Excellent paper " + (i + 1));
            
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
        // Input: Calculate rating tendency for Reviewer R007
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Accept_Paper_" + (i + 1));
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.ACCEPT);
            assignment.setFeedback("Good paper " + (i + 1));
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Reject_Paper_" + (i + 1));
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("Needs improvement " + (i + 1));
            
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
        // Input: Calculate rating tendency for Reviewer R008
        // Setup: Create Reviewer R008 with no reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Calculate acceptance proportion with no assignments
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_RecentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Input: Calculate rating tendency for Reviewer R009
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 1 ACCEPT review
        Paper acceptPaper = factory.createPaper();
        acceptPaper.setTitle("Single_Accept_Paper");
        
        ReviewAssignment acceptAssignment = factory.createReviewAssignment();
        acceptAssignment.setPaper(acceptPaper);
        acceptAssignment.setGrade(Grade.ACCEPT);
        acceptAssignment.setFeedback("Well written paper");
        
        reviewer.getAssignments().add(acceptAssignment);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = factory.createPaper();
            paper.setTitle("Reject_Paper_" + (i + 1));
            
            ReviewAssignment assignment = factory.createReviewAssignment();
            assignment.setPaper(paper);
            assignment.setGrade(Grade.REJECT);
            assignment.setFeedback("Not suitable " + (i + 1));
            
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.20 (1/5 = 0.2)
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleReviewCase() {
        // Test Case 5: "Single review case"
        // Input: Calculate rating tendency for Reviewer R010
        // Setup: Create Reviewer R010 with 1 REJECT review
        
        Reviewer reviewer = factory.createReviewer();
        
        // Create 1 REJECT review
        Paper paper = factory.createPaper();
        paper.setTitle("Single_Reject_Paper");
        
        ReviewAssignment assignment = factory.createReviewAssignment();
        assignment.setPaper(paper);
        assignment.setGrade(Grade.REJECT);
        assignment.setFeedback("Poor quality paper");
        
        reviewer.getAssignments().add(assignment);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}