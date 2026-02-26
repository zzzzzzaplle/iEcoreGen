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
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper30 = createPaperWithDecision("P30", true);
        Paper paper31 = createPaperWithDecision("P31", true);
        Paper paper32 = createPaperWithDecision("P32", true);
        
        // Add papers to author
        author.getPapers().add(paper30);
        author.getPapers().add(paper31);
        author.getPapers().add(paper32);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author();
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper33 = createPaperWithDecision("P33", true);
        Paper paper34 = createPaperWithDecision("P34", false);
        
        // Add papers to author
        author.getPapers().add(paper33);
        author.getPapers().add(paper34);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author();
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper35 = createPaperWithDecision("P35", false);
        Paper paper36 = createPaperWithDecision("P36", false);
        Paper paper37 = createPaperWithDecision("P37", false);
        
        // Add papers to author
        author.getPapers().add(paper35);
        author.getPapers().add(paper36);
        author.getPapers().add(paper37);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author();
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper38 = createPaperWithDecision("P38", true);
        Paper paper39 = createPaperWithDecision("P39", false);
        Paper paper40 = createPaperWithDecision("P40", false);
        
        // Add papers to author
        author.getPapers().add(paper38);
        author.getPapers().add(paper39);
        author.getPapers().add(paper40);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        
        // Create Paper P41 with ACCEPT decision
        Paper paper41 = createPaperWithDecision("P41", true);
        
        // Add paper to author
        author.getPapers().add(paper41);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    /**
     * Helper method to create a paper with a specified decision
     */
    private Paper createPaperWithDecision(String title, boolean accepted) {
        Paper paper = new Paper();
        paper.setTitle(title);
        
        Decision decision = new Decision();
        decision.setAccepted(accepted);
        paper.setDecision(decision);
        
        return paper;
    }
}