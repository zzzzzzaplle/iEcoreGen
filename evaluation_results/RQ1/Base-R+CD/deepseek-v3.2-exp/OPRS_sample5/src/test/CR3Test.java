import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author authorA001;
    private Author authorA002;
    private Author authorA003;
    private User userU1;
    private Author authorA004;
    private Author authorA005;
    private Author authorA006;
    
    @Before
    public void setUp() {
        // Initialize authors for test cases
        authorA001 = new Author();
        authorA002 = new Author();
        authorA003 = new Author();
        
        // Setup for multi-role user test case
        userU1 = new User();
        authorA004 = new Author();
        userU1.addRole(authorA004);
        
        // Setup for paper ownership validation test case
        authorA005 = new Author();
        authorA006 = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        
        // Create 5 papers and add them to author A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA001.submitPaper(paper);
        }
        
        // Expected Output: 5
        int result = authorA001.countSubmittedPapers();
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        
        // Expected Output: 0
        int result = authorA002.countSubmittedPapers();
        assertEquals("New author with no papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        
        // Create and submit one paper
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        authorA003.submitPaper(paperP24);
        
        // Expected Output: 1
        int result = authorA003.countSubmittedPapers();
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles, and Papers P25-P27 linked to U1
        
        // Create and submit 3 papers to the author role
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA004.submitPaper(paper);
        }
        
        // Expected Output: 3
        int result = authorA004.countSubmittedPapers();
        assertEquals("Multi-role user with 3 papers in author role should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: 
        // 1. Create Author A005 and Paper P28 (A005)
        // 2. Create Author A006 and Paper P29 (A006)
        
        // Create and submit paper for A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create and submit paper for A006 (should not be counted for A005)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only A005's paper should be counted)
        int result = authorA005.countSubmittedPapers();
        assertEquals("Author A005 should only count its own paper, not A006's paper", 1, result);
        
        // Verify A006 has its own paper
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have its own paper", 1, resultA006);
    }
}