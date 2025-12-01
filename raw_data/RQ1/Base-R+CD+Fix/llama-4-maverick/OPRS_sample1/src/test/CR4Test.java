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
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        author = new Author();
        
        // Create papers with ACCEPT decisions
        Paper paper30 = new Paper();
        paper30.setDecision(Grade.ACCEPT);
        author.submitPaper(paper30);
        
        Paper paper31 = new Paper();
        paper31.setDecision(Grade.ACCEPT);
        author.submitPaper(paper31);
        
        Paper paper32 = new Paper();
        paper32.setDecision(Grade.ACCEPT);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        author = new Author();
        
        // Create papers with mixed decisions
        Paper paper33 = new Paper();
        paper33.setDecision(Grade.ACCEPT);
        author.submitPaper(paper33);
        
        Paper paper34 = new Paper();
        paper34.setDecision(Grade.REJECT);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        author = new Author();
        
        // Create papers with REJECT decisions
        Paper paper35 = new Paper();
        paper35.setDecision(Grade.REJECT);
        author.submitPaper(paper35);
        
        Paper paper36 = new Paper();
        paper36.setDecision(Grade.REJECT);
        author.submitPaper(paper36);
        
        Paper paper37 = new Paper();
        paper37.setDecision(Grade.REJECT);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author = new Author();
        
        // Create papers with mixed decisions (1 ACCEPT, 2 REJECT)
        Paper paper38 = new Paper();
        paper38.setDecision(Grade.ACCEPT);
        author.submitPaper(paper38);
        
        Paper paper39 = new Paper();
        paper39.setDecision(Grade.REJECT);
        author.submitPaper(paper39);
        
        Paper paper40 = new Paper();
        paper40.setDecision(Grade.REJECT);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate and verify (1/3 = 0.33)
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper41 = new Paper();
        paper41.setDecision(Grade.ACCEPT);
        author.submitPaper(paper41);
        
        // Calculate acceptance rate and verify
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}