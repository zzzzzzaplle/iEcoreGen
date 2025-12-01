import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper1 = new Paper("P30", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P31", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.ACCEPT);
        
        Paper paper3 = new Paper("P32", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.ACCEPT);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify it's 1.00
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper("P33", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P34", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECT);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        
        // Calculate acceptance rate and verify it's 0.50
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper1 = new Paper("P35", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.REJECT);
        
        Paper paper2 = new Paper("P36", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECT);
        
        Paper paper3 = new Paper("P37", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.REJECT);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify it's 0.00
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper1 = new Paper("P38", PaperType.RESEARCH);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P39", PaperType.RESEARCH);
        paper2.setFinalDecision(Decision.REJECT);
        
        Paper paper3 = new Paper("P40", PaperType.RESEARCH);
        paper3.setFinalDecision(Decision.REJECT);
        
        // Add papers to author
        author.addPaper(paper1);
        author.addPaper(paper2);
        author.addPaper(paper3);
        
        // Calculate acceptance rate and verify it's approximately 0.33
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.01); // Using delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper("P41", PaperType.RESEARCH);
        paper.setFinalDecision(Decision.ACCEPT);
        
        // Add paper to author
        author.addPaper(paper);
        
        // Calculate acceptance rate and verify it's 1.00
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}