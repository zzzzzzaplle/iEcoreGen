import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        author.setName("A006");
        
        // Create papers with ACCEPT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(true);
        Paper paper2 = new Paper();
        paper2.setDecision(true);
        Paper paper3 = new Paper();
        paper3.setDecision(true);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        author.setName("A007");
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setDecision(true);  // ACCEPT
        Paper paper2 = new Paper();
        paper2.setDecision(false); // REJECT
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        author.setName("A008");
        
        // Create papers with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(false);
        Paper paper2 = new Paper();
        paper2.setDecision(false);
        Paper paper3 = new Paper();
        paper3.setDecision(false);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        author.setName("A009");
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setDecision(true);  // ACCEPT
        Paper paper2 = new Paper();
        paper2.setDecision(false); // REJECT
        Paper paper3 = new Paper();
        paper3.setDecision(false); // REJECT
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        author.setName("A010");
        
        // Create paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setDecision(true);
        
        // Add paper to author
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}