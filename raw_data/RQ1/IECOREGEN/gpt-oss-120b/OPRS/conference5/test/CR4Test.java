package edu.conference.conference5.test;

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
        Paper paper30 = factory.createPaper();
        paper30.setDecision(Grade.ACCEPT);
        author.submitPaper(paper30);
        
        Paper paper31 = factory.createPaper();
        paper31.setDecision(Grade.ACCEPT);
        author.submitPaper(paper31);
        
        Paper paper32 = factory.createPaper();
        paper32.setDecision(Grade.ACCEPT);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = factory.createAuthor();
        
        // Create papers with mixed decisions
        Paper paper33 = factory.createPaper();
        paper33.setDecision(Grade.ACCEPT);
        author.submitPaper(paper33);
        
        Paper paper34 = factory.createPaper();
        paper34.setDecision(Grade.REJECT);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = factory.createAuthor();
        
        // Create papers with REJECT decisions
        Paper paper35 = factory.createPaper();
        paper35.setDecision(Grade.REJECT);
        author.submitPaper(paper35);
        
        Paper paper36 = factory.createPaper();
        paper36.setDecision(Grade.REJECT);
        author.submitPaper(paper36);
        
        Paper paper37 = factory.createPaper();
        paper37.setDecision(Grade.REJECT);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = factory.createAuthor();
        
        // Create papers with mixed decisions (1 ACCEPT, 2 REJECT)
        Paper paper38 = factory.createPaper();
        paper38.setDecision(Grade.ACCEPT);
        author.submitPaper(paper38);
        
        Paper paper39 = factory.createPaper();
        paper39.setDecision(Grade.REJECT);
        author.submitPaper(paper39);
        
        Paper paper40 = factory.createPaper();
        paper40.setDecision(Grade.REJECT);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, acceptanceRate, 0.01); // Using delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = factory.createAuthor();
        
        // Create single paper with ACCEPT decision
        Paper paper41 = factory.createPaper();
        paper41.setDecision(Grade.ACCEPT);
        author.submitPaper(paper41);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}