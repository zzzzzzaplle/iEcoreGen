import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Create Author A001
        Author author = new Author();
        
        // Create Papers P19-P23 linked to A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Verify the count is 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Create Author A002 (no papers)
        Author author = new Author();
        
        // Verify the count is 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Create Author A003
        Author author = new Author();
        
        // Create Paper P24 linked to A003
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Verify the count is 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Create User U1 with Author and Reviewer roles
        User user = new User();
        Author author = new Author();
        Reviewer reviewer = new Reviewer();
        user.addRole(author);
        user.addRole(reviewer);
        
        // Create Papers P25-P27 linked to U1 (via author role)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Verify the count is 3 (only counting papers submitted by author role)
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Create Author A005
        Author authorA005 = new Author();
        
        // Create Paper P28 (A005)
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Author A006
        Author authorA006 = new Author();
        
        // Create Paper P29 (A006)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Verify A005 has only 1 paper (ownership validation)
        assertEquals(1, authorA005.countSubmittedPapers());
    }
}