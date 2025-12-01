import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        author.setName("A001");
        
        // Create 5 papers and add them to author
        List<Paper> papers = new ArrayList<>();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            author.addSubmittedPaper(paper);
        }
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addSubmittedPaper(paper);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUser() {
        // Setup: Create User U1 with Author and Reviewer roles
        // Note: Since Java doesn't support multiple inheritance, we'll treat U1 as an Author
        Author author = new Author();
        author.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        List<Paper> papers = new ArrayList<>();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            author.addSubmittedPaper(paper);
        }
        
        // Input: Count papers for Author A004 (U1 as author)
        int result = author.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addSubmittedPaper(paperP28);
        
        // Setup: Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addSubmittedPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1 (only P28 should be counted for A005)
        assertEquals(1, result);
        
        // Additional verification that A006 has 1 paper
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}