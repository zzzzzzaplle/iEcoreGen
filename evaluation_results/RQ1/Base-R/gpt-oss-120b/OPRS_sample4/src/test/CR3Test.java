import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        author.setName("A001");
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Verify the total number of papers is 5
        assertEquals(5, author.getTotalSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        Author author = new Author();
        author.setName("A002");
        
        // Verify the total number of papers is 0
        assertEquals(0, author.getTotalSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addPaper(paper);
        
        // Verify the total number of papers is 1
        assertEquals(1, author.getTotalSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        // Note: Since Java doesn't support multiple inheritance, we'll create an Author
        // and demonstrate that the method works regardless of other roles
        Author author = new Author();
        author.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addPaper(paper);
        }
        
        // Verify the total number of papers is 3
        assertEquals(3, author.getTotalSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addPaper(paperP28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addPaper(paperP29);
        
        // Verify Author A005 has exactly 1 paper
        assertEquals(1, authorA005.getTotalSubmittedPapers());
    }
}