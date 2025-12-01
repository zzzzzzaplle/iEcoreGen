import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Create Author A001
        Author author = new Author();
        author.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(author);
            author.addSubmittedPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Create Author A003
        Author author = new Author();
        author.setName("A003");
        
        // Create Paper P24 linked to A003
        Paper paper = new Paper();
        paper.setTitle("P24");
        paper.setAuthor(author);
        author.addSubmittedPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Create User U1 with Author and Reviewer roles
        // Since Java doesn't support multiple inheritance, we'll create an Author
        // and verify it can also act as a Reviewer by adding reviews
        Author author = new Author();
        author.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            paper.setAuthor(author);
            author.addSubmittedPaper(paper);
        }
        
        // Add reviewer functionality by creating reviews (though Author class doesn't have review methods)
        // This test focuses on counting submitted papers for the author role
        
        // Expected Output: 3
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Create Author A005
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        // Create Papers P28 (A005)
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        paperP28.setAuthor(authorA005);
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Author A006
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        // Create Papers P29 (A006)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        paperP29.setAuthor(authorA006);
        authorA006.addSubmittedPaper(paperP29);
        
        // Count papers for Author A005 - Expected Output: 1
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Verify A006 has 1 paper as well
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}