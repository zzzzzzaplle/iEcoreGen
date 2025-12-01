import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_authorWithFivePapers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        Paper paper19 = new Paper();
        Paper paper20 = new Paper();
        Paper paper21 = new Paper();
        Paper paper22 = new Paper();
        Paper paper23 = new Paper();
        
        author.submitPaper(paper19);
        author.submitPaper(paper20);
        author.submitPaper(paper21);
        author.submitPaper(paper22);
        author.submitPaper(paper23);
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWithZeroPapers() {
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper24 = new Paper();
        
        author.submitPaper(paper24);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (via the Author role)
        Paper paper25 = new Paper();
        Paper paper26 = new Paper();
        Paper paper27 = new Paper();
        
        authorRole.submitPaper(paper25);
        authorRole.submitPaper(paper26);
        authorRole.submitPaper(paper27);
        
        // Input: Count papers for Author A004 (the author role)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        Paper paper28 = new Paper();
        
        authorA005.submitPaper(paper28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        Paper paper29 = new Paper();
        
        authorA006.submitPaper(paper29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}