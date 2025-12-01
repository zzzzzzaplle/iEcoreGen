package edu.conference.conference2.test;

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
        factory = ConferenceFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = factory.createAuthor();
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper30 = factory.createPaper();
        paper30.setDecision(Grade.ACCEPT);
        author.submitPaper(paper30);
        
        Paper paper31 = factory.createPaper();
        paper31.setDecision(Grade.ACCEPT);
        author.submitPaper(paper31);
        
        Paper paper32 = factory.createPaper();
        paper32.setDecision(Grade.ACCEPT);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = factory.createAuthor();
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper33 = factory.createPaper();
        paper33.setDecision(Grade.ACCEPT);
        author.submitPaper(paper33);
        
        Paper paper34 = factory.createPaper();
        paper34.setDecision(Grade.REJECT);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = factory.createAuthor();
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper35 = factory.createPaper();
        paper35.setDecision(Grade.REJECT);
        author.submitPaper(paper35);
        
        Paper paper36 = factory.createPaper();
        paper36.setDecision(Grade.REJECT);
        author.submitPaper(paper36);
        
        Paper paper37 = factory.createPaper();
        paper37.setDecision(Grade.REJECT);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = factory.createAuthor();
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper38 = factory.createPaper();
        paper38.setDecision(Grade.ACCEPT);
        author.submitPaper(paper38);
        
        Paper paper39 = factory.createPaper();
        paper39.setDecision(Grade.REJECT);
        author.submitPaper(paper39);
        
        Paper paper40 = factory.createPaper();
        paper40.setDecision(Grade.REJECT);
        author.submitPaper(paper40);
        
        // Expected Output: 0.33 (rounded to 2 decimal places)
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = factory.createAuthor();
        
        // Create Paper P41 with ACCEPT decision
        Paper paper41 = factory.createPaper();
        paper41.setDecision(Grade.ACCEPT);
        author.submitPaper(paper41);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}