package edu.conference.conference5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.conference.Author;
import edu.conference.ConferenceFactory;
import edu.conference.ConferencePackage;
import edu.conference.Grade;
import edu.conference.Paper;
import edu.conference.ReviewAssignment;
import edu.conference.Reviewer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

public class CR1Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Create Reviewer R001
        Reviewer reviewer = factory.createReviewer();
        
        // Create Papers P1, P2, P3
        Paper paper1 = factory.createPaper();
        Paper paper2 = factory.createPaper();
        Paper paper3 = factory.createPaper();
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        // Create review assignments with UNDECIDED grade (default)
        ReviewAssignment assignment1 = factory.createReviewAssignment();
        assignment1.setPaper(paper1);
        assignment1.setGrade(Grade.UNDECIDED);
        reviewer.getAssignments().add(assignment1);
        
        ReviewAssignment assignment2 = factory.createReviewAssignment();
        assignment2.setPaper(paper2);
        assignment2.setGrade(Grade.UNDECIDED);
        reviewer.getAssignments().add(assignment2);
        
        ReviewAssignment assignment3 = factory.createReviewAssignment();
        assignment3.setPaper(paper3);
        assignment3.setGrade(Grade.UNDECIDED);
        reviewer.getAssignments().add(assignment3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Create Reviewer R002
        Reviewer reviewer = factory.createReviewer();
        
        // Create Papers P4, P5
        Paper paper4 = factory.createPaper();
        Paper paper5 = factory.createPaper();
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        ReviewAssignment assignment4 = factory.createReviewAssignment();
        assignment4.setPaper(paper4);
        assignment4.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(assignment4);
        
        ReviewAssignment assignment5 = factory.createReviewAssignment();
        assignment5.setPaper(paper5);
        assignment5.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(assignment5);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Create Reviewer R003
        Reviewer reviewer = factory.createReviewer();
        
        // Create Papers P6-P10
        Paper paper6 = factory.createPaper();
        Paper paper7 = factory.createPaper();
        Paper paper8 = factory.createPaper();
        Paper paper9 = factory.createPaper();
        Paper paper10 = factory.createPaper();
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (UNDECIDED)
        ReviewAssignment assignment6 = factory.createReviewAssignment();
        assignment6.setPaper(paper6);
        assignment6.setGrade(Grade.UNDECIDED);
        reviewer.getAssignments().add(assignment6);
        
        ReviewAssignment assignment7 = factory.createReviewAssignment();
        assignment7.setPaper(paper7);
        assignment7.setGrade(Grade.UNDECIDED);
        reviewer.getAssignments().add(assignment7);
        
        // Assign P8-P10 to R003 with grade=REJECT
        ReviewAssignment assignment8 = factory.createReviewAssignment();
        assignment8.setPaper(paper8);
        assignment8.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment8);
        
        ReviewAssignment assignment9 = factory.createReviewAssignment();
        assignment9.setPaper(paper9);
        assignment9.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment9);
        
        ReviewAssignment assignment10 = factory.createReviewAssignment();
        assignment10.setPaper(paper10);
        assignment10.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment10);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 2
        assertEquals("Reviewer with 2 pending and 3 submitted reviews should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Create Reviewer R004 (no assignments)
        Reviewer reviewer = factory.createReviewer();
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Create Reviewer R005
        Reviewer reviewer = factory.createReviewer();
        
        // Create Papers P11-P13
        Paper paper11 = factory.createPaper();
        Paper paper12 = factory.createPaper();
        Paper paper13 = factory.createPaper();
        
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        ReviewAssignment assignment11 = factory.createReviewAssignment();
        assignment11.setPaper(paper11);
        assignment11.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(assignment11);
        
        ReviewAssignment assignment12 = factory.createReviewAssignment();
        assignment12.setPaper(paper12);
        assignment12.setGrade(Grade.UNDECIDED); // No grade submitted
        reviewer.getAssignments().add(assignment12);
        
        ReviewAssignment assignment13 = factory.createReviewAssignment();
        assignment13.setPaper(paper13);
        assignment13.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment13);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected Output: 1
        assertEquals("Reviewer with 1 pending and 2 submitted reviews should return 1", 1, result);
    }
}