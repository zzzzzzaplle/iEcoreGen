import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Author author;
    
    @Before
    public void setUp() {
        // Reset author before each test
        author = new Author();
    }
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        author = new Author();
        
        // Create papers with ACCEPT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.ACCEPT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.ACCEPT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        author = new Author();
        
        // Create paper with ACCEPT decision
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        // Create paper with REJECT decision
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        author = new Author();
        
        // Create three papers with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.REJECT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author = new Author();
        
        // Create paper with ACCEPT decision
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        // Create two papers with REJECT decisions
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, result, 0.01);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        author.submitPaper(paper);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}