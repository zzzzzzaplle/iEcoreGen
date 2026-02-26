import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author("A006");
        
        // Create papers with ACCEPTED decisions
        Paper paper1 = new Paper("P30", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper("P31", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.ACCEPTED);
        
        Paper paper3 = new Paper("P32", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.ACCEPTED);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.acceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001); // Using delta for double comparison
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author("A007");
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper("P33", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper("P34", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.acceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001); // Using delta for double comparison
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author("A008");
        
        // Create papers with REJECTED decisions
        Paper paper1 = new Paper("P35", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.REJECTED);
        
        Paper paper2 = new Paper("P36", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECTED);
        
        Paper paper3 = new Paper("P37", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.acceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001); // Using delta for double comparison
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author("A009");
        
        // Create papers with mixed decisions (1 ACCEPTED, 2 REJECTED)
        Paper paper1 = new Paper("P38", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper("P39", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECTED);
        
        Paper paper3 = new Paper("P40", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.acceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001); // Using delta for double comparison (1/3 ≈ 0.333)
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author("A010");
        
        // Create single paper with ACCEPTED decision
        Paper paper = new Paper("P41", PaperType.RESEARCH);
        paper.setFinalDecision(Decision.ACCEPTED);
        
        // Add paper to author
        author.addPaper(paper);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.acceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001); // Using delta for double comparison
    }
}