import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Author authorA006;
    private Author authorA007;
    private Author authorA008;
    private Author authorA009;
    private Author authorA010;
    
    @Before
    public void setUp() {
        // Clear all authors from previous tests
        List<Author> allAuthors = Author.getAllAuthors();
        if (allAuthors instanceof ArrayList) {
            ((ArrayList<Author>) allAuthors).clear();
        }
        
        // Initialize authors
        authorA006 = new Author();
        authorA007 = new Author();
        authorA008 = new Author();
        authorA009 = new Author();
        authorA010 = new Author();
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Papers P30-P32 with final decision=ACCEPT
        Paper p30 = new Paper();
        p30.setDecision(Grade.ACCEPT);
        Paper p31 = new Paper();
        p31.setDecision(Grade.ACCEPT);
        Paper p32 = new Paper();
        p32.setDecision(Grade.ACCEPT);
        
        // Submit papers to author A006
        authorA006.submitPaper(p30);
        authorA006.submitPaper(p31);
        authorA006.submitPaper(p32);
        
        // Calculate acceptance rate for Author A006
        double result = authorA006.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper p33 = new Paper();
        p33.setDecision(Grade.ACCEPT);
        Paper p34 = new Paper();
        p34.setDecision(Grade.REJECT);
        
        // Submit papers to author A007
        authorA007.submitPaper(p33);
        authorA007.submitPaper(p34);
        
        // Calculate acceptance rate for Author A007
        double result = authorA007.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Papers P35-P37 with REJECT decisions
        Paper p35 = new Paper();
        p35.setDecision(Grade.REJECT);
        Paper p36 = new Paper();
        p36.setDecision(Grade.REJECT);
        Paper p37 = new Paper();
        p37.setDecision(Grade.REJECT);
        
        // Submit papers to author A008
        authorA008.submitPaper(p35);
        authorA008.submitPaper(p36);
        authorA008.submitPaper(p37);
        
        // Calculate acceptance rate for Author A008
        double result = authorA008.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper p38 = new Paper();
        p38.setDecision(Grade.ACCEPT);
        Paper p39 = new Paper();
        p39.setDecision(Grade.REJECT);
        Paper p40 = new Paper();
        p40.setDecision(Grade.REJECT);
        
        // Submit papers to author A009
        authorA009.submitPaper(p38);
        authorA009.submitPaper(p39);
        authorA009.submitPaper(p40);
        
        // Calculate acceptance rate for Author A009
        double result = authorA009.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1/3)
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Paper P41 with ACCEPT decision
        Paper p41 = new Paper();
        p41.setDecision(Grade.ACCEPT);
        
        // Submit paper to author A010
        authorA010.submitPaper(p41);
        
        // Calculate acceptance rate for Author A010
        double result = authorA010.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}