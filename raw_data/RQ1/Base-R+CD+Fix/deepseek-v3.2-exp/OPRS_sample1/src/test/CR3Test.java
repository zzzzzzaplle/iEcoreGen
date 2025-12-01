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
        Paper paper19 = new Paper();
        Paper paper20 = new Paper();
        Paper paper21 = new Paper();
        Paper paper22 = new Paper();
        Paper paper23 = new Paper();
        
        // Submit papers to author
        author.submitPaper(paper19);
        author.submitPaper(paper20);
        author.submitPaper(paper21);
        author.submitPaper(paper22);
        author.submitPaper(paper23);
        
        // Verify the count of submitted papers
        assertEquals("Author with 5 papers should return count 5", 5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        Author author = new Author();
        
        // Verify the count of submitted papers is 0
        assertEquals("New author with 0 papers should return count 0", 0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper24 = new Paper();
        
        // Submit paper to author
        author.submitPaper(paper24);
        
        // Verify the count of submitted papers is 1
        assertEquals("Author with 1 paper should return count 1", 1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add both roles to user
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1's author role
        Paper paper25 = new Paper();
        Paper paper26 = new Paper();
        Paper paper27 = new Paper();
        
        // Submit papers to author role
        authorRole.submitPaper(paper25);
        authorRole.submitPaper(paper26);
        authorRole.submitPaper(paper27);
        
        // Verify the count of submitted papers from author role
        assertEquals("Multi-role user with 3 papers in author role should return count 3", 3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        Paper paper28 = new Paper();
        authorA005.submitPaper(paper28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        Paper paper29 = new Paper();
        authorA006.submitPaper(paper29);
        
        // Verify A005 has only 1 paper (P28)
        assertEquals("Author A005 should have exactly 1 paper", 1, authorA005.countSubmittedPapers());
        
        // Verify A006 has only 1 paper (P29)
        assertEquals("Author A006 should have exactly 1 paper", 1, authorA006.countSubmittedPapers());
    }
}