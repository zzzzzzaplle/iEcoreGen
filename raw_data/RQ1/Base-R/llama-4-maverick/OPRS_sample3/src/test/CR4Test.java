import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Author author;
    
    @Before
    public void setUp() {
        // Initialize author before each test
        author = new Author();
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 and Papers P30-P32 with final decision=ACCEPT
        author.setName("A006");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision("Accept");
        papers.add(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision("Accept");
        papers.add(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision("Accept");
        papers.add(paper3);
        
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        author.setName("A007");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision("Accept");
        papers.add(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision("Reject");
        papers.add(paper2);
        
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        author.setName("A008");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision("Reject");
        papers.add(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision("Reject");
        papers.add(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision("Reject");
        papers.add(paper3);
        
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author.setName("A009");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision("Accept");
        papers.add(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision("Reject");
        papers.add(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision("Reject");
        papers.add(paper3);
        
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        author.setName("A010");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision("Accept");
        papers.add(paper);
        
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}