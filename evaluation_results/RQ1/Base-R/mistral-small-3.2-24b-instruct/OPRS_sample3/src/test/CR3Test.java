import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Create Author A001
        Author author = new Author("A001");
        
        // Create Papers P19-P23 linked to A001
        Paper paper19 = new Paper("P19", true);
        Paper paper20 = new Paper("P20", true);
        Paper paper21 = new Paper("P21", false);
        Paper paper22 = new Paper("P22", true);
        Paper paper23 = new Paper("P23", false);
        
        author.addSubmittedPaper(paper19);
        author.addSubmittedPaper(paper20);
        author.addSubmittedPaper(paper21);
        author.addSubmittedPaper(paper22);
        author.addSubmittedPaper(paper23);
        
        // Verify the count is 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Create Author A002 (no papers)
        Author author = new Author("A002");
        
        // Verify the count is 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Create Author A003
        Author author = new Author("A003");
        
        // Create Paper P24 linked to A003
        Paper paper24 = new Paper("P24", true);
        author.addSubmittedPaper(paper24);
        
        // Verify the count is 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Create User U1 with Author and Reviewer roles
        // Since Java doesn't support multiple inheritance, we'll create an Author
        // (which is a User) and test the author functionality specifically
        Author author = new Author("U1");
        
        // Create Papers P25-P27 linked to U1
        Paper paper25 = new Paper("P25", true);
        Paper paper26 = new Paper("P26", false);
        Paper paper27 = new Paper("P27", true);
        
        author.addSubmittedPaper(paper25);
        author.addSubmittedPaper(paper26);
        author.addSubmittedPaper(paper27);
        
        // Verify the count is 3
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Create Author A005
        Author authorA005 = new Author("A005");
        
        // Create Papers P28 (A005)
        Paper paper28 = new Paper("P28", true);
        authorA005.addSubmittedPaper(paper28);
        
        // Create Author A006
        Author authorA006 = new Author("A006");
        
        // Create Papers P29 (A006)
        Paper paper29 = new Paper("P29", false);
        authorA006.addSubmittedPaper(paper29);
        
        // Verify A005 has only 1 paper (P28)
        assertEquals(1, authorA005.countSubmittedPapers());
        
        // Verify A006 has only 1 paper (P29)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}