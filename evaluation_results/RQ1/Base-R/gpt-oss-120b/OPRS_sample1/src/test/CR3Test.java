import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Create Author A001
        Author author = new Author();
        author.setName("A001");
        
        // Create Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.getPaperCount());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Expected Output: 0
        assertEquals(0, author.getPaperCount());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Create Author A003
        Author author = new Author();
        author.setName("A003");
        
        // Create Paper P24 linked to A003
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.getPaperCount());
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Create User U1 with Author role
        Author author = new Author();
        author.setName("U1");
        
        // Create Papers P25-P27 linked to U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Expected Output: 3
        assertEquals(3, author.getPaperCount());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Create Author A005
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        // Create Paper P28 (A005)
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Author A006
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        // Create Paper P29 (A006)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only count papers for A005)
        assertEquals(1, authorA005.getPaperCount());
    }
}