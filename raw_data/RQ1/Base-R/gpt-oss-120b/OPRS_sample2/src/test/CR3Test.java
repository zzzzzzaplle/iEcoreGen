import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author("A001");
        
        // Create 5 papers and add them to the author
        Paper paper19 = new Paper("Paper P19", PaperType.RESEARCH);
        Paper paper20 = new Paper("Paper P20", PaperType.RESEARCH);
        Paper paper21 = new Paper("Paper P21", PaperType.EXPERIENCE_REPORT);
        Paper paper22 = new Paper("Paper P22", PaperType.RESEARCH);
        Paper paper23 = new Paper("Paper P23", PaperType.EXPERIENCE_REPORT);
        
        author.addPaper(paper19);
        author.addPaper(paper20);
        author.addPaper(paper21);
        author.addPaper(paper22);
        author.addPaper(paper23);
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        Author author = new Author("A002");
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author("A003");
        Paper paper24 = new Paper("Paper P24", PaperType.RESEARCH);
        author.addPaper(paper24);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        // Since Java doesn't support multiple inheritance, we'll create an Author
        // and demonstrate that the count works regardless of other roles
        Author author = new Author("U1");
        
        // Create Papers P25-P27 linked to U1
        Paper paper25 = new Paper("Paper P25", PaperType.RESEARCH);
        Paper paper26 = new Paper("Paper P26", PaperType.EXPERIENCE_REPORT);
        Paper paper27 = new Paper("Paper P27", PaperType.RESEARCH);
        
        author.addPaper(paper25);
        author.addPaper(paper26);
        author.addPaper(paper27);
        
        // Input: Count papers for Author A004
        int result = author.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals("Author with 3 papers should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Author A006
        Author authorA005 = new Author("A005");
        Author authorA006 = new Author("A006");
        
        // Create Paper P28 (A005) and Paper P29 (A006)
        Paper paper28 = new Paper("Paper P28", PaperType.RESEARCH);
        Paper paper29 = new Paper("Paper P29", PaperType.RESEARCH);
        
        authorA005.addPaper(paper28);
        authorA006.addPaper(paper29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1 (only P28 belongs to A005)
        assertEquals("Author A005 should have only 1 paper (P28)", 1, result);
        
        // Verify that A006 has only 1 paper (P29)
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have only 1 paper (P29)", 1, resultA006);
    }
}