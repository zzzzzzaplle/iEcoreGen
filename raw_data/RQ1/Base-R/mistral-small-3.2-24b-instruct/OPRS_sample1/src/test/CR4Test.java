import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private ReviewSystem reviewSystem;
    
    @Before
    public void setUp() {
        reviewSystem = new ReviewSystem();
    }
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Create Author A006
        User author = new User();
        author.setName("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision("Accept");
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision("Accept");
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision("Accept");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = reviewSystem.calculateAcceptanceRate(author);
        
        // Verify perfect acceptance rate
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Create Author A007
        User author = new User();
        author.setName("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision("Accept");
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate
        double result = reviewSystem.calculateAcceptanceRate(author);
        
        // Verify 50% acceptance rate
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Create Author A008
        User author = new User();
        author.setName("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision("Reject");
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision("Reject");
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = reviewSystem.calculateAcceptanceRate(author);
        
        // Verify 0% acceptance rate
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Create Author A009
        User author = new User();
        author.setName("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision("Accept");
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision("Reject");
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision("Reject");
        
        // Add papers to author
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = reviewSystem.calculateAcceptanceRate(author);
        
        // Verify approximately 33% acceptance rate (1/3)
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Create Author A010
        User author = new User();
        author.setName("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision("Accept");
        
        // Add paper to author
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate
        double result = reviewSystem.calculateAcceptanceRate(author);
        
        // Verify perfect acceptance rate for single paper
        assertEquals(1.00, result, 0.001);
    }
}