import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        User author = new User("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper1 = new Paper("P30", true);
        paper1.setFinalDecision("Accept");
        Paper paper2 = new Paper("P31", true);
        paper2.setFinalDecision("Accept");
        Paper paper3 = new Paper("P32", true);
        paper3.setFinalDecision("Accept");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify result
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        User author = new User("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper("P33", true);
        paper1.setFinalDecision("Accept");
        Paper paper2 = new Paper("P34", true);
        paper2.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate and verify result
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        User author = new User("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper1 = new Paper("P35", true);
        paper1.setFinalDecision("Reject");
        Paper paper2 = new Paper("P36", true);
        paper2.setFinalDecision("Reject");
        Paper paper3 = new Paper("P37", true);
        paper3.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify result
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        User author = new User("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper1 = new Paper("P38", true);
        paper1.setFinalDecision("Accept");
        Paper paper2 = new Paper("P39", true);
        paper2.setFinalDecision("Reject");
        Paper paper3 = new Paper("P40", true);
        paper3.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate and verify result
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        User author = new User("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper("P41", true);
        paper.setFinalDecision("Accept");
        
        // Add paper to author
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate and verify result
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}