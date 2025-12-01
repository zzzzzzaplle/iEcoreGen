import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author();
        author.setName("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        List<Paper> papers = new ArrayList<>();
        Paper paper30 = new Paper();
        paper30.setDecision(Decision.ACCEPT);
        papers.add(paper30);
        
        Paper paper31 = new Paper();
        paper31.setDecision(Decision.ACCEPT);
        papers.add(paper31);
        
        Paper paper32 = new Paper();
        paper32.setDecision(Decision.ACCEPT);
        papers.add(paper32);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author();
        author.setName("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        List<Paper> papers = new ArrayList<>();
        Paper paper33 = new Paper();
        paper33.setDecision(Decision.ACCEPT);
        papers.add(paper33);
        
        Paper paper34 = new Paper();
        paper34.setDecision(Decision.REJECT);
        papers.add(paper34);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author();
        author.setName("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        List<Paper> papers = new ArrayList<>();
        Paper paper35 = new Paper();
        paper35.setDecision(Decision.REJECT);
        papers.add(paper35);
        
        Paper paper36 = new Paper();
        paper36.setDecision(Decision.REJECT);
        papers.add(paper36);
        
        Paper paper37 = new Paper();
        paper37.setDecision(Decision.REJECT);
        papers.add(paper37);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author();
        author.setName("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        List<Paper> papers = new ArrayList<>();
        Paper paper38 = new Paper();
        paper38.setDecision(Decision.ACCEPT);
        papers.add(paper38);
        
        Paper paper39 = new Paper();
        paper39.setDecision(Decision.REJECT);
        papers.add(paper39);
        
        Paper paper40 = new Paper();
        paper40.setDecision(Decision.REJECT);
        papers.add(paper40);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.33
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        author.setName("A010");
        
        // Create Paper P41 with ACCEPT decision
        List<Paper> papers = new ArrayList<>();
        Paper paper41 = new Paper();
        paper41.setDecision(Decision.ACCEPT);
        papers.add(paper41);
        
        author.setSubmittedPapers(papers);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}