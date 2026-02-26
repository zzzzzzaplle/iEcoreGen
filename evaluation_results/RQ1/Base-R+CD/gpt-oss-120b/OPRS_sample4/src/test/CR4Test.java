import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        
        // Create papers with ACCEPT decisions
        Paper paper1 = new Paper("P30", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P31", PaperType.RESEARCH);
        paper2.setDecision(Grade.ACCEPT);
        
        Paper paper3 = new Paper("P32", PaperType.RESEARCH);
        paper3.setDecision(Grade.ACCEPT);
        
        // Add papers to author
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
        papers.add(paper3);
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper("P33", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P34", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        // Add papers to author
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
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
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        
        // Create papers with REJECT decisions
        Paper paper1 = new Paper("P35", PaperType.RESEARCH);
        paper1.setDecision(Grade.REJECT);
        
        Paper paper2 = new Paper("P36", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper("P37", PaperType.RESEARCH);
        paper3.setDecision(Grade.REJECT);
        
        // Add papers to author
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
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
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper("P38", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P39", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper("P40", PaperType.RESEARCH);
        paper3.setDecision(Grade.REJECT);
        
        // Add papers to author
        List<Paper> papers = new ArrayList<>();
        papers.add(paper1);
        papers.add(paper2);
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
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper = new Paper("P41", PaperType.RESEARCH);
        paper.setDecision(Grade.ACCEPT);
        
        // Add paper to author
        List<Paper> papers = new ArrayList<>();
        papers.add(paper);
        author.setPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}