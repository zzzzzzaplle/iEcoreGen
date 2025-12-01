import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 all ACCEPTED
        Author author = new Author();
        author.setName("A006");
        
        // Create papers with ACCEPT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision(Paper.Decision.ACCEPT);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT) and P34 (REJECT)
        Author author = new Author();
        author.setName("A007");
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision(Paper.Decision.REJECT);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 all REJECTED
        Author author = new Author();
        author.setName("A008");
        
        // Create papers with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision(Paper.Decision.REJECT);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        author.setName("A009");
        
        // Create papers with mixed decisions (1 ACCEPT, 2 REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision(Paper.Decision.REJECT);
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.33 (approximately)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with single Paper P41 ACCEPTED
        Author author = new Author();
        author.setName("A010");
        
        // Create paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision(Paper.Decision.ACCEPT);
        
        // Add paper to author
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}