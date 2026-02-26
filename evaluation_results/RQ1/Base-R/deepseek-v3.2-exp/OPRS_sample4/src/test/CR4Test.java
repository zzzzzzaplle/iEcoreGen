import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        author.setName("A006");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setDecision(Decision.ACCEPT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setDecision(Decision.ACCEPT);
        
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        author.setName("A007");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setDecision(Decision.REJECT);
        
        papers.add(paper1);
        papers.add(paper2);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        author.setName("A008");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setDecision(Decision.REJECT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setDecision(Decision.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setDecision(Decision.REJECT);
        
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        author.setName("A009");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setDecision(Decision.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setDecision(Decision.REJECT);
        
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.01); // Using delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        author.setName("A010");
        
        List<Paper> papers = new ArrayList<>();
        
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setDecision(Decision.ACCEPT);
        
        papers.add(paper);
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}