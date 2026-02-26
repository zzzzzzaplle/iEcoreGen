package edu.conference.conference1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.conference.Author;
import edu.conference.Paper;
import edu.conference.ConferenceFactory;
import edu.conference.Grade;

public class CR4Test {
    
    private ConferenceFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = factory.createAuthor();
        
        // Create papers with ACCEPT decisions
        Paper paper1 = factory.createPaper();
        paper1.setDecision(Grade.ACCEPT);
        author.getPapers().add(paper1);
        
        Paper paper2 = factory.createPaper();
        paper2.setDecision(Grade.ACCEPT);
        author.getPapers().add(paper2);
        
        Paper paper3 = factory.createPaper();
        paper3.setDecision(Grade.ACCEPT);
        author.getPapers().add(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals("Perfect acceptance rate should be 1.00", 1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = factory.createAuthor();
        
        // Create one accepted paper and one rejected paper
        Paper acceptedPaper = factory.createPaper();
        acceptedPaper.setDecision(Grade.ACCEPT);
        author.getPapers().add(acceptedPaper);
        
        Paper rejectedPaper = factory.createPaper();
        rejectedPaper.setDecision(Grade.REJECT);
        author.getPapers().add(rejectedPaper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals("50% acceptance rate should be 0.50", 0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = factory.createAuthor();
        
        // Create three rejected papers
        Paper paper1 = factory.createPaper();
        paper1.setDecision(Grade.REJECT);
        author.getPapers().add(paper1);
        
        Paper paper2 = factory.createPaper();
        paper2.setDecision(Grade.REJECT);
        author.getPapers().add(paper2);
        
        Paper paper3 = factory.createPaper();
        paper3.setDecision(Grade.REJECT);
        author.getPapers().add(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals("No accepted papers should return 0.00", 0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = factory.createAuthor();
        
        // Create one accepted and two rejected papers
        Paper acceptedPaper = factory.createPaper();
        acceptedPaper.setDecision(Grade.ACCEPT);
        author.getPapers().add(acceptedPaper);
        
        Paper rejectedPaper1 = factory.createPaper();
        rejectedPaper1.setDecision(Grade.REJECT);
        author.getPapers().add(rejectedPaper1);
        
        Paper rejectedPaper2 = factory.createPaper();
        rejectedPaper2.setDecision(Grade.REJECT);
        author.getPapers().add(rejectedPaper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (rounded to 2 decimal places)
        assertEquals("1 acceptance out of 3 papers should be 0.33", 0.33, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = factory.createAuthor();
        
        // Create single accepted paper
        Paper paper = factory.createPaper();
        paper.setDecision(Grade.ACCEPT);
        author.getPapers().add(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals("Single accepted paper should return 1.00", 1.00, acceptanceRate, 0.001);
    }
}