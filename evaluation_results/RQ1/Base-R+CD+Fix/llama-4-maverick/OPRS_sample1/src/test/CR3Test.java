import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        
        // Create 5 papers and submit them to the author
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
        
        // Verify the count of submitted papers is 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Verify the count of submitted papers is 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper24 = new Paper();
        
        author.submitPaper(paper24);
        
        // Verify the count of submitted papers is 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        User user = new User();
        user.setName("U1");
        
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add both roles to the user
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create papers and submit them through the author role
        Paper paper25 = new Paper();
        Paper paper26 = new Paper();
        Paper paper27 = new Paper();
        
        authorRole.submitPaper(paper25);
        authorRole.submitPaper(paper26);
        authorRole.submitPaper(paper27);
        
        // Verify the count of submitted papers is 3
        assertEquals(3, authorRole.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28
        // Create Author A006 with Paper P29
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper paper28 = new Paper();
        Paper paper29 = new Paper();
        
        // Submit papers to respective authors
        authorA005.submitPaper(paper28);
        authorA006.submitPaper(paper29);
        
        // Verify Author A005 has only 1 paper (paper28)
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Additional verification that Author A006 has 1 paper (paper29)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}