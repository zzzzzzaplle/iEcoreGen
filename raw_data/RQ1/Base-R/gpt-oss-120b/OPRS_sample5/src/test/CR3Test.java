import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Input: Count papers for Author A001
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        
        // Create Author A001
        Author author = new Author("A001");
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper("P" + i, PaperType.RESEARCH);
            author.addPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.getSubmittedPaperCount());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Input: Count papers for Author A002
        // Setup: Create Author A002 (no papers)
        
        // Create Author A002 with no papers
        Author author = new Author("A002");
        
        // Expected Output: 0
        assertEquals(0, author.getSubmittedPaperCount());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Input: Count papers for Author A003
        // Setup: Create Author A003 and Paper P24 linked to A003
        
        // Create Author A003
        Author author = new Author("A003");
        
        // Create Paper P24 and link to author
        Paper paper = new Paper("P24", PaperType.RESEARCH);
        author.addPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.getSubmittedPaperCount());
    }
    
    @Test
    public void testCase4_MultiRoleUser() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Input: Count papers for Author A004
        // Setup: Create User U1 with Author and Reviewer roles, Papers P25-P27 linked to U1
        
        // Create Author A004 (Note: The test spec mentions U1 but output expects A004)
        Author author = new Author("A004");
        
        // Create 3 papers and link them to the author
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper("P" + i, PaperType.RESEARCH);
            author.addPaper(paper);
        }
        
        // Expected Output: 3
        assertEquals(3, author.getSubmittedPaperCount());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Input: Count papers for Author A005
        // Setup: Create Author A005 with Paper P28, Author A006 with Paper P29
        
        // Create Author A005
        Author authorA005 = new Author("A005");
        
        // Create Paper P28 linked to A005
        Paper paperP28 = new Paper("P28", PaperType.RESEARCH);
        authorA005.addPaper(paperP28);
        
        // Create Author A006 with Paper P29 (should not be counted for A005)
        Author authorA006 = new Author("A006");
        Paper paperP29 = new Paper("P29", PaperType.RESEARCH);
        authorA006.addPaper(paperP29);
        
        // Expected Output: 1 (only P28 belongs to A005)
        assertEquals(1, authorA005.getSubmittedPaperCount());
    }
}